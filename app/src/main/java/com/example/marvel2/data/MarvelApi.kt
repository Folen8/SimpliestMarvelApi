package com.example.marvel2.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

interface MarvelApi {

    @GET("/v1/public/characters")
    fun getCharacters(
        @Query("apikey")apikey:String = PUBLIC_KEY,
        @Query("ts")ts:String = timeStamp,
        @Query("hash")hash:String = hash(),
        @Query("offset")offset:Int
    ) : Call<CharacterDTO>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacterById(
        @Path("characterId") characterId: Int,
        @Query("apikey") apikey:String = PUBLIC_KEY,
        @Query("ts") ts:String = timeStamp,
        @Query("hash") hash:String = hash(),
    ) : Call<CharacterDTO>

    companion object {

        var BASE_URL = "https://gateway.marvel.com"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val PUBLIC_KEY = "cd1a6086e7a6d97eec85189056686768"
        const val PRIVATE_KEY = "2bdc56d36e7d02d77c801d404612cdcb6ead6842"

        fun hash():String{
            val input = "$timeStamp$PRIVATE_KEY$PUBLIC_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1,md.digest(input.toByteArray())).toString(16).padStart(32,'0')
        }

        fun create() : MarvelApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(MarvelApi::class.java)

        }
    }
}