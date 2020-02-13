package com.riga.sebastian.mvishoppingcart.model.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ProductEndpoint {
    @GET
    fun getAllProducts(@Path("pagination")pagination: Int)
}