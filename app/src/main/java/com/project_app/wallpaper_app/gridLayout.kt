package com.project_app.wallpaper_app

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class gridLayout: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.image_container)

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        val gridlayoutManager = GridLayoutManager(applicationContext, 3)
        recyclerview.layoutManager = gridlayoutManager
    }
}