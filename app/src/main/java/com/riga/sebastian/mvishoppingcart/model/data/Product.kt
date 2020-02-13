@file:Suppress("unused")

package com.riga.sebastian.mvishoppingcart.model.data

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.stringify
import java.util.*

data class Product(
    val id: Int,
    val image: String,
    val name: String,
    val category: String,
    val description: String,
    val price: Double
) {
    constructor() : this(-1, "", "", "", "", 0.0)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherProduct = other as? Product ?: return false

        if (id != otherProduct.id) return false
        if (image != otherProduct.image) return false
        if (name != otherProduct.name) return false
        if (category != otherProduct.category) return false
        if (description != otherProduct.description) return false
        return price != otherProduct.price
    }

    override fun hashCode(): Int = Objects.hash(id, image, name, category, description, price)

    @ImplicitReflectionSerializer
    override fun toString(): String = JSON.stringify(this)
}