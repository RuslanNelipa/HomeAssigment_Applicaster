package com.nelipa.homeassigment.applicaster.ui.video

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.databinding.ObservableBoolean
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.nelipa.homeassigment.applicaster.R
import com.nelipa.homeassigment.applicaster.base.BaseFragment
import com.nelipa.homeassigment.applicaster.databinding.FragmentVideoPostDetailsBinding


class VideoPostDetailsFragment : BaseFragment() {
    private val args: VideoPostDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentVideoPostDetailsBinding

    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var videoUrl: String? = null
    private val isValidUrl = ObservableBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        videoUrl = args.videoUrl
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentVideoPostDetailsBinding.inflate(inflater).also {
        binding = it
        binding.videoUrl = videoUrl
        binding.isUrlValid = isValidUrl
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvInvalidUrl.setOnClickListener {
            videoUrl = getString(R.string.valid_url_mp4)
            initializePlayer()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun initializePlayer() = context?.run {
        player = SimpleExoPlayer.Builder(this).build()
        binding.playerView.player = player
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        videoUrl?.let { player?.prepare(buildMediaSource(it), false, false) }
    }

    private fun buildMediaSource(url: String): MediaSource {
        isValidUrl.set(URLUtil.isValidUrl(url))

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, "exoplayer-applicaster")
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl))
    }

    private fun releasePlayer() = player?.let { _player ->
        playWhenReady = _player.playWhenReady
        playbackPosition = _player.currentPosition
        currentWindow = _player.currentWindowIndex
        _player.release()
        player = null
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        binding.playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}