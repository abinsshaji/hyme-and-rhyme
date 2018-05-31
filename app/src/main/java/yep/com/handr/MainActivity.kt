package yep.com.handr

import android.app.FragmentManager
import android.content.Intent
import android.database.SQLException
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.* import yep.com.handr.fragments.HymeFragment
import yep.com.handr.fragments.RhymeFragment
import yep.com.handr.utils.Rxbus
import android.database.sqlite.SQLiteDatabase
import yep.com.handr.utils.DatabaseHelper
import java.io.IOException


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener ,HymeFragment.OnFragmentInteractionListener,RhymeFragment.OnFragmentInteractionListener{

    //inform fab search to
    var connectfrag: ConnectFragment?=null
    var rxbus=Rxbus

    private var mDBHelper: DatabaseHelper? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mDBHelper = DatabaseHelper(this)

        try {
            mDBHelper!!.updateDataBase()
        } catch (mIOException: IOException) {
            throw Error("UnableToUpdateDatabase")
        }


        try {
            mDb = mDBHelper!!.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }




        fab.setOnClickListener { view ->
              rxbus.send("search")
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        setupViewpager()
    }
     fun informSerachClick(update:ConnectFragment)
     {
         connectfrag=update

     }

    private fun setupViewpager()
    {
        tab_layout.setupWithViewPager(view_pager)
        val pageadapter:ViewPagerAdapter=ViewPagerAdapter(supportFragmentManager)
        pageadapter.addFragment(RhymeFragment(),"Rhyme")
        pageadapter.addFragment(HymeFragment(),"Hyme")
        view_pager.adapter=pageadapter


    }

    class ViewPagerAdapter(manager: android.support.v4.app.FragmentManager) :FragmentPagerAdapter(manager)
    {

        val fragmentList:MutableList<Fragment>?= mutableListOf()
        val fragmentTitle:MutableList<String>?= mutableListOf()
        override fun getItem(position: Int): Fragment? {
           return fragmentList?.get(position)

        }

        override fun getCount(): Int {
            return fragmentList!!.size

        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitle?.get(position)
        }

        fun addFragment(fragment:Fragment,title:String)
        {
            fragmentList?.add(fragment)
            fragmentTitle?.add(title)


        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
                startActivity(Intent(this,AddActivity::class.java))

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteractionHym(uri: Uri) {

    }

    override fun onFragmentInteraction(uri: Uri) {

    }
    interface ConnectFragment{
        public fun serachClick()

    }
}
