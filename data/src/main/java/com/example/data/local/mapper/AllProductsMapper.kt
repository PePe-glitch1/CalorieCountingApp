package com.example.data.local.mapper

import com.example.data.local.entity.AllProductsEntity
import com.example.domain.models.AllProducts


fun AllProductsEntity.toDomain(): AllProducts {

    return AllProducts (
        id = this.id,
        name = this.name,
        caloriesIn1g = this.caloriesIn1g,
        proteinsIn1g = this.proteinsIn1g,
        fatsIn1g = this.fatsIn1g,
        carbohydratesIn1g = this.carbohydratesIn1g,
    )
}


fun List<AllProductsEntity>.toDomain(): List<AllProducts> {
    return this.map { it.toDomain() }
}