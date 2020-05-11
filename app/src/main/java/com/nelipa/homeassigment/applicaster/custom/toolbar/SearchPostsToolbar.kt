package com.nelipa.homeassigment.applicaster.custom.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.addTextChangedListener
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.nelipa.homeassigment.applicaster.databinding.ViewSearchToolbarBinding
import com.nelipa.homeassigment.applicaster.managers.contract.SearchQueryProvider

class SearchPostsToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var lastToolbarState = ToolbarState.COLLAPSED
    private var searchQueryProvider: SearchQueryProvider? = null

    private val binding =
        ViewSearchToolbarBinding.inflate(LayoutInflater.from(context), this, true).apply {
            queryProvider = searchQueryProvider
        }

    fun setSearchQueryProvider(searchQueryProvider: SearchQueryProvider) {
        this.searchQueryProvider = searchQueryProvider
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        expandToolbarByQuery()
    }

    private fun expandToolbarByQuery(){
        binding.etSearchQuery.addTextChangedListener { _editable ->
            _editable?.length?.let { _length ->
                if (_length > 0) expandToolbar() else collapseToolbar()
            }
        }
    }

    private fun collapseToolbar() = ConstraintSet()
        .takeIf { lastToolbarState != ToolbarState.COLLAPSED }
        ?.apply {
            TransitionManager.beginDelayedTransition(binding.root, ChangeBounds().apply {
                interpolator = OvershootInterpolator()
                duration =
                    context.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            })
            clone(binding.root)
            connect(
                binding.ivClearQuery.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            clear(binding.ivClearQuery.id, ConstraintSet.END)
            applyTo(binding.root)
        }
        ?.also { lastToolbarState = ToolbarState.COLLAPSED }

    private fun expandToolbar() = ConstraintSet()
        .takeIf { lastToolbarState != ToolbarState.EXPANDED }
        ?.apply {
            TransitionManager.beginDelayedTransition(binding.root, ChangeBounds().apply {
                interpolator = OvershootInterpolator()
                duration =
                    context.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            })
            clone(binding.root)
            connect(
                binding.ivClearQuery.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            clear(binding.ivClearQuery.id, ConstraintSet.END)
            applyTo(binding.root)
        }
        ?.also { lastToolbarState = ToolbarState.EXPANDED }

    enum class ToolbarState {
        COLLAPSED, EXPANDED
    }
}