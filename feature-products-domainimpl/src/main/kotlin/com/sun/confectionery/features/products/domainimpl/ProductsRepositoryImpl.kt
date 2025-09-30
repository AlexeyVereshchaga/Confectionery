package com.sun.confectionery.features.products.domainimpl

import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.db.dao.ProductDao
import com.sun.confectionery.core.db.entities.ProductEntity
import com.sun.confectionery.features.products.data.ProductsApi
import com.sun.confectionery.features.products.domain.ProductModel
import com.sun.confectionery.features.products.domain.ProductRepository


class ProductsRepositoryImpl(
    private val api: ProductsApi,
    private val dao: ProductDao
) : ProductRepository {

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

    override suspend fun getAllLocal(): List<ProductModel> {
        return dao.getAll().map {
            ProductModel(it.id, it.name, it.description, it.price, it.formattedPrice, it.imageUrl)
        }
    }

    override suspend fun getById(id: String): ProductModel? {
        return dao.getById(id)?.let {
            ProductModel(it.id, it.name, it.description, it.price, it.formattedPrice, it.imageUrl)
        }
    }
}
