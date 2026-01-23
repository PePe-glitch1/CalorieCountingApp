package com.example.data.repository

import com.example.data.local.dao.AllProductsDao
import com.example.data.local.mapper.toDomain
import com.example.domain.models.AllProducts
import com.example.domain.repository.AllProductsRepository

class AllProductsRepositoryImpl(
    private val allProductsDao: AllProductsDao
): AllProductsRepository {

    override suspend fun getAllProductsByName(productName: String): AllProducts? {
        val entity = allProductsDao.getAllProductsByName(productName)
        return entity?.toDomain()
    }
}