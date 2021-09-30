package com.example.awesomeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.network.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _status = MutableLiveData<Int>()
    private val _message = MutableLiveData<String>()

    val loading: LiveData<Boolean> = _loading
    val status: LiveData<Int> = _status
    val message: LiveData<String> = _message

    fun getImage(page: Int): LiveData<ArrayList<Photo>> {
        val image = MutableLiveData<ArrayList<Photo>>()
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val data = ApiConfig.getRetrofit().getData(page)
                if (data.isSuccessful) {
                    image.value = data.body()?.photos
                } else {
                    _status.value = data.code()
                }
                _loading.value = false
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _message.value = "Network Error"
                    is HttpException -> _message.value = t.message().toString()
                    else -> _message.value = "Unknown error"
                }
                _loading.value = false
            }
        }
        return image
    }
}