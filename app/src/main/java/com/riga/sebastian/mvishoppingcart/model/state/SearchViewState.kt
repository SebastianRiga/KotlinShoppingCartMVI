package com.riga.sebastian.mvishoppingcart.model.state

import com.riga.sebastian.mvishoppingcart.model.data.Product
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.stringify

sealed class SearchViewState {
    object SearchNotYetStarted : SearchViewState()
    object Loading : SearchViewState()

    data class EmptyResult(val searchQuery: String) : SearchViewState() {
        @ImplicitReflectionSerializer
        override fun toString(): String = JSON.stringify(this)
    }

    data class SearchResult(val searchQuery: String, val result: List<Product>) :
        SearchViewState() {
        @ImplicitReflectionSerializer
        override fun toString(): String = JSON.stringify(this)
    }

    data class Error(val searchQuery: String, val kind: Throwable) : SearchViewState() {
        @ImplicitReflectionSerializer
        override fun toString(): String = JSON.stringify(this)
    }
}