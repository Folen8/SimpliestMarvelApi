package com.example.marvel2.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marvel2.R
import com.example.marvel2.data.Result
import com.squareup.picasso.Picasso

class RecyclerAdapter(val context: Context, var characterList: ArrayList<Result>, private val onClickListener:(Result)->Unit) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_characters,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvCharacterName.text = characterList.get(position).name
        val imageUrl = "${characterList[position].thumbnail.path}/portrait_xlarge.${characterList[position].thumbnail.extension}"
        val imageUrlSecured = imageUrl.replace("http","https")
        Glide.with(context)
            .load(imageUrlSecured)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)
        holder.item.setOnClickListener { onClickListener(characterList[position]) }
    }

    override fun getItemCount() = characterList.size

    fun setCharacterListItems(characterList: ArrayList<Result>){
        this.characterList.addAll(characterList)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvCharacterName: TextView = itemView.findViewById(R.id.tvCharacterName)
        val image: ImageView = itemView.findViewById(R.id.ivCharaterImage)

        val item: CardView = itemView.findViewById(R.id.cvCharacter)
    }
}


