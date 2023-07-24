package com.ebeid.wallpapersapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.domain.models.SavedItemsState.Status.FAIL
import com.ebeid.wallpapersapp.domain.models.SavedItemsState.Status.SUCCESS
import com.ebeid.wallpapersapp.presentation.viewmodel.SavesViewModel
import com.ebeid.wallpapersapp.utils.toast


class SplashFragment : Fragment() {

    private var viewModel: SavesViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SavesViewModel::class.java]
        viewModel!!.getSavedPictures()
        viewModel!!.savedPicturesList.observe(viewLifecycleOwner) {
            if (it.status == SUCCESS) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }else if (it.status == FAIL){
                toast(it.message)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel = null
    }
}