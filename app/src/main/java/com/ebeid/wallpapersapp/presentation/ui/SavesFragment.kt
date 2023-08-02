package com.ebeid.wallpapersapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.FragmentHomeBinding
import com.ebeid.wallpapersapp.databinding.FragmentSavesBinding
import com.ebeid.wallpapersapp.presentation.adapters.PictureListAdapter
import com.ebeid.wallpapersapp.presentation.viewmodel.SavesViewModel


class SavesFragment : Fragment() {

    private var binding: FragmentSavesBinding? = null
    private lateinit var viewModel: SavesViewModel
    private lateinit var adapter: PictureListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavesBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SavesViewModel::class.java]
        setupAdapter()
        observeList()
        binding!!.picturesRecyclerView.adapter = adapter
    }

    private fun setupAdapter() {
        adapter = PictureListAdapter({
            try {
                val item = it
                val action = SavesFragmentDirections.actionSavesFragmentToImagePreviewFragment(
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
        }, {
            viewModel.unSaveSavedPicture(it)
        })
    }

    private fun observeList() =
        viewModel.savedPicturesList.observe(viewLifecycleOwner) { adapter.submitList(it.data) }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}