package com.globant.videoplayerproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.globant.videoplayerproject.R
import com.globant.videoplayerproject.model.Data
import kotlinx.android.synthetic.main.item_row.view.*

class TopGamesAdapter: RecyclerView.Adapter<TopGamesAdapter.ViewHolder>() {

    lateinit var onClick: (Data) -> Unit
    private var dataSet = emptyList<Data>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bindResponse(item, onClick)
    }

    override fun getItemCount() =  dataSet.size

    fun addGames(items: List<Data>) {
        this.dataSet = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindResponse(game: Data, onClick: (Data) -> Unit) = with(itemView){
            val imageUrl = game.box_art_url.replace("{width}x{height}", "150x170")
            Glide.with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_search_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(item_image)

            setOnClickListener{ onClick(game) }
        }
    }
}
