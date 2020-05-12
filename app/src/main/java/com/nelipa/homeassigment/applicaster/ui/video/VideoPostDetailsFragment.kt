package com.nelipa.homeassigment.applicaster.ui.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.nelipa.homeassigment.applicaster.base.BaseFragment
import com.nelipa.homeassigment.applicaster.databinding.FragmentVideoPostDetailsBinding
import com.nelipa.homeassigment.applicaster.ext.getApplicationName

class VideoPostDetailsFragment : BaseFragment() {
    private val args: VideoPostDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentVideoPostDetailsBinding

    private val player = context?.let { SimpleExoPlayer.Builder(it).build() }
    private var videoSource: ProgressiveMediaSource? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentVideoPostDetailsBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVideoSource()
        initPlayerView()
    }

    override fun onPause() {
        super.onPause()
        player?.release()
    }

    private fun initPlayerView() {
        binding.playerPost.apply {
            this.player = player
            useController = true
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
        }

        player?.apply {
            seekTo(0)
            playWhenReady = true
        }
    }

    private fun setVideoSource() {
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(
            Util.getUserAgent(binding.root.context, context.getApplicationName()),
            null,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )

        val dataSourceFactory = DefaultDataSourceFactory(
            binding.root.context,
            null, httpDataSourceFactory
        )

        videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(args.videoUrl))
    }
}