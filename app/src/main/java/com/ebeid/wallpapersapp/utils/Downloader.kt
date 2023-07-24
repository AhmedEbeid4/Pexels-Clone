package com.ebeid.wallpapersapp.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore

class Downloader
constructor(private val context: Context) {
    var downloadManager: DownloadManager? = null
    fun downloadImage(imageUrl: String, title: Int) {
        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(imageUrl)
        val request = DownloadManager.Request(uri)
            .setMimeType("image/jpeg")
            .setTitle("${title}.jpg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "${title}.jpg"
            )
        downloadManager!!.enqueue(request)
    }

    fun downloadVideo(videoUrl: String, title: Int) {
        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(videoUrl)
        val request = DownloadManager.Request(uri)
            .setMimeType("video/mp4")
            .setTitle("${title}.mp4")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${title}.mp4"
            )
        downloadManager!!.enqueue(request)
    }
}