package com.curiousapps.apimyapi.data

import com.curiousapps.apimyapi.domain.EmojiListItem
import com.curiousapps.apimyapi.domain.EmojiListRepository
import com.curiousapps.apimyapi.network.EmojiApi
import java.io.IOException
import javax.inject.Inject

class EmojiListRepositoryImpl @Inject constructor(
    private val api : EmojiApi
) : EmojiListRepository{
    override suspend fun fetchAllData(): Result<List<EmojiListItem>> {
        try {
            api.fetchAllData()
                .let {
                    return Result.success(it)
                }
        }catch (ex: IOException){
            return Result.failure(ex)
        }
    }

    override suspend fun fetchOneData(slug: String): Result<List<EmojiListItem>> {
        try {
            api.fetchOneData(slug = slug)
                .let {
                    return Result.success(it)
                }
        }catch (ex: IOException){
            return Result.failure(ex)
        }
    }
}