package com.sun.confectionery.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sun.confectionery.core.db.dao.ProductDao
import com.sun.confectionery.core.db.entities.ProductEntity


@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}