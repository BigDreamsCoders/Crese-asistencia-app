package com.bigdreamcoders.creseasistencia.views.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import kotlinx.android.synthetic.main.dashboard.*
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
        fun changeFragment(fragment:Fragment)
    }

    private fun bindViews(view: View) {
        view.image_slider_dashboard.apply {
            setIndicatorAnimation(IndicatorAnimations.SLIDE)
            scrollTimeInSec = 1
        }
        innerDashBoardFun?.setSlideViews()?.forEach {
            view.image_slider_dashboard.addSliderView(it)
        }
        view.iv_pdf_dashboard.setOnClickListener{
            categoryFragment(Constants.MATERIAL_TYPE_MANUAL)
        }
        view.iv_video_dashboard.setOnClickListener{
            categoryFragment(Constants.MATERIAL_TYPE_VIDEO)
        }
        view.iv_questions_dashboard.setOnClickListener{
            categoryFragment(Constants.MATERIAL_TYPE_QUESTION)
        }
    }

    private fun categoryFragment(type:String){
        innerDashBoardFun?.changeFragment(CategoryFragment.newInstance(type))
    }

    override fun onDetach() {
        super.onDetach()
        innerDashBoardFun = null
    }
}