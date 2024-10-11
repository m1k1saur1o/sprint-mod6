package cl.bootcamp.mobistore

import android.arch.core.executor.testing.InstantTaskExecutorRule
import cl.bootcamp.mobistore.dataSource.RestDataSource
import cl.bootcamp.mobistore.model.Phone
import cl.bootcamp.mobistore.repository.PhoneRepositoryImp
import cl.bootcamp.mobistore.room.PhoneDao
import cl.bootcamp.mobistore.util.Constants.Companion.DETAILS_ENDPOINT
import cl.bootcamp.mobistore.util.Constants.Companion.PRODUCTS_ENDPOINT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.nio.charset.StandardCharsets

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

private val phone1 = Phone(
    id = 0,
    name = "name1",
    price = 1,
    image = "http://..."
)

private val phone2 = Phone(
    id = 1,
    name = "name2",
    price = 2,
    image = "http://..."
)

private val phone3 = Phone(
    id = 2,
    name = "name3",
    price = 3,
    image = "http://..."
)

class PhoneRepositoryTest {
    private val mockWebServer: MockWebServer = MockWebServer().apply {
        url("/")
        dispatcher = myDispatcher
    }

    private val restDataSource = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestDataSource::class.java)

    private val phoneRepository = PhoneRepositoryImp(restDataSource, MockPhoneDao())

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Verify if get all phones from room DB response is correct`() = runBlocking {
        val phones = phoneRepository.getAllPhonesRoom().first()
        assertEquals(3, phones.size)
    }

    @Test
    fun `Verify if get all phones from API response is correct`() = runBlocking {
        val phones = phoneRepository.getAllPhonesApi()
        assertEquals(3, phones.size)
    }

    @Test
    fun `Verify if get phone details by id from API response is correct`() = runBlocking {
        val phone = phoneRepository.getPhoneDetailsById(1)
        assertEquals(phone.id, 1)
        assertEquals(phone.name, "Samsung Galaxy A21s 64GB")
        assertEquals(phone.price, 167253)
        assertEquals(phone.image, "https://images.samsung.com/is/image/samsung/es-galaxy-a21s-sm-a217fzkoeub-262755098?\$PD_GALLERY_L_JPG$")
        assertEquals(phone.description, "Tamaño 6,5''\n Densidad 294 ppi\nResolución de pantalla 720 x 1600")
        assertEquals(phone.lastPrice, 177253)
        assertEquals(phone.credit, true)
    }

}

class MockPhoneDao: PhoneDao {

    private val phones: MutableStateFlow<List<Phone>> = MutableStateFlow<List<Phone>>(listOf(phone1, phone2, phone3))

    override fun insert(phone: Phone) {
        phones.value = phones.value.toMutableList().apply { add(phone) }
    }

    override fun getAll(): Flow<List<Phone>> = phones
}

val myDispatcher: Dispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/$PRODUCTS_ENDPOINT" -> MockResponse().apply { addResponse("api_phone.json") }
            "/$DETAILS_ENDPOINT/1" -> MockResponse().apply { addResponse("api_phone_details.json") }
            else -> MockResponse().setResponseCode(404)
        }
    }

}

fun MockResponse.addResponse(filePath: String): MockResponse {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        setResponseCode(200)
        setBody(it.readString(StandardCharsets.UTF_8))
    }
    return this
}