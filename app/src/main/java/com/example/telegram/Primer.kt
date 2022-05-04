package com.example.telegram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Primer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primer)
        newresul()
    }

    private fun newresul() {
        val i =Intent()
        i.putExtra("key1","done")
        setResult(RESULT_OK, i)
        finish()
    }

}