package com.example.retrofitavanzadaconfotos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoApi {
    @GET("photos")
    fun getAll(): Call<MutableList<Photos>>

    @GET("photos/{albumId}")
    fun getByID(@Path("albumId")id:String):Call<Photos>
}