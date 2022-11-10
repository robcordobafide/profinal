package com.rtc.utiles

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import com.rtc.BuildConfig
import java.io.File

class ImagenUtiles (
    private val contexto: Context,
    private val btPhoto: ImageButton,
    private val imagen: ImageView,
    private val tomarFotoActivity: ActivityResultLauncher<Intent>) {

    init {
        btPhoto.setOnClickListener { tomaFoto() }
    }

    lateinit var imagenFile: File
    private lateinit var currentImagenPath: String

    private fun tomaFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(contexto.packageManager) != null){
            imagenFile= createImagenFile()
            val imagenURI = FileProvider.getUriForFile(
                contexto,
                BuildConfig.APPLICATION_ID+".provider",
                imagenFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imagenURI)
            tomarFotoActivity.launch(intent)
        }
    }

    private fun createImagenFile(): File {
        val archivo = OtrosUtiles.getTempFile("imagen_")
        val storageDir: File?=contexto
            .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imagen=File.createTempFile(
            archivo,
            ".jpg",
            storageDir)
        currentImagenPath = imagen.absolutePath
        return imagen
    }

    fun actualizaFoto() {
        imagen.setImageBitmap(
            BitmapFactory.decodeFile(imagenFile.absolutePath))
    }
}