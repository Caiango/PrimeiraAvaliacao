package com.example.avaliaoandroidbasico.adapter

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliaoandroidbasico.R
import com.example.avaliaoandroidbasico.constants.Constants
import com.example.avaliaoandroidbasico.databinding.FrutasRvItemBinding
import com.example.avaliaoandroidbasico.model.Frutas
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class FruitAdapter(var clickListener: onClickListener, context: Context) :
    RecyclerView.Adapter<FruitAdapter.HolderData>() {

    var fruitList = ArrayList<Frutas>()
    var mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.frutas_rv_item, parent, false)
        val binding = FrutasRvItemBinding.bind(v)
        val vh = HolderData(binding)
        return vh
    }

    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = fruitList[position]

        holder.fruitName.text = data.nome
        holder.fruitDesc.text = data.desc
        if (data.foto != null) {
            Picasso.get().load(data.foto).into(holder.fruitPhoto)
        }
        holder.initializeClick(fruitList, clickListener)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    class HolderData(v: FrutasRvItemBinding) : RecyclerView.ViewHolder(v.root) {
        var fruitName = v.fruitName
        var fruitDesc = v.fruitsDesc
        var fruitPhoto = v.fruitPhoto

        fun initializeClick(item: ArrayList<Frutas>, action: onClickListener) {

            itemView.setOnClickListener {
                action.onItemClick(item[position], adapterPosition)
            }
        }
    }

    fun getList(frutasList: ArrayList<Frutas>) {
        fruitList = frutasList
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        val dialog = AlertDialog.Builder(mContext)
        dialog.setTitle("Deseja Excluir a Fruta?")
        dialog.setPositiveButton("Sim") { _: DialogInterface, _: Int ->
            fruitList.removeAt(position)
            notifyDataSetChanged()
        }
        dialog.setNegativeButton("Não") { _: DialogInterface, _: Int ->
            Toast.makeText(mContext, "CANCELADO", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }
        dialog.show()

    }

    fun swap(initPos: Int, targetPos: Int) {
        Collections.swap(fruitList, initPos, targetPos)
        notifyItemMoved(initPos, targetPos)
    }

    interface onClickListener {
        fun onItemClick(item: Frutas, position: Int) {

        }
    }

}