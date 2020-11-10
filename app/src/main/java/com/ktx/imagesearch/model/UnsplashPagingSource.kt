package com.ktx.imagesearch.model

import androidx.paging.PagingSource
import com.ktx.imagesearch.api.UnsplashApi
import com.ktx.imagesearch.util.Helper.Companion.UNSPLASH_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class UnsplashPagingSource(
    private val api: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    /* Triggers the API requests
    *  position variable holds the current page we have. Null value for the first page so we used elvis operator
    *
    *
    */

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = api.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

}