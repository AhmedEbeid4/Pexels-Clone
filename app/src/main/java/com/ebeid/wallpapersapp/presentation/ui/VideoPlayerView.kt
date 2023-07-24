package com.ebeid.wallpapersapp.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.ebeid.wallpapersapp.R
import com.ebeid.wallpapersapp.utils.hide
import com.ebeid.wallpapersapp.utils.show
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlayerView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val playerView: PlayerView
    private var player: SimpleExoPlayer? = null
    private var progressBar: ProgressBar? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.video_player_layout, this, true)
        playerView = findViewById(R.id.playerView)
        progressBar = findViewById(R.id.progress_circular)
    }

    fun setVideoUrl(videoUrl: String) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        player = SimpleExoPlayer.Builder(context).build()
        player?.setMediaItem(mediaItem)
        playerView.player = player
        player?.addListener(PlayerEventListener())
    }

    fun startVideo() {
        player?.play()
    }



    fun stopVideo() {
        player?.pause()
    }

    fun releasePlayer() {
        player?.release()
        player = null
    }

    private inner class PlayerEventListener : Player.Listener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    progressBar!!.show()
                }

                Player.STATE_READY -> {
                    progressBar!!.hide()
                }
            }
        }
    }
}