package cl.bootcamp.mobistore.dataSource

import cl.bootcamp.mobistore.model.Phone
import cl.bootcamp.mobistore.model.PhoneApi
import cl.bootcamp.mobistore.model.PhoneDetails
import cl.bootcamp.mobistore.model.PhoneDetailsApi
import cl.bootcamp.mobistore.util.Constants.Companion.DETAILS_ENDPOINT
import cl.bootcamp.mobistore.util.Constants.Companion.PRODUCTS_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestDataSource {

    @GET(PRODUCTS_ENDPOINT)
    suspend fun getPhones(): List<PhoneApi>

    @GET("${DETAILS_ENDPOINT}/{id}")
    suspend fun getPhoneDetailsById(@Path(value = "id") id: Int): Response<PhoneDetailsApi>

}