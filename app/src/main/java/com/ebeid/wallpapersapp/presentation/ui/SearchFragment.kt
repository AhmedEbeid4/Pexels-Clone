package com.ebeid.wallpapersapp.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.ebeid.wallpapersapp.utils.Constants.VIDEO_ITEM
import com.ebeid.wallpapersapp.utils.Constants.PICTURE_ONLY
import com.ebeid.wallpapersapp.utils.Constants.PICTURE_ITEM
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.FragmentSearchBinding
import com.ebeid.wallpapersapp.domain.models.PexelsModel
import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.models.VideoModel
import com.ebeid.wallpapersapp.presentation.adapters.ItemAdapter
import com.ebeid.wallpapersapp.presentation.adapters.LoadAdapter
import com.ebeid.wallpapersapp.presentation.viewmodel.SavesViewModel
import com.ebeid.wallpapersapp.presentation.viewmodel.SearchViewModel
import com.ebeid.wallpapersapp.utils.Constants.NETHER_VIDEO_NOR_PICTURE
import com.ebeid.wallpapersapp.utils.hide
import com.ebeid.wallpapersapp.utils.show
import kotlin.math.roundToInt

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private var binding: FragmentSearchBinding? = null
    private lateinit var pictureAdapter: ItemAdapter
    private lateinit var videoAdapter: ItemAdapter
    private lateinit var savesViewModel: SavesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        savesViewModel = ViewModelProvider(requireActivity())[SavesViewModel::class.java]
        setupAdapters()
        setupRecyclerViews()
        setupSearchView()
        observeData()
    }

    private fun setupAdapters() {
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
        videoAdapter = ItemAdapter(
            VIDEO_ITEM, PICTURE_ONLY, {
                navigateToVideoPreview(it)
            }, null, null
        )
    }

    private fun setupRecyclerViews() {
        val paddingBottom = resources.getDimension(com.intuit.sdp.R.dimen._150sdp).roundToInt()
        binding!!.picturesRecyclerView.adapter =
            pictureAdapter.withLoadStateFooter(
                LoadAdapter(
                    true,
                    paddingBottom
                ) { pictureAdapter.retry() })

        binding!!.videosRecyclerView.adapter =
            videoAdapter.withLoadStateFooter(
                LoadAdapter(
                    true,
                    paddingBottom
                ) { videoAdapter.retry() })

        binding!!.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.picturesRadioBtn) {
                binding!!.apply {
                    picturesRecyclerView.show()
                    videosRecyclerView.hide()
                }
            } else {
                binding!!.apply {
                    picturesRecyclerView.hide()
                    videosRecyclerView.show()
                }
            }
        }
    }

    private fun setupSearchView() {
        binding!!.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty() && query.trim().isNotEmpty()) {
                    if (query.trim() != viewModel.lastQuery) {
                        viewModel.searchQuery(query.trim())
                    }
                    closeKeyboard()
                    binding!!.picturesRecyclerView.smoothScrollToPosition(0)
                    binding!!.videosRecyclerView.smoothScrollToPosition(0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeData() {
        viewModel.pictures.observe(viewLifecycleOwner) { pagingData ->
            pictureAdapter.submitData(
                viewLifecycleOwner.lifecycle, pagingData as PagingData<PexelsModel>
            )
        }

        viewModel.videos.observe(viewLifecycleOwner) { pagingData ->
            videoAdapter.submitData(
                viewLifecycleOwner.lifecycle, pagingData as PagingData<PexelsModel>
            )
        }
    }

    private fun navigateToImagePreview(item : Picture){
        try{
            val action = SearchFragmentDirections.actionSearchFragmentToImagePreviewFragment(
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
        }catch (_:Exception){}
    }

    private fun navigateToVideoPreview(item: PexelsModel){
        try {
            val action =
                SearchFragmentDirections.actionSearchFragmentToVideoPreviewFragment(item as VideoModel)
            findNavController().navigate(action)
        }catch (_:Exception){}
    }
    private fun closeKeyboard() {
        val view = requireView()
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}