package com.example.ps2017136first.services

import com.example.ps2017136first.model.user
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersService {
    @GET("/users/{id}")
    fun getUser(@Path("id") id: String): Call<user>
}