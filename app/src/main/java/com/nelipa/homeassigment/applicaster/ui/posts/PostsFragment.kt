package com.nelipa.homeassigment.applicaster.ui.posts

import android.os.Bundle
import android.view.View
import com.nelipa.homeassigment.applicaster.R
import com.nelipa.homeassigment.applicaster.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : BaseFragment(R.layout.fragment_posts) {
    private val viewModel by viewModels<PostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLoad.setOnClickListener {
            viewModel.loadPosts()
        }
    }
}
