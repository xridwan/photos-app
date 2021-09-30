package com.example.awesomeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.R
import com.example.awesomeapp.databinding.LayoutItemGridBinding

class MainGridAdapter(
    private var photos: MutableList<Photo>,
) : RecyclerView.Adapter<MainGridAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(photos: MutableList<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LayoutItemGridBinding.bind(itemView)
        fun bind(data: Photo) {
            with(binding) {
                imgAvatar.load(data.src.portrait) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(android.R.color.darker_gray)
                    error(R.drawable.nodata)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size
}