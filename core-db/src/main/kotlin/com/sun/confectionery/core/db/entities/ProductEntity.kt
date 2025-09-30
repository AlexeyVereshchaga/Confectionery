package com.sun.confectionery.core.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val formattedPrice: String,
    val imageUrl: String
)

