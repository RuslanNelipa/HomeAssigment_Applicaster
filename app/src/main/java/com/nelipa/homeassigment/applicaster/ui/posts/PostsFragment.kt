package com.nelipa.homeassigment.applicaster.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nelipa.homeassigment.applicaster.R
import com.nelipa.homeassigment.applicaster.BR
import com.nelipa.homeassigment.applicaster.base.BaseFragment
import com.nelipa.homeassigment.applicaster.custom.MultiTypeListAdapter
import com.nelipa.homeassigment.applicaster.databinding.FragmentPostsBinding
import com.nelipa.homeassigment.applicaster.ext.observeData
import com.nelipa.homeassigment.applicaster.models.PostItem
import kotlin.reflect.KClass

class PostsFragment : BaseFragment() {
    private val viewModel by viewModels<PostsViewModel>()

    private lateinit var binding: FragmentPostsBinding

    private val itemLayouts = mapOf<KClass<*>, Int>(
        PostItem.PostLinkItem::class to R.layout.item_post_link,
        PostItem.PostVideoItem::class to R.layout.item_post_video
    )
    private val postsAdapter = MultiTypeListAdapter(itemLayouts, BR.item)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        observePosts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPostsBinding.inflate(inflater).also {
        binding = it
        binding.viewModel = viewModel
    }.getRoot()


    private fun initRv() {
        binding.rvPosts.apply {
            adapter = postsAdapter.apply { setHasStableIds(true) }
        }
    }

    private fun observePosts() {
        viewModel.postsLiveData()
            .observeData(viewLifecycleOwner) {
//                postsAdapter.setItems(it)
            }
    }
}
