package com.example.telegram.ui.screens.settings

import android.app.Activity.RESULT_OK

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.example.telegram.R
import com.example.telegram.database.*
import com.example.telegram.ui.screens.base.BaseFragment
import com.example.telegram.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_settings.*


/* Фрагмент настроек */

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val cropActivityResultContract = object : ActivityResultContract<Any,Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage
                .activity()
                .setAspectRatio(1, 1)
                .setRequestedSize(250, 250)
                .setCropShape(CropImageView.CropShape.OVAL)
                .getIntent(APP_ACTIVITY)

        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract){uri ->
            uri?.path?.let {
                val path = REF_STORAGE_ROOT.child(
                    FOLDER_PROFILE_IMAGE
                )
                    .child(CURRENT_UID)
                putFileToStorage(uri, path) {
                    getUrlFromStorage(path) {
                        putUrlToDatabase(it) {
                            settings_user_photo.downloadAndSetImage(it)
                            showToast(getString(R.string.toast_data_update))
                            USER.photoUrl = it
                            APP_ACTIVITY.mAppDrawer.updateHeader()
                        }
                    }
                }

            }

     //           showToast(getString(R.string.toast_data_update))
            }









            //val uri = null
           // val path = REF_STORAGE_ROOT.child(
           //     FOLDER_PROFILE_IMAGE
           // )
           //     .child(CURRENT_UID)
           // uri?.let { it1 ->
            //    putFileToStorage(it1, path) {
            //        getUrlFromStorage(path) {
            //            putUrlToDatabase(it) {
            //                settings_user_photo.downloadAndSetImage(it)
            //                showToast(getString(R.string.toast_data_update))
            //                USER.photoUrl = it
            //                APP_ACTIVITY.mAppDrawer.updateHeader()
            //            }
             //       }
            //    }


    }
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()


      //  }
    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.state
        settings_username.text = USER.username
        settings_btn_change_username.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        settings_btn_change_bio.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        //onclick запускает функцию
        settings_change_photo.setOnClickListener { startphotobutton() }
        settings_user_photo.downloadAndSetImage(USER.photoUrl)

    }
    private fun startphotobutton(){
        //Log.i(TAG, "Permission:, granted:")
        cropActivityResultLauncher.launch(null)

    }


   // private fun changePhotoUser(){
   //     launcher.launch( )
    //    CropImage.activity()
     //       .setAspectRatio(1, 1)
     //       .setRequestedSize(250, 250)
     //       .setCropShape(CropImageView.CropShape.OVAL)
     //       .start(APP_ACTIVITY,this)

    //}


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        /* Создания выпадающего меню*/
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* Слушатель выбора пунктов выпадающего меню */
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AppStates.updateState(AppStates.OFFLINE)
                AUTH.signOut()
                restartActivity()
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /* Активность которая запускается для получения картинки для фото пользователя */
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK && data != null
        ) {

            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(
                FOLDER_PROFILE_IMAGE
            )
                .child(CURRENT_UID)
            putFileToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        settings_user_photo.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        USER.photoUrl = it
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                    }
                }
            }
        }
    }

}

//private fun <I> ActivityResultLauncher<I>?.launch(function: () -> Unit) {

//}

//private fun <I> ActivityResultLauncher<I>?.launch(changePhotoUser: Unit) {

//}
