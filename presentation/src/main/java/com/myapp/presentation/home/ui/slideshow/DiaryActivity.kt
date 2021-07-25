package com.myapp.presentation.home.ui.slideshow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myapp.presentation.R


class DiaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary) // コードからFragmentを追加

        val fragment = SlideshowFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .commit()
    }


}