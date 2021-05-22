package com.example.avaliaoandroidbasico.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avaliaoandroidbasico.R
import com.example.avaliaoandroidbasico.adapter.FruitAdapter
import com.example.avaliaoandroidbasico.constants.Constants
import com.example.avaliaoandroidbasico.databinding.ActivityMainBinding
import com.example.avaliaoandroidbasico.helper.ItemHelper
import com.example.avaliaoandroidbasico.model.Frutas


class MainActivity : AppCompatActivity(), FruitAdapter.onClickListener {

    private var arrayFrutas = ArrayList<Frutas>()
    private lateinit var binding: ActivityMainBinding
    private var adapter = FruitAdapter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState != null) {
            val saved = savedInstanceState.getParcelableArrayList<Frutas>(Constants.ARRAY_LIST)
            arrayFrutas = saved as ArrayList<Frutas>
            Log.i("onCreate", saved.toString())
        } else {
            var img1 = Uri.parse(
                "android.resource://" + R::class.java.getPackage()
                    .getName() + "/" + R.mipmap.orange_image
            ).toString()
            var img2 = Uri.parse(
                "android.resource://" + R::class.java.getPackage()
                    .getName() + "/" + R.mipmap.ic_banana_foreground
            ).toString()
            var Fruta1 = Frutas(
                "Laranja",
                img1,
                "Diminui os níveis de colesterol ruim no organismo. \nRica em vitamina C. \nContém flavonoides, betacaroteno e fibras."
            )
            var Fruta2 = Frutas(
                "Banana",
                img2,
                "Previne as doenças cardiovasculares. \nFaz bem para o sistema digestivo. \nMelhora o humor."
            )
            arrayFrutas.add(Fruta1)
            arrayFrutas.add(Fruta2)

            adapter.getList(arrayFrutas)
        }

        setupUI()

        adapter.getList(arrayFrutas)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (Constants.REQUEST_CODE_RESULT_1 == requestCode) {
                arrayFrutas.add(data?.getParcelableExtra(Constants.NEW_FRUIT)!!)
                adapter.notifyDataSetChanged()
            } else if (Constants.REQUEST_CODE_RESULT_2 == requestCode) {
                deleteFruit(data?.getParcelableExtra(Constants.SELECTED_FRUIT_FROM_RESULT)!!)
            }

        }
    }

    fun setupUI() {
        binding.rvFruits.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvFruits.setHasFixedSize(true)
        binding.rvFruits.adapter = adapter
        val itemTouch = ItemTouchHelper(ItemHelper(adapter))
        itemTouch.attachToRecyclerView(binding.rvFruits)

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddNewFruitActivity::class.java)
            intent.putParcelableArrayListExtra(Constants.ARRAY_LIST, arrayFrutas)
            startActivityForResult(intent, Constants.REQUEST_CODE_RESULT_1)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.ARRAY_LIST, arrayFrutas)
        Log.i("onSavedInstance", "onSavedInstance")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val saved = savedInstanceState.getParcelableArrayList<Frutas>(Constants.ARRAY_LIST)
        Log.i("onRestoreInstanceState", saved.toString())
    }

    override fun onItemClick(item: Frutas, position: Int) {
        callFruitDetail(item)
    }

    fun callFruitDetail(item: Frutas) {
        val intent = Intent(this, FruitDetails::class.java)
        intent.putExtra(Constants.SELECTED_FRUIT, item)
        startActivityForResult(intent, Constants.REQUEST_CODE_RESULT_2)
    }

    fun deleteFruit(fruta: Frutas) {
        arrayFrutas.remove(fruta)
        adapter.notifyDataSetChanged()
    }

}