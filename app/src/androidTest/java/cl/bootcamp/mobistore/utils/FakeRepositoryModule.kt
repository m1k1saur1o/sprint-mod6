package cl.bootcamp.mobistore.utils

import cl.bootcamp.mobistore.di.RepositoryModule
import cl.bootcamp.mobistore.model.Phone
import cl.bootcamp.mobistore.model.PhoneApi
import cl.bootcamp.mobistore.model.PhoneDetails
import cl.bootcamp.mobistore.repository.PhoneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton


private val fakePhoneDetails =  PhoneDetails(
    id = 1,
    name = "name",
    price = 1,
    lastPrice = 2,
    description = "description",
    image = "https://...",
    credit = true
)

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

private val phoneApi1 = PhoneApi(
    id = 0,
    name = "name1",
    image = "https://....",
    price = 1
)

private val phoneApi2 = PhoneApi(
    id = 1,
    name = "name2",
    image = "https://....",
    price = 1
)

val phoneList = listOf(phone1, phone2 , phone3)

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {

    @Provides
    @Singleton
    fun phoneRepository(): PhoneRepository = object : PhoneRepository {


        override suspend fun getPhoneDetailsById(id: Int): PhoneDetails = fakePhoneDetails


        override suspend fun getAllPhonesApi(): ArrayList<PhoneApi> {
            val phones = ArrayList<PhoneApi>()

            phones.add(phoneApi1)
            phones.add(phoneApi2)

            return phones
        }

        override fun getAllPhonesRoom(): Flow<List<Phone>> = flowOf(phoneList)

    }
}