package com.example.domain.repository

import com.example.domain.models.AllProducts

interface AllProductsRepository {

    suspend fun getAllProductsByName(productName: String): AllProducts?

}