package com.ebeid.wallpapersapp.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ebeid.wallpapersapp.utils.Constants.VIDEO_ITEM
import com.ebeid.wallpapersapp.utils.Constants.VIDEO_ONLY
import com.ebeid.wallpapersapp.utils.Constants.PICTURE_ITEM
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.FragmentHomeBinding
import com.ebeid.wallpapersapp.domain.models.PexelsModel
import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.models.VideoModel
import com.ebeid.wallpapersapp.presentation.adapters.ItemAdapter
import com.ebeid.wallpapersapp.presentation.adapters.LoadAdapter
import com.ebeid.wallpapersapp.presentation.viewmodel.HomeViewModel
import com.ebeid.wallpapersapp.presentation.viewmodel.SavesViewModel
import com.ebeid.wallpapersapp.utils.Constants.NETHER_VIDEO_NOR_PICTURE
import com.ebeid.wallpapersapp.utils.hide
import com.ebeid.wallpapersapp.utils.show
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var pictureAdapter: ItemAdapter
    private lateinit var videoAdapter: ItemAdapter
    private lateinit var savesViewModel: SavesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        savesViewModel = ViewModelProvider(requireActivity())[SavesViewModel::class.java]
        setupAdapter()
        setupRecyclerViews()
        observeData()
    }
    private fun setupAdapter(){
        pictureAdapter = ItemAdapter(PICTURE_ITEM, NETHER_VIDEO_NOR_PICTURE, {
            navigateToImagePreview(it as Picture)
        }, {
            savesViewModel.hasSaved(it)
        }, {
            if (!savesViewModel.hasSaved(it)) {
                savesViewModel.savePicture(it as Picture)
            } else {
                savesViewModel.unSavePicture(it as Picture)
            }
        })

        videoAdapter = ItemAdapter(VIDEO_ITEM, VIDEO_ONLY, {
            navigateToVideoPreview(it)
        }, null, null)
    }

    private fun setupRecyclerViews(){
        binding!!.picturesRecyclerView.adapter =
            pictureAdapter.withLoadStateFooter(LoadAdapter(false,null) { pictureAdapter.retry() })

        binding!!.videosRecyclerView.adapter =
            videoAdapter.withLoadStateFooter(LoadAdapter(false,null) { videoAdapter.retry() })

        binding!!.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding!!.apply {
                if (checkedId == R.id.picturesRadioBtn) {
                    picturesRecyclerView.show()
                    videosRecyclerView.hide()
                } else {
                    picturesRecyclerView.hide()
                    videosRecyclerView.show()
                }
            }
        }
    }

    private fun observeData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.picturesList.collect {
                pictureAdapter.submitData(it as PagingData<PexelsModel>)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videosList.collect {
                videoAdapter.submitData(it as PagingData<PexelsModel>)
            }
        }
    }

    private fun navigateToImagePreview(item : Picture){
        try {
            val action = HomeFragmentDirections.actionHomeFragmentToImagePreviewFragment(
                item.primaryImageUrl,
                item.id,
                item.large,
                item.large2xPicture,
                item.small,
                item.medium,
                item.portrait,
                item.landscape,
                item.tiny
            )
            findNavController().navigate(action)
        }catch (_:Exception){ }

    }

    private fun navigateToVideoPreview(item:PexelsModel){
        try {
            val action =
                HomeFragmentDirections.actionHomeFragmentToVideoPreviewFragment(item as VideoModel)
            findNavController().navigate(action)
        }catch (_:Exception){}
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}