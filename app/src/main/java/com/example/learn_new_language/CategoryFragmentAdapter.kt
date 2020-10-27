package com.example.learn_new_language

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CategoryFragmentAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var mContext = context
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            NumbersFragment()
        } else if (position == 1) {
            FamilyFragment()
        } else if(position == 2) {
            ColorsFragment()
        } else{
            PhrasesFragment()
        }
        }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            mContext.getString(R.string.category_numbers)
        } else if (position == 1) {
            mContext.getString(R.string.category_family)
        } else if (position == 2) {
            mContext.getString(R.string.category_colors)
        } else {
            mContext.getString(R.string.category_phrases)
        }
    }


    override fun getCount(): Int {
        return 4
    }
}