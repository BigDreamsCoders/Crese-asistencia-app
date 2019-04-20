package com.bigdreamcoders.creseasistencia.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.manuals.Manuals
import kotlinx.android.synthetic.main.manual_cardview.view.*

class ManualsAdapter(private var list: ArrayList<Manuals> = ArrayList(), private val clickListener: (Manuals) -> Unit) :
    RecyclerView.Adapter<ManualsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.manual_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    fun updateList(newList: ArrayList<Manuals>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Manuals, clickListener:(Manuals)->Unit) = with(itemView) {
            tv_manual_name_cardview.text = item.name
            tv_manual_type_cardview.text = item.sourceType
            this.setOnClickListener { clickListener(item) }
        }
    }
}