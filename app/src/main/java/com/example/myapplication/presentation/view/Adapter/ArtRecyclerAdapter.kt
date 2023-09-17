package com.example.myapplication.presentation.view.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.myapplication.R
import com.example.myapplication.roomdb.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>() {
    class ArtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }
    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_roow, parent, false)
        return ArtViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arts.size

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.artRowNameTex)
        val artIstnameText = holder.itemView.findViewById<TextView>(R.id.artRowAristNameTex)
        val yearText = holder.itemView.findViewById<TextView>(R.id.artRowYearTex)
        val art = arts[position]
        holder.itemView.apply {
            nameText.text = "Name:${art.name}"
            artIstnameText.text = "Artist Name:${art.artistName}"
            yearText.text = "Year : ${art.year}"
            glide.load(art.imageUrl).into(imageView)

        }


    }
}