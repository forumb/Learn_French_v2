package com.example.learn_new_language

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class NumbersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NumbersFragment())
            .commit()
    }
}

