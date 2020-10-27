package com.example.learn_new_language

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.content.ContextCompat.getSystemService


/**
 * A simple [Fragment] subclass.
 * Use the [NumbersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NumbersFragment : Fragment() {

    private var mMediaPlayer: MediaPlayer? = null
    lateinit var mAudioManager: AudioManager

    private val mOnAudioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK
            ) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer!!.pause()
                mMediaPlayer!!.seekTo(0)
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer!!.start()
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.word_list, container, false)
        mAudioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Create a list of words
        var words: ArrayList<Word> = ArrayList<Word>()

        words.add(Word("one", "un", R.drawable.number_one, R.raw.numbers_one))
        words.add(Word("two", "deux", R.drawable.number_two, R.raw.numbers_two))
        words.add(Word("three", "Trois", R.drawable.number_three, R.raw.numbers_three))
        words.add(Word("four", "quatre", R.drawable.number_four, R.raw.numbers_four))
        words.add(Word("five", "cinq", R.drawable.number_five, R.raw.numbers_five))
        words.add(Word("six", "six", R.drawable.number_six, R.raw.numbers_six))
        words.add(Word("seven", "sept", R.drawable.number_seven, R.raw.numbers_seven))
        words.add(Word("eight", "huit", R.drawable.number_eight, R.raw.numbers_eight))
        words.add(Word("nine", "neuf", R.drawable.number_nine, R.raw.numbers_nine))
        words.add(Word("ten", "dix", R.drawable.number_ten, R.raw.numbers_ten))


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        var itemsAdapter: WordAdapter = WordAdapter(activity!!, words, R.color.category_numbers)
        val listView: ListView = rootView.findViewById(R.id.list)
        listView.adapter = itemsAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val word: Word = words.get(position)

            // Request audio focus so in order to play the audio file. The app needs to play a
            // short audio file, so we will request audio focus with a short amount of time
            // with AUDIOFOCUS_GAIN_TRANSIENT.
            val result = mAudioManager.requestAudioFocus(
                mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // We have audio focus now.

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                mMediaPlayer = MediaPlayer.create(activity, word.getAudioResource())

                // Start the audio file
                mMediaPlayer?.start()
            }

            /**
             * This listener gets triggered when the {@link MediaPlayer} has completed
             * playing the audio file.
             */
            mMediaPlayer?.setOnCompletionListener {
                releaseMediaPlayer()
            }
        }


        // Inflate the layout for this fragment
        return rootView
    }

    private fun releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer?.release()

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener)
        }
    }

    override fun onStop() {
        super.onStop()

        releaseMediaPlayer()
    }

}