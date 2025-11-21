package com.curiousapps.apimyapi.network

import com.curiousapps.apimyapi.domain.EmojiListItem
import com.curiousapps.apimyapi.util.URL_EXT
import com.curiousapps.apimyapi.util.URL_ONE
import retrofit2.http.GET
import retrofit2.http.Path

interface EmojiApi {

    @GET(URL_EXT)
    suspend fun fetchAllData(): List<EmojiListItem>

    @GET(URL_ONE)
    suspend fun fetchOneData(
        @Path("slug") slug: String
    ): List<EmojiListItem>
}