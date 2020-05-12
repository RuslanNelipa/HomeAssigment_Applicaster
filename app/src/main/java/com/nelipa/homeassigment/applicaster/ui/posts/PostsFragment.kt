package com.nelipa.homeassigment.applicaster.ui.posts

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nelipa.homeassigment.applicaster.R
import com.nelipa.homeassigment.applicaster.BR
import com.nelipa.homeassigment.applicaster.base.BaseFragment
import com.nelipa.homeassigment.applicaster.custom.MultiTypeListAdapter
import com.nelipa.homeassigment.applicaster.databinding.FragmentPostsBinding
import com.nelipa.homeassigment.applicaster.ext.observeData
import com.nelipa.homeassigment.applicaster.ext.snack
import com.nelipa.homeassigment.applicaster.models.PostItem
import com.nelipa.homeassigment.applicaster.utils.Event
import kotlin.reflect.KClass

class PostsFragment : BaseFragment() {
    private val viewModel by viewModels<PostsViewModel>()

    private lateinit var binding: FragmentPostsBinding
    private var snack: Snackbar? = null

    private val itemLayouts = mapOf<KClass<*>, Int>(
        PostItem.PostLinkItem::class to R.layout.item_post_link,
        PostItem.PostVideoItem::class to R.layout.item_post_video,
        PostItem.PostUnsupportedItem::class to R.layout.item_post_unsupported
    )
    private val postsAdapter = MultiTypeListAdapter(itemLayouts, BR.item)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPostsBinding.inflate(inflater).also {
        binding = it
        binding.viewModel = viewModel
        binding.searchPostsToolbar.searchQueryProvider = viewModel.searchQueryProvider()
        binding.lifecycleOwner = viewLifecycleOwner
    }.getRoot()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        observeViewModelData()
        handleBackClick()
        handleSwipeToRefresh()
    }

    override fun onPause() {
        super.onPause()
        snack?.dismiss()
    }

    private fun initRv() {
        binding.rvPosts.apply {
            layoutManager = GridLayoutManager(context, 1)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = postsAdapter
        }
    }

    private fun observeViewModelData() {
        viewModel.postsLiveData().observeData(viewLifecycleOwner, ::onPostsReceived)
        viewModel.errorLiveData().observeData(viewLifecycleOwner, ::onErrorReceived)
        viewModel.isLoadingLiveData().observeData(viewLifecycleOwner, ::onLoadingStateChanged)
    }

    private fun onLoadingStateChanged(isLoading: Boolean) {
        binding.refreshPostsLayout.isRefreshing = isLoading
    }

    private fun handleSwipeToRefresh(){
        binding.refreshPostsLayout.setOnRefreshListener {
            snack?.dismiss()
            viewModel.refreshPosts()
        }
    }

    private fun handleBackClick() = overrideBackClick {
        if (!binding.searchPostsToolbar.isSearchQueryEmpty()) {
            binding.searchPostsToolbar.clearSearchQuery()
        } else {
            activity?.onBackPressed()
        }
    }

    private fun onErrorReceived(error: Event<PostsViewModel.PostsError>) {
        error.takeIf { it.hasBeenHandled.not() }
            ?.getContentIfNotHandled()
            ?.let { _error ->
                snack = when (_error) {
                    PostsViewModel.PostsError.NetworkError -> binding.root.snack(
                        getString(R.string.failed_to_fetch_data),
                        getString(R.string.retry)
                    ) {
                        snack?.dismiss()
                        viewModel.loadPosts()
                    }

                    is PostsViewModel.PostsError.Generic -> binding.root.snack(_error.message)
                }
                snack?.show()
            }
    }

    private fun onPostsReceived(posts: List<Any>) {
        postsAdapter.setItems(posts)
        showToolbar()
    }

    private fun showToolbar() = ConstraintSet().apply {
        TransitionManager.beginDelayedTransition(binding.root, ChangeBounds())
        clone(binding.root)
        connect(
            binding.searchPostsToolbar.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        clear(binding.searchPostsToolbar.id, ConstraintSet.BOTTOM)
        applyTo(binding.root)
    }
}
