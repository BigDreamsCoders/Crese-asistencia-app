package com.bigdreamcoders.creseasistencia.views.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.VideosPresenterImp
import com.bigdreamcoders.creseasistencia.services.networkService.models.manuals.Manuals
import com.bigdreamcoders.creseasistencia.services.networkService.models.videos.Videos
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.adapter.VideosAdapter
import com.bigdreamcoders.creseasistencia.views.views.VideosView
import kotlinx.android.synthetic.main.manuals.view.et_search_manuals
import kotlinx.android.synthetic.main.videos.view.*
import java.text.SimpleDateFormat

class VideosFragment : Fragment(), VideosView {

    private lateinit var imp: VideosPresenterImp
    private lateinit var type: String
    private lateinit var category: String
    private lateinit var adapter: VideosAdapter
    private var list: ArrayList<Videos> = ArrayList()
    private var innerFunctions: InnerFunctions? = null
    private var filter: Int = -1

    companion object {
        fun newInstance(type: String, category: String): Fragment {
            val instance = VideosFragment()
            instance.type = type
            instance.category = category
            return instance
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        innerFunctions = context as InnerFunctions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imp = VideosPresenterImp(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.videos, container, false)
        bind(view)
        if (savedInstanceState != null) with(savedInstanceState) {
            type = getString(Constants.MATERIAL_TYPE_KEY, "")
            category = getString(Constants.MATERIAL_CATEGORY_KEY, "")
            list =
                getParcelableArrayList(Constants.SI_LIST_VIDEOS) ?: ArrayList()
            filter=getInt(Constants.SI_FILTER, -1)
            adapter.updateList(sortList(list))
            view.tv_video_count_video.text = list.size.toString()
        }else{
            imp.fetchVideos(innerFunctions?.getToken()?:"", ".*", category)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.MATERIAL_TYPE_KEY, type)
        outState.putString(Constants.MATERIAL_CATEGORY_KEY, category)
        outState.putParcelableArrayList(Constants.SI_LIST_VIDEOS, list)
        outState.putInt(Constants.SI_FILTER, filter)
    }

    private fun bind(view: View) {
        view.et_search_manuals.setOnTouchListener { _, event ->
            run {
                val left = 0
                val right = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (view.et_search_manuals.right - view.et_search_manuals.compoundDrawables[right].bounds.width())) {
                        imp.fetchVideos(
                            innerFunctions?.getToken() ?: "",
                            if (view.et_search_manuals.text.trim().toString().isNotEmpty()) {
                                view.et_search_manuals.text.trim().toString()
                            } else {
                                ".*"
                            },
                            category
                        )
                        event.action=MotionEvent.ACTION_CANCEL
                        hideKeyboard()
                    }
                    if (event.rawX <= (view.et_search_manuals.compoundDrawables[left].bounds.width())) {
                        showDialog()
                        event.action=MotionEvent.ACTION_CANCEL
                        hideKeyboard()
                    }
                }
                return@setOnTouchListener false
            }
        }
        adapter = VideosAdapter{video:Videos->showDialogVideo(video)}
        view.rv_videos.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@VideosFragment.adapter
            setHasFixedSize(true)
        }
    }

    interface InnerFunctions {
        fun getToken(): String
        fun logout()
        fun error(msg:Int)
        fun hideKeyboard()
        fun openVideo(url:String)
    }

    override fun updateList(list: ArrayList<Videos>) {
        this@VideosFragment.list=list
        adapter.updateList(sortList(list))
    }

    override fun updateItemCount(count: Int) {
        view?.tv_video_count_video?.text = count.toString()
    }

    override fun logout() {
        innerFunctions?.logout()
    }

    override fun error(msg:Int) {
        innerFunctions?.error(msg)
    }

    override fun beginFetch() {
        view?.rv_videos?.visibility=View.GONE
        view?.pb_videos?.visibility=View.VISIBLE
    }

    override fun finishFetch() {
        view?.rv_videos?.visibility=View.VISIBLE
        view?.pb_videos?.visibility=View.GONE
    }

    override fun hideKeyboard() {
        innerFunctions?.hideKeyboard()
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(activity).apply {
            setItems(Array(3) {
                when (it) {
                    0 -> resources.getString(Constants.MANUAL_SPINNER_ALPHA)
                    1 -> resources.getString(Constants.MANUAL_SPINNER_DATE)
                    else -> resources.getString(Constants.MANUAL_SPINNER_TYPE)
                }
            }) { dialog, witch ->
                filter = witch
                adapter.updateList(sortList(list))
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showDialogVideo(video:Videos){
        val dialog = AlertDialog.Builder(activity).apply {
            setTitle(video.name)
            setItems(Array(2) {
                when (it) {
                    0 -> resources.getString(Constants.VIDEO_ACTION_SHARE)
                    else -> resources.getString(Constants.VIDEO_ACTION_OPEN)
                }
            }) { dialog, witch ->
                when (witch) {
                    0 -> videoIntent(video)
                    else -> {
                        openVideo(video)
                    }
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun sortList(list:ArrayList<Videos>):ArrayList<Videos>{
        return when(filter){
            -1->list
            0->
                return list.sortedBy {
                    it.name
                }.toCollection(ArrayList())
            1->
                return list.sortedBy {
                    run{
                        return@sortedBy SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse(it.dateCreated)
                            .time
                    }
                }.toCollection(ArrayList())
            else->
                return list.sortedBy {
                    it.sourceType
                }.toCollection(ArrayList())
        }
    }

    private fun videoIntent(video:Videos){
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "${activity?.resources?.getString(R.string.share_video_intent_msg)} -> ${video.URL}")
        }
        activity?.startActivity(intent)
    }

    private fun openVideo(video:Videos){
        innerFunctions?.openVideo(video.URL)
    }

    override fun onDetach() {
        super.onDetach()
        innerFunctions = null
    }
}