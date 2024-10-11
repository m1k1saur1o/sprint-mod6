package cl.bootcamp.mobistore.model

data class ApiResponse(
    val results: ArrayList<PhoneApi>
)

data class PhoneApi(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String
)

data class PhoneDetailsApi(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String,
    val description: String,
    val lastPrice: Int,
    val credit: Boolean
)
