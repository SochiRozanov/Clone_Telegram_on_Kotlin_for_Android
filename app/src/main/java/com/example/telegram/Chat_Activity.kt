package com.example.telegram

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.telegram.database.AUTH
import com.example.telegram.database.initFirebase
import com.example.telegram.database.initUser
import com.example.telegram.databinding.ActivityMainBinding
import com.example.telegram.ui.screens.main_list.MainListFragment
import com.example.telegram.ui.screens.register.EnterPhoneNumberFragment
import com.example.telegram.ui.objects.AppDrawer
import com.example.telegram.utilits.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/* Главная активность*/

class Chat_Activity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    lateinit var mToolbar: Toolbar
    lateinit var bottom_binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        /* Функция запускается один раз, при создании активити */
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.bNav.selectedItemId = R.id.btn_Main_Menu_4
        mBinding.bNav.setOnNavigationItemReselectedListener {
        when(it.itemId){
            R.id.btn_Main_Menu_1-> {
                Toast.makeText(this,"Переход на главную",Toast.LENGTH_LONG).show()
            }
            R.id.btn_Main_Menu_2-> {
                Toast.makeText(this,"Переход на списки",Toast.LENGTH_LONG).show()
            }
            R.id.btn_Main_Menu_3-> {
                Toast.makeText(this,"Переход на добавить",Toast.LENGTH_LONG).show()
            }
            R.id.btn_Main_Menu_4-> {
                Toast.makeText(this,"Переход на связь",Toast.LENGTH_LONG).show()
            }
            R.id.btn_Main_Menu_5-> {
                Toast.makeText(this,"Переход в кабинет",Toast.LENGTH_LONG).show()
            }
        }
        }

        APP_ACTIVITY = this


        initFirebase()
        //elv[
        initUser {
            CoroutineScope(Dispatchers.IO).launch {
                initContacts()
            }
            initFields()
            initFunc()

        }
    }

    //
    private fun initFunc() {
        /* Функция инициализирует функциональность приложения */
        setSupportActionBar(mToolbar)
        if (AUTH.currentUser != null) {
            mAppDrawer.create()
            replaceFragment(MainListFragment(), false)
        } else {
            replaceFragment(EnterPhoneNumberFragment(), false)
        }
    }

    private fun initFields() {
        /* Функция инициализирует переменные */
        mToolbar = mBinding.mainToolbarTop
        mAppDrawer = AppDrawer()
    }

    override fun onStart() {
        super.onStart()
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initContacts()
        }
    }
}
