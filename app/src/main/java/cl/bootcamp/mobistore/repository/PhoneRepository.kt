package cl.bootcamp.mobistore.repository

import cl.bootcamp.mobistore.dataSource.RestDataSource
import cl.bootcamp.mobistore.model.Phone
import cl.bootcamp.mobistore.model.PhoneApi
import cl.bootcamp.mobistore.model.PhoneDetails
import cl.bootcamp.mobistore.model.PhoneDetailsApi
import cl.bootcamp.mobistore.room.PhoneDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PhoneRepository {
    suspend fun getPhoneDetailsById(id: Int): PhoneDetails
    suspend fun getAllPhonesApi(): ArrayList<PhoneApi>
    fun getAllPhonesRoom(): Flow<List<Phone>>
}

class PhoneRepositoryImp @Inject constructor(
    private val dataSource: RestDataSource,
    private val phoneDao: PhoneDao
): PhoneRepository
{
    override suspend fun getPhoneDetailsById(id: Int): PhoneDetails {
        val data = dataSource.getPhoneDetailsById(id).body()!!
        val phoneDetails = PhoneDetails(
            id = id,
            name = data.name,
            price = data.price,
            lastPrice = data.lastPrice,
            description = data.description,
            image = data.image,
            credit = data.credit
        )
        return phoneDetails
    }

    override suspend fun getAllPhonesApi(): ArrayList<PhoneApi> {
        val data = dataSource.getPhones()
        data.forEach {
            val phone = Phone(
                id = it.id,
                name = it.name,
                price = it.price,
                image = it.image
            )
            phoneDao.insert(phone)
        }
        return ArrayList(data)
    }

    override fun getAllPhonesRoom(): Flow<List<Phone>> = phoneDao.getAll()
}