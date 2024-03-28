package com.rohman.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rohman.githubuser.data.local.FavoriteUser
import com.rohman.githubuser.data.repository.FavoriteUserRepo
import com.rohman.githubuser.data.response.DetailResponseUser
import com.rohman.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val userFavoriteRepository: FavoriteUserRepo = FavoriteUserRepo(application)

    private val _user = MutableLiveData<DetailResponseUser?>()
    val user: MutableLiveData<DetailResponseUser?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUser(username: String) {

        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponseUser> {
            override fun onResponse(
                call: Call<DetailResponseUser>,
                response: Response<DetailResponseUser>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponseUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    fun insert(userFavorite: FavoriteUser) {
        userFavoriteRepository.insert(userFavorite)
    }

    fun delete(userFavorite: String) {
        userFavoriteRepository.delete(userFavorite)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        userFavoriteRepository.getFavoriteUserByUsername(username)

}