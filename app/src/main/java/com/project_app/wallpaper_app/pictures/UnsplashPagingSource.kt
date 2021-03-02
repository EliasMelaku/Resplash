package com.project_app.wallpaper_app.pictures

import androidx.paging.PagingSource
import com.project_app.wallpaper_app.api.UnsplashAPI
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource (
    private val unsplashApi: UnsplashAPI,
    private val query : String,
) : PagingSource<Int, UnsplashImage>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhoto(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX)  null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        }catch(exception: IOException){
            LoadResult.Error(exception)
        }catch(exception: HttpException){
            LoadResult.Error(exception)
        }

    }
}
