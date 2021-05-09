package com.example.avaliaoandroidbasico.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.avaliaoandroidbasico.R
import com.example.avaliaoandroidbasico.constants.Constants
import com.example.avaliaoandroidbasico.databinding.ActivityAddNewFruitBinding
import com.example.avaliaoandroidbasico.model.Frutas

class AddNewFruitActivity : AppCompatActivity() {
    private lateinit var fruit: Frutas
    private lateinit var binding: ActivityAddNewFruitBinding

    companion object {
        const val PERMISSION_CODE_READ = 1001
        const val PERMISSION_CODE_WRITE = 1002
        const val IMAGE_PICK_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fruit = Frutas(null, null, null)
        setupUI()


    }

    private fun setupUI() {

        binding.btnConclude.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val desc = binding.editDesc.text.toString()

            fruit.nome = nome
            fruit.desc = desc

            if (fruit.nome.equals("") || fruit.desc.equals("") || fruit.foto.isNullOrBlank()) {
                binding.editNome.error = getString(R.string.edit_error)
            } else {
                val retorno = Intent()
                retorno.putExtra(Constants.NEW_FRUIT, fruit)
                setResult(Activity.RESULT_OK, retorno)
                finish()
            }
        }


        binding.btnFoto.setOnClickListener {
            checkPermissionForImage()
        }
    }

    private fun checkPermissionForImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED)
                && (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)


                requestPermissions(permission, PERMISSION_CODE_READ)
                requestPermissions(permissionCoarse, PERMISSION_CODE_WRITE)
                checkPermissionForImage()
            } else {
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // I'M GETTING THE URI OF THE IMAGE AS DATA AND SETTING IT TO THE IMAGEVIEW
            var image = data?.data
            fruit.foto = image.toString()
        }
    }
}