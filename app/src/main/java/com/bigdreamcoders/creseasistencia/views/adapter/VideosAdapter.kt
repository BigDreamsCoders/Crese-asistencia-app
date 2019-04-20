package com.bigdreamcoders.creseasistencia.views.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.manuals.Manuals
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.videos.Videos
import kotlinx.android.synthetic.main.manual_cardview.view.*
import kotlinx.android.synthetic.main.videos_cardview.view.*

class VideosAdapter(private var list: ArrayList<Videos> = ArrayList()) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.videos_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Item", list[position].name)
        holder.bind(list[position])
    }

    fun updateList(newList:ArrayList<Videos>){
        list=newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Videos) = with(itemView) {
            tv_video_name_cardview.text = item.name
            tv_videos_type_cardview.text = item.sourceType
        }
    }
}