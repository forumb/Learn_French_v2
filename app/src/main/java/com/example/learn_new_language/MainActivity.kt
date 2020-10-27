package com.example.learn_new_language

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Create instance of view pager to set the fragments
         * @id viewpager is created in main activity xml file
         */
        val viewPager: ViewPager = findViewById(R.id.viewpager)
        val categoryAdapter: CategoryFragmentAdapter = CategoryFragmentAdapter(this, supportFragmentManager)

        //Set adapter to categoryAdapter which is instance of CategoryFragmentAdapter
        viewPager.adapter = categoryAdapter

        /**
         * Create instance of tab layout to view fragment
         * @id viewpager is created in main activity xml file
         */
        val tabLayout: TabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)

    }
}