package com.example.learn_new_language

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FamilyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportFragmentManager.beginTransaction().replace(R.id.container, FamilyFragment()).commit()
    }

}