package domain.model

data class RestaurantModel(
    val name: String,
    val status: String,
    val bestMatch: Double,
    val newest: Double,
    val ratingAverage: Double,
    val distance: Int,
    val popularity: Double,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int,
    val isFavorite: Boolean = false
)
