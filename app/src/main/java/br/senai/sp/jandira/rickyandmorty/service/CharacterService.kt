package br.senai.sp.jandira.rickyandmorty.service

import retrofit2.Call
import retrofit2.http.GET
import br.senai.sp.jandira.rickyandmorty.model.CharacterList
import retrofit2.http.Path

interface CharacterService {

    //https://rickandmortyapi.com/api/character
    @GET("character")
    fun getCharacters(): Call<CharacterList>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Long)
}