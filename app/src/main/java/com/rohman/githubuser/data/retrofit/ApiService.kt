package com.rohman.githubuser.data.retrofit

import com.rohman.githubuser.data.response.DetailResponseUser
import com.rohman.githubuser.data.response.ItemsItem
import com.rohman.githubuser.data.response.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers():Call<ArrayList<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponseUser>

    @GET("search/users")
    fun findUsers(
        @Query("q") q: String
    ): Call<ResponseUser>

    @GET("users/{username}/following")
    fun getUserFollowings(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}