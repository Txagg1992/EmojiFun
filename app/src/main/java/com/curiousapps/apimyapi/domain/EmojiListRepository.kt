package com.curiousapps.apimyapi.domain

import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
interface EmojiListRepository {

    suspend fun fetchAllData(): Result<List<EmojiListItem>>
    suspend fun fetchOneData(slug: String): Result<List<EmojiListItem>>
}