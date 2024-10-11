package cl.bootcamp.mobistore.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.mobistore.model.Phone
import cl.bootcamp.mobistore.repository.PhoneRepository
import cl.bootcamp.mobistore.state.PhoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhoneViewModel @Inject constructor(
    private val repository: PhoneRepository
): ViewModel()
{
    var state by mutableStateOf(PhoneState())
        private set

    val phones: Flow<List<Phone>> by lazy {
        repository.getAllPhonesRoom()
    }

    fun getAllApi()
    {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.getAllPhonesApi()
            }
        }
    }

    fun getPhoneById(id: Int)
    {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repository.getPhoneDetailsById(id)
                state = state.copy(
                    name = result.name,
                    price = result.price,
                    image = result.image,
                    description = result.description,
                    lastPrice = result.lastPrice,
                    credit = result.credit,
                )
            }
        }
    }

    fun clean() {
        state = state.copy(
            name = "",
            price = 0,
            image = "",
            description = "",
            lastPrice = 0,
            credit = false
        )
    }
}