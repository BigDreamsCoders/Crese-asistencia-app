package com.bigdreamcoders.creseasistencia.views.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.services.networkService.models.videos.Videos
import kotlinx.android.synthetic.main.videos_cardview.view.*

class VideosAdapter(private var list: ArrayList<Videos> = ArrayList(), private val clickListener:(Videos)->Unit) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.videos_cardview, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(newList:ArrayList<Videos>){
        Log.d("LIST", list.size.toString())
        list=newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, val clickListener: (Videos) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Videos) = with(itemView) {
            tv_video_name_cardview.text = item.name
            tv_videos_type_cardview.text = item.sourceType
            this.setOnClickListener{clickListener(item)}
        }
    }
}