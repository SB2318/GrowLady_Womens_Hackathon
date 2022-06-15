package com.example.sb2318.growlady

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity(){


    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private val bottomNavView:BottomNavigationView
     get()= findViewById(R.id.bottom_nav_view)

    private val navigationDrawer:NavigationView
     get()= findViewById(R.id.nav_drawer)

    private val toolbar: Toolbar
        get()= findViewById(R.id.toolbar)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navHostFragment= supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // create nav controller
        navController= navHostFragment.navController

        appBarConfig= AppBarConfiguration(setOf(
            R.id.videoFragment,
            R.id.pdfFragment,
            R.id.quizFragment,
        R.id.questionsFragment,
        R.id.motivationFragment,
        R.id.previousPaperFragment,
        R.id.updateFragment))

        setupActionBarWithNavController(navController,appBarConfig)

        //setupActionBarWithNavController(navController)
        bottomNavView.setupWithNavController(navController)
        navigationDrawer.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_drawer_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.log_out->{
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this,"Logged out successfully",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                finish()
                return true
            }
        }
        return item.onNavDestinationSelected(navController) ||super.onOptionsItemSelected(item)
    }
}