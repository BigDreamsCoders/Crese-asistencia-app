package com.bigdreamcoders.creseasistencia.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.FaqPresenterImp
import com.bigdreamcoders.creseasistencia.services.networkService.models.faq.Faq
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.FaqView
import kotlinx.android.synthetic.main.faq.view.*

class FaqFragment : Fragment(), FaqView {

    private lateinit var imp: FaqPresenterImp
    private var innerFunctions: InnerFunctions? = null
    private var list: ArrayList<Faq> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InnerFunctions) {
            innerFunctions = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imp = FaqPresenterImp(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.faq, container, false)
        if (savedInstanceState != null) with(savedInstanceState){
            this@FaqFragment.deleteAllChild()
            list=getParcelableArrayList(Constants.SI_LIST_FAQ)?: ArrayList()
            innerFunctions?.createView(list)?.forEach {
                view?.ll_faq?.addView(it)
            }
        }
        else{
            bind()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.SI_LIST_FAQ, list)
    }

    override fun inflateView(list: ArrayList<Faq>) {
        this@FaqFragment.list = list
        innerFunctions?.createView(list)?.forEach {
            view?.ll_faq?.addView(it)
        }
    }

    override fun deleteAllChild() {
        view?.ll_faq?.removeAllViews()
    }

    private fun bind() {
        imp.fetchFAQ(innerFunctions?.getToken() ?: "")
    }

    override fun beginFetch() {
        view?.ll_faq?.visibility = View.GONE
        view?.pb_faq?.visibility = View.VISIBLE
    }

    override fun logout() {
        innerFunctions?.logout()
    }

    override fun error(msg: Int) {
        innerFunctions?.error(msg)
    }

    override fun finishFetch() {
        view?.ll_faq?.visibility = View.VISIBLE
        view?.pb_faq?.visibility = View.GONE
    }

    interface InnerFunctions {
        fun getToken(): String
        fun logout()
        fun error(msg: Int)
        fun createView(list: ArrayList<Faq>): MutableList<LinearLayout>
    }

    override fun onDetach() {
        super.onDetach()
        innerFunctions = null
    }
}