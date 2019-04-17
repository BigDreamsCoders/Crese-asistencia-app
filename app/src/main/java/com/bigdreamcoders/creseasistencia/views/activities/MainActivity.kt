package com.bigdreamcoders.creseasistencia.views.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.views.fragments.DashBoardFragment
import com.smarteist.autoimageslider.DefaultSliderView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*

class MainActivity : AppCompatActivity(), DashBoardFragment.InnerDashBoardFun {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_dehaze)
            setDisplayHomeAsUpEnabled(true)
            title = resources.getString(R.string.dashboard_fragment_name)
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_content_main_content, DashBoardFragment())
            .commit()
        nv_main_activity.apply {
            setCheckedItem(R.id.dash_board_drawer)
            menu.performIdentifierAction(R.id.dash_board_drawer, 0)
        }

    }

    override fun setSlideViews(): ArrayList<DefaultSliderView> {
        val array = ArrayList<DefaultSliderView>()
        for (i in 0..2) {
            val slider = DefaultSliderView(this@MainActivity)
            when (i) {
                0 -> slider.imageUrl =
                    "https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                1 -> slider.imageUrl =
                    "https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                2 -> slider.imageUrl =
                    "https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            }
            slider.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            slider.description = resources.getString(R.string.email_valid)
            array.add(slider)
        }
        return array
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                dl_main_activity.openDrawer(GravityCompat.START)
                return true
            }
        }
        return true
    }
}
