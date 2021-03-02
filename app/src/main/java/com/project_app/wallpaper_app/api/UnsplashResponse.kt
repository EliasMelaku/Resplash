package com.project_app.wallpaper_app.api

import com.project_app.wallpaper_app.pictures.UnsplashImage

data class UnsplashResponse (
    val results: List<UnsplashImage>
)
