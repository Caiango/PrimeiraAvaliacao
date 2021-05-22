package com.example.avaliaoandroidbasico.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.avaliaoandroidbasico.constants.Constants
import com.example.avaliaoandroidbasico.databinding.ActivityFruitDetailsBinding
import com.example.avaliaoandroidbasico.model.Frutas
import com.squareup.picasso.Picasso

class FruitDetails : AppCompatActivity() {
    private lateinit var binding: ActivityFruitDetailsBinding
    private lateinit var fruit: Frutas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent
        fruit = bundle.getParcelableExtra(Constants.SELECTED_FRUIT)!!
        setupUI()

    }

    private fun setupUI() {
        binding.selFruitName.text = fruit.nome
        binding.selFruitDesc.text = fruit.desc
        Picasso.get().load(fruit.foto).into(binding.selFruitImg)

        binding.button.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Deseja Excluir a Fruta?")
            dialog.setPositiveButton("Sim") { _: DialogInterface, _: Int ->
                val retorno = Intent()
                retorno.putExtra(Constants.SELECTED_FRUIT_FROM_RESULT, fruit)
                setResult(Activity.RESULT_OK, retorno)
                finish()
            }
            dialog.setNegativeButton("Não") { _: DialogInterface, _: Int ->
                Toast.makeText(this, "CANCELADO", Toast.LENGTH_SHORT).show()
            }
            dialog.show()

        }

    }

}