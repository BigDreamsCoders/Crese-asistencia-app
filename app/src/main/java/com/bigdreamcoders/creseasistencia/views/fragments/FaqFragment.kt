package com.bigdreamcoders.creseasistencia.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.FaqPresenterImp
import com.bigdreamcoders.creseasistencia.views.views.FaqView
import kotlinx.android.synthetic.main.faq.view.*

class FaqFragment : Fragment(), FaqView {

    private lateinit var imp: FaqPresenterImp
    private var innerFunctions: InnerFunctions? = null

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
        bind(view)
        return view
    }

    private fun bind(view: View) {
        view.button_faq?.setOnClickListener {
            imp.sendEmail(
                innerFunctions?.getToken() ?: "",
                view.et_email_faq.text.toString().trim(),
                view.et_full_name_faq.text.toString().trim(),
                view.et_question_faq.text.toString().trim()
            )
        }
    }

    override fun beginFetch() {
        view?.button_faq?.visibility = View.GONE
        view?.pb_faq?.visibility = View.VISIBLE
    }

    override fun logout() {
        innerFunctions?.logout()
    }

    override fun msg(msg: Int) {
        innerFunctions?.error(msg)
    }

    override fun finishFetch() {
        view?.button_faq?.visibility = View.VISIBLE
        view?.pb_faq?.visibility = View.GONE
    }

    interface InnerFunctions {
        fun getToken(): String
        fun logout()
        fun error(msg: Int)
    }

    override fun onDetach() {
        super.onDetach()
        innerFunctions = null
    }
}