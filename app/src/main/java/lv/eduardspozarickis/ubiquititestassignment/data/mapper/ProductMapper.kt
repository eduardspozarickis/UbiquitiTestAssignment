package lv.eduardspozarickis.ubiquititestassignment.data.mapper

import lv.eduardspozarickis.ubiquititestassignment.data.local.dao.ProductDao
import lv.eduardspozarickis.ubiquititestassignment.data.remote.entity.ProductDTO
import lv.eduardspozarickis.ubiquititestassignment.domain.entity.Product

fun ProductDTO.toDomain(): Product {
    return Product(
        id = this.id,
        deviceType = this.deviceType,
        line = this.line.name,
        name = this.product.name,
        imageId = this.images.default,
    )
}

fun ProductDTO.toData(): lv.eduardspozarickis.ubiquititestassignment.data.local.entity.Product {
    return lv.eduardspozarickis.ubiquititestassignment.data.local.entity.Product(
        id = this.id,
        deviceType = this.deviceType,
        line = this.line.name,
        name = this.product.name,
        imageId = this.images.default,
    )
}

fun lv.eduardspozarickis.ubiquititestassignment.data.local.entity.Product.toDomain(): Product {
    return Product(
        id = this.id,
        deviceType = this.deviceType,
        line = this.line,
        name = this.name,
        imageId = this.imageId,
    )
}