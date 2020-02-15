package com.riga.sebastian.mvishoppingcart.model.network

import com.riga.sebastian.mvishoppingcart.model.data.Product
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ProductEndpointSearchImpl(private val endpoint: ProductEndpointDecorator) {

    fun searchForProduct(query: String): Observable<List<Product>> {
        if (query.isEmpty() || query.isBlank()) return Observable.just(listOf())

        return endpoint.getAllProducts()
            .delay(1000, TimeUnit.MILLISECONDS)
            .flatMap { products: List<Product> -> Observable.fromIterable(products) }
            .filter { product: Product -> isProductMatchingQuery(product, query) }
            .toList()
            .toObservable()
    }

    private fun isProductMatchingQuery(product: Product, query: String): Boolean {
        val queryWords = query.split(" ")

        queryWords.forEach { word: String ->
            return (
                    product.name.contains(word) ||
                    product.category.contains(word) ||
                    product.description.contains(word)
            )
        }

        return false
    }
}