package com.nelipa.homeassigment.applicaster.managers.implementation

import androidx.databinding.ObservableField
import com.nelipa.homeassigment.applicaster.ext.onPropertyChanged
import com.nelipa.homeassigment.applicaster.managers.contract.SearchQueryConsumer
import com.nelipa.homeassigment.applicaster.managers.contract.SearchQueryProvider
import com.nelipa.homeassigment.applicaster.utils.logd
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchQueryAdapter : SearchQueryProvider, SearchQueryConsumer {

    override val searchQuery = ObservableField("")

    init {
        observeQueryChange()
    }

    private val searchQuerySubject = PublishSubject.create<String>()

    override fun observeSearchQuery(): Observable<String> = searchQuerySubject
        .startWith("")
        .distinctUntilChanged()
        .throttleWithTimeout(500, TimeUnit.MILLISECONDS)

    private fun onSearchQueryChanged(query: String) = searchQuerySubject.onNext(query)

    private fun observeQueryChange() = searchQuery.onPropertyChanged { _field ->
        _field.get()?.let(::onSearchQueryChanged)
    }
}