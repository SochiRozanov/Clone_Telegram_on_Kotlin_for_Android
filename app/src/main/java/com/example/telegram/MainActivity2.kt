package com.example.telegram

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.example.telegram.utilits.APP_ACTIVITY
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class MainActivity2 : AppCompatActivity() {
    private val cropActivityResultContract = object : ActivityResultContract<Any, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(1, 1)
                .setRequestedSize(250, 250)
                .setCropShape(CropImageView.CropShape.OVAL)
                .getIntent(this@MainActivity2)

        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnChooseImage = findViewById<Button>(R.id.btnChooseImage)
        val ivCropImage = findViewById<ImageView>(R.id.ivCroppedImage)
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract){
            it?.let { uri ->
                ivCropImage.setImageURI(uri)
            }
        }
        btnChooseImage.setOnClickListener{
            cropActivityResultLauncher.launch(null)
        }
    }
}