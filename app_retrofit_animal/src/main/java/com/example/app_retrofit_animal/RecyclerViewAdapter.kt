package com.example.app_retrofit_animal

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_retrofit_animal.model.AnimalInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_row.view.*
import java.lang.Exception

class RecyclerViewAdapter(): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var items: List<AnimalInfo> = ArrayList<AnimalInfo>()
    fun setListData(items: List<AnimalInfo>) {
        this.items = items
    }
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val place = view.tv_place
        val kind = view.tv_kind
        val album: ImageView = view.iv_album_file

        fun bind(photo: AnimalInfo, context: Context) {
            place.text = photo.animal_place.toString()
            kind.text = photo.animal_kind
            try {
                Picasso.get().load(photo.album_file).into(album)
            } catch (e: Exception) {
                // 沒有圖片

            }
            album.setOnClickListener {
                val iv: ImageView = ImageView(context)
                Picasso.get().load(photo.album_file).into(iv)
                AlertDialog.Builder(context)
                    .setTitle("Photo")
                    .setView(iv)
                    .setPositiveButton("Close", null)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position], holder.itemView.context)
    }
}