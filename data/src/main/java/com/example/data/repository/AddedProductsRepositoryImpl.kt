package com.example.data.repository

import com.example.data.local.dao.AddedProductsDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toEntity
import com.example.domain.models.AddedProducts
import com.example.domain.repository.AddedProductsRepository
import java.time.LocalDateTime

class AddedProductsRepositoryImpl(
    private val addedProductsDao: AddedProductsDao
): AddedProductsRepository {

    override suspend fun saveAddedProduct(params: AddedProducts): Long {
        val entity = params.toEntity()
        addedProductsDao.insert(entity)
        return params.id
    }

    override suspend fun getAllAddedProductByUserId(userId: Long): List<AddedProducts>? {
        val entities = addedProductsDao.getAddedProductsByUserId(userId)
        return entities?.map { it.toDomain() }
    }

    override suspend fun getAllAddedProductByUserIdAndDate(
        userId: Long,
        date: LocalDateTime
    ): List<AddedProducts>? {
        val entities = addedProductsDao.getAddedProductsByUserIdAndData(userId, date)
        return entities?.map { it.toDomain() }
    }

    override suspend fun getAddedProductById(id: Long): AddedProducts? {
        val entity = addedProductsDao.getAddedProductsById(id)
        return entity?.toDomain()
    }

    override suspend fun deleteAddedProductById(id: Long) {
        val entity = addedProductsDao.deleteById(id)
    }
}