package com.example.ar_sceneform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ar_sceneform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Using view binding instead of findViewById
    private lateinit var activityMainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        //On button click go to AR activity
        activityMainBinding.arButton.setOnClickListener {
            val intent = Intent(this,ArActivity::class.java)
            startActivity(intent)
        }
    }

}