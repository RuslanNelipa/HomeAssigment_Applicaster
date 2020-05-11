package com.nelipa.homeassigment.applicaster.managers.contract

import io.reactivex.Observable

interface SearchQueryConsumer {
    /**
     * Observe for query using this method. Results should be throttled
     * @return Observable casted from PublishSubject
     * */
    fun observeSearchQuery(): Observable<String>
}