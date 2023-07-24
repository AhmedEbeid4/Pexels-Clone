package com.ebeid.wallpapersapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.PictureItemLayoutBinding
import com.ebeid.wallpapersapp.databinding.VideoItemLayoutBinding
import com.ebeid.wallpapersapp.domain.models.PexelsModel
import com.ebeid.wallpapersapp.domain.models.VideoModel
import com.ebeid.wallpapersapp.presentation.ui.VideoPlayerView
import com.ebeid.wallpapersapp.utils.Constants
import com.ebeid.wallpapersapp.utils.hide
import com.ebeid.wallpapersapp.utils.show

class ItemAdapter
constructor(
    private val itemType: Int,
    private val showPicture: Int,
    private val onClick: (item: PexelsModel) -> Unit,
    private val hasSaved: ((pexelsModel: PexelsModel) -> Boolean)?,
    private val onSaveClick: ((item: PexelsModel) -> Unit)?,
) :
    PagingDataAdapter<PexelsModel, ItemAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ViewBinding
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = if (itemType == Constants.VIDEO_ITEM) {
            VideoItemLayoutBinding.inflate(inflater, parent, false)
        } else {
            PictureItemLayoutBinding.inflate(inflater, parent, false)
        }
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        var videoPlayerView: VideoPlayerView? = null
        fun bind(item: PexelsModel) {

            if (itemType == Constants.VIDEO_ITEM) {
                (binding as VideoItemLayoutBinding).apply {
                    videoPlayerView = video
                    pexelsItem = item

                    if (showPicture == Constants.PICTURE_ONLY) {
                        goBtn.setOnClickListener { onClick(item) }
                        videoPlayerView!!.hide()
                        videoImage.show()
                    } else if (showPicture == Constants.VIDEO_ONLY) {
                        videoPlayerView!!.setVideoUrl((item as VideoModel).videoUrl)
                        goBtn.setOnClickListener {
                            videoPlayerView!!.stopVideo()
                            videoPlayerView!!.releasePlayer()
                            onClick(item)
                        }
                        videoPlayerView!!.startVideo()
                    }
                }
            } else {
                (binding as PictureItemLayoutBinding).apply {
                    if (hasSaved!!(item)) {
                        saveBtn.setImageResource(R.drawable.baseline_bookmark_24)
                    } else {
                        saveBtn.setImageResource(R.drawable.baseline_bookmark_border_24)
                    }
                    pexelsItem = item
                    saveBtn.setOnClickListener {
                        onSaveClick!!(item)
                        if (hasSaved!!(item)) {
                            saveBtn.setImageResource(R.drawable.baseline_bookmark_24)
                        } else {
                            saveBtn.setImageResource(R.drawable.baseline_bookmark_border_24)
                        }
                    }
                    image.setOnClickListener { onClick(item) }
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (itemType == Constants.VIDEO_ITEM && showPicture == Constants.VIDEO_ONLY) {
            holder.videoPlayerView!!.stopVideo()
            holder.videoPlayerView!!.releasePlayer()
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        // Resume video playback when the view becomes visible
        if (itemType == Constants.VIDEO_ITEM && showPicture == Constants.VIDEO_ONLY) {
            holder.videoPlayerView!!.startVideo()
        }
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<PexelsModel>() {
            override fun areItemsTheSame(oldItem: PexelsModel, newItem: PexelsModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PexelsModel, newItem: PexelsModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}