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
import kotlinx.android.synthetic.main.category_menu.view.*

class CategoryFragment : Fragment() {

    private lateinit var type: String

    companion object {
        fun newInstance(type: String): CategoryFragment {
            val newFragment = CategoryFragment()
            newFragment.type=type
            return newFragment
        }
    }

    private var innerCategoryFunctions: InnerCategoryFunctions? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InnerCategoryFunctions) {
            innerCategoryFunctions = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        type=savedInstanceState?.getString(Constants.MATERIAL_TYPE_KEY)?:type
        val view = inflater.inflate(R.layout.category_menu, container, false)
        bind(view)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.MATERIAL_TYPE_KEY, type)
    }

    private fun bind(view: View) {
        view.cl_category_cctva.setOnClickListener {
            innerCategoryFunctions?.changeFragment(
                selectFragment(Constants.CATEGORY_CCTV)
            )
        }
        view.cl_category_cameras.setOnClickListener {
            innerCategoryFunctions?.changeFragment(
                selectFragment(Constants.CATEGORY_CAMARAS)
            )
        }
        view.cl_category_gps.setOnClickListener {
            innerCategoryFunctions?.changeFragment(
                selectFragment(Constants.CATEGORY_GPS)
            )
        }
        view.cl_category_access_control.setOnClickListener {
            innerCategoryFunctions?.changeFragment(
                selectFragment(Constants.CATEGORY_AC)
            )
        }
    }

    private fun selectFragment(category:String): Fragment {
        Log.d("TYPE", type)
        return when (type) {
            Constants.MATERIAL_TYPE_MANUAL -> ManualsFragment.newInstance(type, category)
            Constants.MATERIAL_TYPE_VIDEO -> VideosFragment.newInstance(type, category)
            Constants.MATERIAL_TYPE_QUESTION->ManualsFragment()
            else -> DashBoardFragment()
        }
    }

    interface InnerCategoryFunctions {
        fun changeFragment(fragment: Fragment)
    }

    override fun onDetach() {
        super.onDetach()
        innerCategoryFunctions = null
    }
}