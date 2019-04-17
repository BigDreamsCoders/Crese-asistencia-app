package com.bigdreamcoders.creseasistencia.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bigdreamcoders.creseasistencia.R
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import kotlinx.android.synthetic.main.dashboard.view.*

class DashBoardFragment : Fragment() {

    private var innerDashBoardFun: InnerDashBoardFun? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InnerDashBoardFun) {
            innerDashBoardFun = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dashboard, container, false)
        bindViews(view)
        return view
    }

    interface InnerDashBoardFun {
        fun setSlideViews(): ArrayList<DefaultSliderView>
    }

    private fun bindViews(view: View) {
        view.image_slider_dashboard.apply {
            setIndicatorAnimation(IndicatorAnimations.SLIDE)
            scrollTimeInSec = 1
        }
        innerDashBoardFun?.setSlideViews()?.forEach {
            Log.d("BIND", it.toString())
            view.image_slider_dashboard.addSliderView(it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        innerDashBoardFun = null
    }
}