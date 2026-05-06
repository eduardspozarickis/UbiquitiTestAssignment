package lv.eduardspozarickis.ubiquititestassignment.data.remote.entity

data class Devices(
    val devices: List<ProductDTO>
)

data class ProductDTO(
    val deviceType: String,
    val images: Images,
    val id: String,
    val line: Line,
    val product: Product,
)

data class Images(
    val default: String,
)

data class Line(
    val id: String,
    val name: String,
)

data class Product(
    val name: String,
)