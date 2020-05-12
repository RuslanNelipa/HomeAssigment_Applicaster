package com.nelipa.homeassigment.applicaster.ui.link

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.nelipa.homeassigment.applicaster.base.BaseFragment
import com.nelipa.homeassigment.applicaster.databinding.FragmentLinkPostDetailsBinding

class LinkPostDetailsFragment : BaseFragment() {
    private val args: LinkPostDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentLinkPostDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLinkPostDetailsBinding.inflate(inflater).also {
        binding = it
        binding.url = args.postUrl
    }.root
}