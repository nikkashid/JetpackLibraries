package com.nikhil.jetpacklibraries.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikhil.jetpacklibraries.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
    }
}