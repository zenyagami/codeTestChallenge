package data.entities

data class SortingDto(
    val bestMatch: Double,
    val newest: Double,
    val ratingAverage: Double,
    val distance: Int,
    val popularity: Double,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int
)
