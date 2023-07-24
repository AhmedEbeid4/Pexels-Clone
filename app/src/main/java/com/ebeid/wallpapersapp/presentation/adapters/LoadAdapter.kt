package com.ebeid.wallpapersapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.LoadMoreLayoutBinding
import com.ebeid.wallpapersapp.utils.hide
import com.ebeid.wallpapersapp.utils.show

class LoadAdapter(
    private val forSearch: Boolean,
    private val itemPadding: Int?,
    private val retry: () -> Unit,
    ) : LoadStateAdapter<LoadAdapter.ViewHolder>() {

    private lateinit var binding: LoadMoreLayoutBinding

    inner class ViewHolder(val retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryBtn.setOnClickListener { retry() }
        }

        fun setData(states: LoadState) {
            binding.apply {
                if (forSearch) {
                    loadMoreParent.setBackgroundResource(R.color.white)
                    loadMoreParent.setPadding(
                        loadMoreParent.paddingLeft,
                        loadMoreParent.paddingTop,
                        loadMoreParent.paddingRight,
                        itemPadding!!
                    )
                    val layoutParams = loadMoreParent.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.topMargin = 0
                    loadMoreParent.layoutParams = layoutParams
                }

                if (states is LoadState.Loading) {
                    loadMoreProgress.show()
                } else {
                    loadMoreProgress.hide()
                }
                if (states is LoadState.Error) {
                    errorTextView.show()
                    retryBtn.show()
                } else {
                    errorTextView.hide()
                    retryBtn.hide()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadAdapter.ViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadAdapter.ViewHolder {
        binding = LoadMoreLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }

}