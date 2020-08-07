package com.axiel7.moelist.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.axiel7.moelist.R
import com.axiel7.moelist.ui.animelist.AnimeListFragment
import com.axiel7.moelist.ui.home.HomeFragment
import com.axiel7.moelist.ui.mangalist.MangaListFragment
import com.axiel7.moelist.utils.SharedPrefsHelpers
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private var homeFragment = HomeFragment()
    private var animeListFragment = AnimeListFragment()
    private var mangaListFragment = MangaListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //toolbar
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_round_menu_24)

        //edge to edge
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            window.decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }

        //bottom sheet
        val dialogView = layoutInflater.inflate(R.layout.main_bottom_sheet, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(dialogView)
        toolbar.setNavigationOnClickListener { bottomSheetDialog.show() }

        //shared preferences
        SharedPrefsHelpers.init(this)
        val sharedPref = SharedPrefsHelpers.instance
        val isUserLogged = sharedPref?.getBoolean("isUserLogged", false)
        val accessToken = sharedPref?.getString("accessToken", "null")
        val isTokenNull = accessToken.equals("null")

        //launch login
        if (!isUserLogged!! || isTokenNull) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        //bottom nav and fragments
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        setupTransitions()
        setupBottomBar(navView)
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, homeFragment)
            .commit()
    }

    private fun setupBottomBar(navigationView: BottomNavigationView) {
        navigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = homeFragment
            when (item.itemId) {
                R.id.navigation_home -> selectedFragment = homeFragment
                R.id.navigation_anime_list -> selectedFragment = animeListFragment
                R.id.navigation_manga_list -> selectedFragment = mangaListFragment
            }
            val fade = Fade()
            selectedFragment.enterTransition = fade
            selectedFragment.exitTransition = fade
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, selectedFragment)
                .commitAllowingStateLoss()

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setupTransitions() {
        val fade = Fade()

        homeFragment.enterTransition = fade
        homeFragment.exitTransition = fade

        animeListFragment.enterTransition = fade
        animeListFragment.exitTransition = fade

        mangaListFragment.enterTransition = fade
        mangaListFragment.exitTransition = fade
    }
}