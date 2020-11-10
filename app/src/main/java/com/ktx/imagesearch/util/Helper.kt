package com.ktx.imagesearch.util

import com.ktx.imagesearch.BuildConfig

class Helper {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val ACCES_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
        const val UNSPLASH_STARTING_PAGE_INDEX = 1
        const val DEFAULT_QUERY = "cats"
    }

}