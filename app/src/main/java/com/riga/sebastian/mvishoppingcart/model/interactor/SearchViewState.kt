package com.riga.sebastian.mvishoppingcart.model.interactor

import com.riga.sebastian.mvishoppingcart.model.data.Product

interface SearchViewState {
    sealed class SearchNotYetStarted : SearchViewState
    sealed class Loading : SearchViewState
    sealed class EmptyResult(val searchQuery: String) : SearchViewState
    sealed class SearchResult(val searchQuery: String, result: List<Product>) : SearchViewState
    sealed class Error(val searchQuery: String, val kind: Throwable) : SearchViewState
}