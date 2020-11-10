package com.ktx.imagesearch.ui.gallery

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ktx.imagesearch.repository.UnsplashRepository
import com.ktx.imagesearch.util.Helper
import com.ktx.imagesearch.util.Helper.Companion.DEFAULT_QUERY
import javax.inject.Inject

class GalleryViewModel
@ViewModelInject
constructor(
    private val repository: UnsplashRepository
) : ViewModel() {
    private val TAG = "GalleryViewModel"

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    // switchMap gets triggered when the value of the liveData changes
    val photos = currentQuery.switchMap { query ->
        repository.getSearchResults(query).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }
}