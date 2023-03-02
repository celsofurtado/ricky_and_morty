package br.senai.sp.jandira.rickyandmorty.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class RetrofitFactory {

    private val URL = "https://rickandmortyapi.com/api/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCharactersService(): CharacterService {
        return retrofitFactory.create(CharacterService::class.java)
    }

}