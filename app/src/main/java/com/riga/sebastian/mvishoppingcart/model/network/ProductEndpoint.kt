package com.riga.sebastian.mvishoppingcart.model.network

import com.riga.sebastian.mvishoppingcart.model.data.Product
import com.riga.sebastian.mvishoppingcart.model.dependencies.DependencyInjector
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductEndpoint {
    @GET("${DependencyInjector.ENDPOINT_ENTITIES_BASE_URL}/products{pagination}.json")
    fun getProducts(@Path("pagination")pagination: Int) : Observable<List<Product>>
}