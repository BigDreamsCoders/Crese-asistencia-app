package com.bigdreamcoders.creseasistencia.views.activities

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.manuals.Manuals
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.fragments.CategoryFragment
import com.bigdreamcoders.creseasistencia.views.fragments.DashBoardFragment
import com.bigdreamcoders.creseasistencia.views.fragments.ManualsFragment
import com.bigdreamcoders.creseasistencia.views.fragments.VideosFragment
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.DefaultSliderView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import spencerstudios.com.bungeelib.Bungee

class MainActivity : AppCompatActivity(), DashBoardFragment.InnerDashBoardFun,
    ManualsFragment.InnerFunctions, CategoryFragment.InnerCategoryFunctions,
    VideosFragment.InnerFunctions {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (savedInstanceState == null) {
            firstEntry()
        } else {
            supportActionBar?.title = savedInstanceState.getString(Constants.SI_TITLE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putString(Constants.SI_TITLE, "QUE PEX")
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_dehaze)
            setDisplayHomeAsUpEnabled(true)
        }
        nv_main_activity.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.session_drawer -> {
                    clearSession()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun firstEntry() {
        supportActionBar?.title = resources.getString(R.string.dashboard_fragment_name)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_content_main_content, DashBoardFragment())
            .commit()
        nv_main_activity.apply {
            setCheckedItem(R.id.dash_board_drawer)
            menu.performIdentifierAction(R.id.dash_board_drawer, 0)
        }
    }

    private fun clearSession() {
        getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        Bungee.card(this)
        finish()
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
            array.add(slider)
        }
        return array
    }

    override fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_content_main_content, fragment)
            .addToBackStack(null)
            .commit()
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

    override fun getToken(): String {
        return getSharedPreferences(
            Constants.SP_NAME,
            Context.MODE_PRIVATE
        ).getString(Constants.SP_TOKEN, "") ?: ""
    }

    override fun downloadPDF(manual: Manuals) {
        val fileName = "${manual.name}.pdf"
        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val r = DownloadManager.Request(Uri.parse(manual.URL)).apply {
            title = manual.name
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            allowScanningByMediaScanner()
            setMimeType("application/pdf")
            setNotificationVisibility(1)
        }
        dm.enqueue(r)
        Snackbar.make(
            dl_main_activity,
            resources.getString(R.string.download_info),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun logout() {
        Toast.makeText(this, resources.getString(R.string.logout_msg), Toast.LENGTH_LONG).show()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }

    override fun error(msg: Int) {
        Snackbar.make(dl_main_activity, resources.getText(msg), Snackbar.LENGTH_LONG).show()
    }

}
