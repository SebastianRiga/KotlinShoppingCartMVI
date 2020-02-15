package com.riga.sebastian.mvishoppingcart.model.interactor

import com.riga.sebastian.mvishoppingcart.model.data.Product
import com.riga.sebastian.mvishoppingcart.model.network.ProductEndpointSearchImpl
import com.riga.sebastian.mvishoppingcart.model.state.SearchViewState
import io.reactivex.Observable

class SearchInteractor(private val searchImpl: ProductEndpointSearchImpl) {

    fun search(query: String): Observable<SearchViewState> {
        if (query.isBlank() || query.isEmpty())
            return Observable.just(SearchViewState.SearchNotYetStarted)

        return searchImpl.searchForProduct(query)
            .map { products: List<Product> -> mapSearchResultToState(query, products)}
            .startWith(SearchViewState.Loading)
            .onErrorReturn { error: Throwable -> SearchViewState.Error(query, error) }
    }

    private fun mapSearchResultToState(query: String, searchResult: List<Product>): SearchViewState {
        if (searchResult.isEmpty()) return SearchViewState.EmptyResult(query)
        return SearchViewState.SearchResult(query, searchResult)
    }
}