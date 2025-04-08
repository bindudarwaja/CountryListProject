package com.myapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.model.Country
import com.myapp.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.myapp.data.utils.Result
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    val responseContainer = MutableLiveData<List<Country>>()
    val errorMessage = MutableLiveData<String>()
    val isShowProgress = MutableLiveData<Boolean>()

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled : ${throwable.localizedMessage}")
    }

    fun getCountriesFromRepository() {
        isShowProgress.value = true
        job = viewModelScope.launch(exceptionHandler) {
            val result = getCountriesUseCase.execute()
            if (result is Result.Success) {
                responseContainer.postValue(result.data)
            } else if (result is Result.Error) {
                onError("Error: ${result.message}")
            }
            isShowProgress.postValue(false)
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isShowProgress.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}