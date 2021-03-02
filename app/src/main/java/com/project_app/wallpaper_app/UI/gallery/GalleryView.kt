package com.project_app.wallpaper_app.UI.gallery

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.project_app.wallpaper_app.pictures.UnsplashRepsitory

class GalleryView @ViewModelInject constructor(private val repository: UnsplashRepsitory) :
    ViewModel() {
    private  val currentQuery = MutableLiveData(DEFAULT)
    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String){
        currentQuery.value = query
    }

    companion object{
        private const val DEFAULT = "wallpaper"
    }
}