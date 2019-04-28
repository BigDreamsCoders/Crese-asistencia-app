package com.bigdreamcoders.creseasistencia.views.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.ManualsPresenterImp
import com.bigdreamcoders.creseasistencia.services.networkService.models.manuals.Manuals
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.adapter.ManualsAdapter
import com.bigdreamcoders.creseasistencia.views.views.ManualsView
import kotlinx.android.synthetic.main.manuals.view.*
import java.text.SimpleDateFormat

class ManualsFragment : Fragment(), ManualsView {

    private lateinit var imp: ManualsPresenterImp
    private lateinit var type: String
    private lateinit var category: String
    private var innerFunctions: InnerFunctions? = null
    private lateinit var adapter: ManualsAdapter
    private var list: ArrayList<Manuals> = ArrayList()
    private var filter: Int = -1

    companion object {
        fun newInstance(type: String, category: String): Fragment {
            val instance = ManualsFragment()
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
        imp = ManualsPresenterImp(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.manuals, container, false)
        bind(view)
        if (savedInstanceState != null) with(savedInstanceState) {
            type = getString(Constants.MATERIAL_TYPE_KEY, "")
            category = getString(Constants.MATERIAL_CATEGORY_KEY, "")
            list =
                getParcelableArrayList(Constants.SI_LIST_MANUALS) ?: ArrayList()
            filter=getInt(Constants.SI_FILTER, -1)
            adapter.updateList(sortList(list))
            view.tv_manual_count_manuals.text = list.size.toString()
        }else{
            imp.fetchManuals(innerFunctions?.getToken()?:"", ".*", category)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.MATERIAL_TYPE_KEY, type)
        outState.putString(Constants.MATERIAL_CATEGORY_KEY, category)
        outState.putParcelableArrayList(Constants.SI_LIST_MANUALS, list)
        outState.putInt(Constants.SI_FILTER, filter)
    }

    private fun bind(view: View) {
        view.et_search_manuals.setOnTouchListener { _, event ->
            run {
                val left = 0
                val right = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (view.et_search_manuals.right - view.et_search_manuals.compoundDrawables[right].bounds.width())) {
                        imp.fetchManuals(
                            innerFunctions?.getToken() ?: "",
                            if (view.et_search_manuals.text.trim().toString().isNotEmpty()) {
                                view.et_search_manuals.text.trim().toString()
                            } else {
                                ".*"
                            },
                            category
                        )
                        event.action = MotionEvent.ACTION_CANCEL
                        hideKeyboard()
                    }
                    if (event.rawX <= (view.et_search_manuals.compoundDrawables[left].bounds.width())) {
                        showDialog()
                        event.action = MotionEvent.ACTION_CANCEL
                        hideKeyboard()
                    }
                }
                return@setOnTouchListener false
            }
        }
        adapter = ManualsAdapter(list) { manual: Manuals -> openPDF(manual) }
        view.rv_manuals.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@ManualsFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun openPDF(manual: Manuals) {
        showPDFDialog(manual)
    }

    interface InnerFunctions {
        fun getToken(): String
        fun logout()
        fun error(msg: Int)
        fun hideKeyboard()
        fun downloadPDF(manual: Manuals)
    }

    override fun updateList(list: ArrayList<Manuals>) {
        this@ManualsFragment.list = list
        adapter.updateList(sortList(list))
    }

    override fun updateItemCount(count: Int) {
        view?.tv_manual_count_manuals?.text = count.toString()
    }

    override fun beginFetch() {
        view?.pb_manuals?.visibility = View.VISIBLE
        view?.rv_manuals?.visibility = View.GONE
    }

    override fun error(msg: Int) {
        innerFunctions?.error(msg)
    }

    override fun logout() {
        innerFunctions?.logout()
    }

    override fun finishFetch() {
        view?.pb_manuals?.visibility = View.GONE
        view?.rv_manuals?.visibility = View.VISIBLE
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

    private fun showPDFDialog(manual: Manuals) {
        val dialog = AlertDialog.Builder(activity).apply {
            setTitle(manual.name)
            setItems(Array(2) {
                when (it) {
                    0 -> resources.getString(Constants.PDF_ACTION_SHARE)
                    else -> resources.getString(Constants.PDF_ACTION_OPEN)
                }
            }) { dialog, witch ->
                when (witch) {
                    0 -> pdfIntent(manual)
                    else -> {
                        innerFunctions?.downloadPDF(manual)
                    }
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun sortList(list:ArrayList<Manuals>):ArrayList<Manuals>{
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

    private fun pdfIntent(manual: Manuals) {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "${activity?.resources?.getString(R.string.share_pdf_intent_msg)} -> ${manual.URL}"
            )
        }
        activity?.startActivity(intent)
    }

    override fun hideKeyboard() {
        innerFunctions?.hideKeyboard()
    }

    override fun onDetach() {
        super.onDetach()
        innerFunctions = null
    }
}
