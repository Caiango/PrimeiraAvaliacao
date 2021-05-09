package com.example.avaliaoandroidbasico.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.avaliaoandroidbasico.R
import com.example.avaliaoandroidbasico.model.Frutas
import com.squareup.picasso.Picasso

class FruitAdapter(var clickListener: onClickListener) :
    RecyclerView.Adapter<FruitAdapter.HolderData>() {

    var fruitList = ArrayList<Frutas>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.frutas_rv_item, parent, false)
        return HolderData(v)
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

    class HolderData(v: View) : RecyclerView.ViewHolder(v) {
        var fruitName = v.findViewById<TextView>(R.id.fruitName)
        var fruitDesc = v.findViewById<TextView>(R.id.fruitsDesc)
        var fruitPhoto = v.findViewById<ImageView>(R.id.fruitPhoto)

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

    interface onClickListener {
        fun onItemClick(item: Frutas, position: Int) {

        }
    }

}