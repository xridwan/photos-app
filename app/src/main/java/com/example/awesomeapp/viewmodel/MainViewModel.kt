package com.example.awesomeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()

//    fun getImage(page: Int): LiveData<ArrayList<Photo>> {
//        val image = MutableLiveData<ArrayList<Photo>>()
//        loading.postValue(true)
//        RetrofitInstance.getRetrofit().getData(page).enqueue(object : Callback<Response> {
//            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
//                loading.postValue(false)
//                if (response.isSuccessful) {
//                    val data = response.body()?.photos
//                    image.postValue(data)
//                } else {
//                    status.postValue(response.code())
//                }
//            }
//
//            override fun onFailure(call: Call<Response>, t: Throwable) {
//                loading.postValue(false)
//                message.postValue(t.localizedMessage)
//            }
//        })
//        return image
//    }

    fun getImage(page: Int): LiveData<ArrayList<Photo>> {
        val image = MutableLiveData<ArrayList<Photo>>()
        loading.value = true
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.getRetrofit().getData(page)
                if (data.isSuccessful) {
                    image.value = data.body()?.photos
                } else {
                    status.value = data.code()
                }
                loading.value = false
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> message.value = t.message.toString()
                    is HttpException -> message.value = t.message.toString()
                    else -> message.value = "Unknown error"
                }
                loading.value = false
            }
        }
        return image
    }


    fun getLoading(): LiveData<Boolean> = loading
    fun getStatus(): LiveData<Int> = status
    fun getMessage(): LiveData<String> = message
}