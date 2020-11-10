package com.ktx.imagesearch.api

import com.ktx.imagesearch.model.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)