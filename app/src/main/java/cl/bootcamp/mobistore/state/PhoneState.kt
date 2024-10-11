package cl.bootcamp.mobistore.state

data class PhoneState(
    val name: String = "",
    val price: Int = 0,
    val image: String = "",
    val description: String = "",
    val lastPrice: Int = 0,
    val credit: Boolean = false
)
