package com.ebeid.wallpapersapp.presentation.ui

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.databinding.FragmentImagePreviewBinding
import com.ebeid.wallpapersapp.utils.Downloader
import com.ebeid.wallpapersapp.utils.toast
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class ImagePreviewFragment : Fragment() {
    private var binding: FragmentImagePreviewBinding? = null
    private val args by navArgs<ImagePreviewFragmentArgs>()
    private var imageDownloader: Downloader? = null
    private var selectedOption = -1

    private val downloadCompleteReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadId != -1L) {
                    val downloadedImageUri =
                        imageDownloader!!.downloadManager!!.getUriForDownloadedFile(downloadId)
                    if (downloadedImageUri != null) {
                        wallpaperDialog( downloadedImageUri)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_image_preview,
                container,
                false
            )

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.imageUrl = args.imageUrl
        binding!!.downloadBtn.setOnClickListener {
            showOptionsDialog()
        }
    }

    private fun downloadImage(imageUrl: String, id: Int) {

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
                imageDownloader = Downloader(requireActivity().applicationContext)
                imageDownloader!!.downloadImage(imageUrl, id)
            }
        } catch (_: Exception) {

        }
    }

    private fun showOptionsDialog() {
        val options =
            arrayOf(
                "Original",
                "Large",
                "Medium",
                "Small",
                "Portrait",
                "Landscape",
                "Tiny",
                "Large 2x"
            )
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select an option")
        builder.setSingleChoiceItems(options, -1) { _, which ->
            selectedOption = which
        }
        builder.setPositiveButton("OK") { dialog, _ ->
            var imageUrl = ""
            if (selectedOption != -1) {
                Log.i("SELECTED_ITEM", options[selectedOption])
                when (selectedOption) {
                    0 -> imageUrl = args.imageUrl
                    1 -> imageUrl = args.largeUrl
                    2 -> imageUrl = args.medium
                    3 -> imageUrl = args.small
                    4 -> imageUrl = args.portrait
                    5 -> imageUrl = args.landscape
                    6 -> imageUrl = args.tiny
                    7 -> imageUrl = args.large2xUrl
                }
                selectedOption = -1
                downloadImage(imageUrl, args.pictureId)
                dialog.dismiss()
            } else {
                toast("You have to choose")
            }
        }
        val dialog = builder.create()

        dialog.show()
    }

    private fun wallpaperDialog(imageUri: Uri) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Set as Wallpaper")
        builder.setMessage("The Picture has been downloaded do you want to set it as Wallpaper")

        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            setWallpaper(requireActivity(), imageUri)
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun setWallpaper(context: Context, imageUri: Uri) {
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.setStream(context.contentResolver.openInputStream(imageUri))
            // wallpaperManager.setStream(context.contentResolver.openInputStream(imageUri), null, true, WallpaperManager.FLAG_LOCK)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            downloadCompleteReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(downloadCompleteReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        imageDownloader = null
    }
}