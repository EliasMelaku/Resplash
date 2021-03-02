package com.project_app.wallpaper_app.UI.gallery

import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.project_app.wallpaper_app.R
import com.project_app.wallpaper_app.databinding.SingleImageBinding
import com.project_app.wallpaper_app.pictures.UnsplashImage

class UnsplashPhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashImage, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {
   inner class PhotoViewHolder(private val binding: SingleImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null ){
                        listener.onItemClick(item)
                    }
                }

            }
        }

        fun bind(photo: UnsplashImage) {
            binding.apply {
                Glide.with(itemView).load(photo.urls.regular).listener(object :RequestListener<Drawable>{
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        smallProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        return false
                    }
                }).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(singleImage)
                imageUsername.text = photo.user.username

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = SingleImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    interface OnItemClickListener{
        fun onItemClick(photo: UnsplashImage)
    }
    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashImage>() {
            override fun areItemsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage) =
                oldItem == newItem
        }
    }
}