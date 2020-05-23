package com.example.retrofitavanzadaconfotos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photos.view.*

class PhotosAdapter: RecyclerView.Adapter<ViewHolder>() {

    var lista: MutableList<Photos> = mutableListOf()
    lateinit var context: Context

    fun PhotosAdapter(lista: MutableList<Photos>, context: Context) {
        this.lista = lista
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_photos, parent, false))
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = lista.get(position)
        holder.bind(item)
    }
}

class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
    var albumId = view.findViewById<TextView>(R.id.textViewAlbumId)
    var id = view.findViewById<TextView>(R.id.textViewId)
    var title = view.findViewById<TextView>(R.id.textViewTitle)
    var url = view.findViewById<TextView>(R.id.textViewUrl)
    var photo = view.findViewById<ImageView>(R.id.imageView)

    fun bind(photos: Photos) {
        albumId.text = photos.albumId.toString()
        id.text = photos.id.toString()
        title.text = photos.title
        url.text = photos.url
        photo.loadUrl(photos.thumbnailUrl)


    }

    fun ImageView.loadUrl(url: String) {
        Picasso.get().load(url).into(imageView)
    }
}
