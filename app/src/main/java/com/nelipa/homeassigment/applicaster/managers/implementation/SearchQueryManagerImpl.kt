package com.nelipa.homeassigment.applicaster.managers.implementation

import androidx.databinding.ObservableField
import com.nelipa.homeassigment.applicaster.ext.onPropertyChanged
import com.nelipa.homeassigment.applicaster.managers.contract.SearchQueryConsumer
import com.nelipa.homeassigment.applicaster.managers.contract.SearchQueryProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchQueryManagerImpl : SearchQueryProvider, SearchQueryConsumer {

    override val searchQuery = ObservableField("")

    init {
        observeQueryChange()
    }

    private val searchQuerySubject = PublishSubject.create<String>()

    override fun onQueryCleared() = searchQuerySubject.onNext("")

    override fun observeSearchQuery(): Observable<String> = searchQuerySubject
        .startWith("")
        .distinctUntilChanged()
        .throttleWithTimeout(500, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())

    private fun onSearchQueryChanged(query: String) = searchQuerySubject.onNext(query)

    private fun observeQueryChange() = searchQuery.onPropertyChanged { _field ->
        _field.get()?.let(::onSearchQueryChanged)
    }
}