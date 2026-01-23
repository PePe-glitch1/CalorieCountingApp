package com.example.data.local.mapper

import com.example.data.local.entity.AddedProductsEntity
import com.example.domain.models.AddedProducts

fun AddedProductsEntity.toDomain(): AddedProducts {
    return AddedProducts(
        id = this.id,
        userId = this.userId,
        date = this.date,
        name = this.name,
        mass = this.mass,
        calories = this.calories,
        proteins = this.proteins,
        fats = this.fats,
        carbohydrates = this.carbohydrates,
    )
}

fun AddedProducts.toEntity(): AddedProductsEntity {
    return AddedProductsEntity(
        id = this.id,
        userId = this.userId,
        date = this.date,
        name = this.name,
        mass = this.mass,
        calories = this.calories,
        proteins = this.proteins,
        fats = this.fats,
        carbohydrates = this.carbohydrates,
    )
}

fun List<AddedProductsEntity>.toDomain(): List<AddedProducts> {
    return this.map { it.toDomain() }
}

fun List<AddedProducts>.toEntity(): List<AddedProductsEntity> {
    return this.map { it.toEntity() }
}
