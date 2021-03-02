package com.project_app.wallpaper_app.UI.details

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.project_app.wallpaper_app.R
import com.project_app.wallpaper_app.databinding.ImageDetailsBinding
import kotlinx.android.synthetic.main.error_and_retry.*
import kotlinx.android.synthetic.main.image_details.*


class DetailsFragment : Fragment(R.layout.image_details), View.OnClickListener {
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ImageDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailsFragment)
                .load(photo.urls.regular)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        largeImageProgress.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setWallpaper.isVisible = true
                        largeImageProgress.isVisible = false
                        return false
                    }

                })
                .centerCrop()
                .into(imageView)


        }
        to_be_wallpaper.setOnLongClickListener { showPreview() }
        set_wallpaper.setOnClickListener(this)
        image_preview_holder.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!! .id){
            R.id.set_wallpaper -> setWallpaper()
            R.id.image_preview_holder -> hidePreview()
        }
    }

    private fun hidePreview(){
        image_preview_holder.isVisible = false
        set_wallpaper.isVisible = true
    }
    private fun showPreview(): Boolean {
        image_preview_holder.isVisible = true
        set_wallpaper.isVisible = false
        image_view_preview.setImageDrawable(image_view.drawable)
        return true
    }

    private fun setWallpaper() {
//        change text and disable button
        set_wallpaper.isEnabled = false
        set_wallpaper.text = "Wallpaper is Set"
        val bitmap: Bitmap = image_view.drawable.toBitmap()
        val task:SetWallpaperTask = SetWallpaperTask(requireContext(), bitmap)
        task.execute(true)
    }


    companion object{
        class SetWallpaperTask internal constructor (val context: Context, private val bitmap:Bitmap):
                AsyncTask<Boolean, String, String>(){
            override fun doInBackground(vararg params: Boolean?): String {
                val wallpaperManager:WallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap)
                return "Wallpaper set"
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Glide.with(requireContext()).clear(image_view)
    }

}




