package com.nelipa.homeassigment.applicaster.managers.contract

import androidx.databinding.ObservableField

interface SearchQueryProvider {
    /**
     * Used for 2-way binding for search query
     * */
    val searchQuery: ObservableField<String>
}