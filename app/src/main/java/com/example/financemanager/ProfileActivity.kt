package com.example.financemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
    fun converterClick(view: View){
        finish()
        val convert: Intent = Intent(this@ProfileActivity,convector::class.java)
        startActivity(convert);
    }
    fun mainClick(view: View){
        finish()

    }
}