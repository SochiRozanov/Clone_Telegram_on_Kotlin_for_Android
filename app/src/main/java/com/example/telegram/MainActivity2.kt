package com.example.telegram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.telegram.databinding.ActivityMainBinding


class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bNav.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.btn_Main_Menu_1->{
                    Toast.makeText(this,"Переход на главную",Toast.LENGTH_LONG).show()
                }
                R.id.btn_Main_Menu_1->{
                    Toast.makeText(this,"Переход на главную",Toast.LENGTH_LONG).show()
                }
                R.id.btn_Main_Menu_1->{
                    Toast.makeText(this,"Переход на главную",Toast.LENGTH_LONG).show()
                }
                R.id.btn_Main_Menu_1->{
                    Toast.makeText(this,"Переход на главную",Toast.LENGTH_LONG).show()
                }
                R.id.btn_Main_Menu_1->{
                    Toast.makeText(this,"Переход на главную",Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}