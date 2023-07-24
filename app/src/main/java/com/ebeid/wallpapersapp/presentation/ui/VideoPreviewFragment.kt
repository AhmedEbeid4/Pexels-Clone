package com.ebeid.wallpapersapp.presentation.ui

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.ebeid.wallpapersapp.databinding.FragmentVideoPreviewBinding
import com.ebeid.wallpapersapp.utils.Downloader
import com.google.android.material.snackbar.Snackbar

class VideoPreviewFragment : Fragment() {

    private var binding: FragmentVideoPreviewBinding? = null
    private val args by navArgs<VideoPreviewFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentVideoPreviewBinding.inflate(layoutInflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.video.setVideoUrl(args.videoModel.videoUrl)
        binding!!.downloadBtn.setOnClickListener {
            downloadVideo(
                args.videoModel.videoUrl,
                args.videoModel.videoId
            )
        }
    }

    private fun downloadVideo(videoUrl: String, id: Int) {

        try {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission()
            } else {
                Snackbar.make(requireView(), "Downloading....", Snackbar.LENGTH_SHORT)
                    .show()
                val videoDownloader = Downloader(requireActivity().applicationContext)
                videoDownloader.downloadVideo(videoUrl, id)
            }
        } catch (_: Exception) {

        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    override fun onPause() {
        super.onPause()
        if (args.videoModel.width > args.videoModel.height) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    override fun onResume() {
        super.onResume()
        if (args.videoModel.width > args.videoModel.height) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding!!.video.releasePlayer()
        binding = null
    }
}