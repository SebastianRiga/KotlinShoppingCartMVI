package com.riga.sebastian.mvishoppingcart.model.dependencies

import com.riga.sebastian.mvishoppingcart.model.interactor.SearchInteractor
import com.riga.sebastian.mvishoppingcart.model.network.ProductEndpoint
import com.riga.sebastian.mvishoppingcart.model.network.ProductEndpointDecorator
import com.riga.sebastian.mvishoppingcart.model.network.ProductEndpointSearchImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Suppress("unused")
object DependencyInjector {
    // # Endpoint constants
    // ## URLs
    // ### Private
    private const val ENDPOINT_BASE_URL = "https://raw.githubusercontent.com"
    private const val ENDPOINT_PROJECT = "SebastianRiga/KotlinShoppingCartMVI"
    private const val ENDPOINT_BRANCH = "master"

    // ### Publicly exposed
    const val ENDPOINT_ENTITIES_BASE_URL = "$ENDPOINT_PROJECT/$ENDPOINT_BRANCH/app/server/api"
    const val ENDPOINT_IMAGE_BASE_URL =
        "$ENDPOINT_BASE_URL/$ENDPOINT_PROJECT/$ENDPOINT_BRANCH/app/server/images"

    // ### Pagination - For demo purposes only! In a real environment you would of course need to
    // query the pagination.
    const val ENDPOINT_ENTITIES_PAGINATION_COUNT = 4

    // # Network
    // ## Setup
    private val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val networkClient = Retrofit.Builder()
        .baseUrl(ENDPOINT_BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val endpoint: ProductEndpoint = networkClient.create(ProductEndpoint::class.java)
    private val endpointDecorator = ProductEndpointDecorator(endpoint)

    // ## Services

    /**
     * Creates a new [ProductEndpointSearchImpl]
     * @return New instance of a [ProductEndpointSearchImpl]
     */
    fun newEndpointSearchImpl(): ProductEndpointSearchImpl =
        ProductEndpointSearchImpl(endpointDecorator)

    // # Interactors

    /**
     * Creates a new [SearchInteractor]
     * @return New instance of a [SearchInteractor]
     */
    fun newSearchInteractor(): SearchInteractor = SearchInteractor(newEndpointSearchImpl())

}