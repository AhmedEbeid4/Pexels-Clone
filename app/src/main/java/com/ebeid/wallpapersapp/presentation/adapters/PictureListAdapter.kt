package com.ebeid.wallpapersapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.PictureItemLayoutBinding
import com.ebeid.wallpapersapp.domain.models.Picture

class PictureListAdapter(
    private val onClick: (picture: Picture) -> Unit,
    private val onSaveClick: (picture: Picture) -> Unit
) :
    ListAdapter<Picture, PictureListAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            PictureItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = getItem(position)
        holder.bind(currentItem)

    }

    inner class ViewHolder(private var binding: PictureItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(picture: Picture) {
            binding.apply {
                pexelsItem = picture
                saveBtn.setImageResource(R.drawable.baseline_bookmark_24)
                saveBtn.setOnClickListener {
                    onSaveClick(picture)
                }
                image.setOnClickListener { onClick(picture) }
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Picture>() {
        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }

    }

}