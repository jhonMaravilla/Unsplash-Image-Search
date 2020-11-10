package com.ktx.imagesearch.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ktx.imagesearch.api.UnsplashApi
import com.ktx.imagesearch.api.UnsplashResponse
import com.ktx.imagesearch.model.UnsplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository
@Inject
constructor(
    private val api: UnsplashApi
) {

    /* pageSize == parems.loadSize in our PagingSource parameter for our API request
    *  maxSize is the max photos our recycleriew can hold to avoid it from loading too much data which will hold unnecessary memory
    */
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(api, query) }
        ).liveData
}