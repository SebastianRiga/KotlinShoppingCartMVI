package com.riga.sebastian.mvishoppingcart.model.network

import com.riga.sebastian.mvishoppingcart.model.data.Product
import com.riga.sebastian.mvishoppingcart.model.dependencies.DependencyInjector
import io.reactivex.Observable

class ProductEndpointDecorator(private val endpoint: ProductEndpoint) {

    fun getProducts(pagination: Int): Observable<List<Product>> = endpoint.getProducts(pagination)

    @Suppress("UNCHECKED_CAST")
    fun getAllProducts(): Observable<List<Product>> {
        val sources: List<Observable<List<Product>>> =
            1.rangeTo(DependencyInjector.ENDPOINT_ENTITIES_PAGINATION_COUNT).map { page ->
                getProducts(page)
            }

        return Observable.zip(sources) { products -> products as List<Product> }
    }

    fun getProductsByCategory(category: String) = getAllProducts()
        .flatMap { products: List<Product> -> Observable.fromIterable(products) }
        .filter { product: Product -> product.category === category }
        .toList()
        .toObservable()

    fun getAllCategories(): Observable<List<String>> = getAllProducts()
        .flatMap { products: List<Product> -> Observable.fromIterable(products) }
        .map { product: Product -> product.category }
        .toList()
        .toObservable()

    fun getProduct(productId: Int) : Observable<Product> = getAllProducts()
        .flatMap { products: List<Product> -> Observable.fromIterable(products) }
        .filter { product: Product -> product.id == productId }
}