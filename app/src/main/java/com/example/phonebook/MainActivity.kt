package com.example.phonebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logo = findViewById<ImageView>(R.id.imageView)
        logo.setOnClickListener{
            val Intent = Intent (this,MainActivity2::class.java)
            startActivity(Intent)
        }
    }
}