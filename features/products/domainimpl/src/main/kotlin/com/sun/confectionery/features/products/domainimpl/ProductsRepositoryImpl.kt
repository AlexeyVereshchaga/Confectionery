package com.sun.confectionery.features.products.domainimpl

import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.db.dao.ProductDao
import com.sun.confectionery.core.db.entities.ProductEntity
import com.sun.confectionery.products.data.ProductsApi
import com.sun.confectionery.products.domain.model.Product
import com.sun.confectionery.products.domain.ProductsRepository

class ProductsRepositoryImpl(
    private val api: ProductsApi,
    private val dao: ProductDao
) : ProductsRepository {

    override suspend fun refreshProductsFromNetwork(): Outcome<Unit> {
        return try {
            val list = api.fetchAll()
            val entities = list.map {
                ProductEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    price = it.price,
                    formattedPrice = it.formattedPrice,
                    imageUrl = it.imageUrl
                )
            }
            dao.insertAll(entities)
            Outcome.Success(Unit)
        } catch (ex: Throwable) {
            Outcome.Error(ex)
        }
    }

    override suspend fun getAllLocal(): List<Product> {
        return dao.getAll().map {
            Product(it.id, it.name, it.description, it.price, it.formattedPrice, it.imageUrl)
        }
    }

    override suspend fun getById(id: String): Product? {
        return dao.getById(id)?.let {
            Product(it.id, it.name, it.description, it.price, it.formattedPrice, it.imageUrl)
        }
    }
}
