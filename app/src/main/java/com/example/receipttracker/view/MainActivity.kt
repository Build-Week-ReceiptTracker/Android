package com.example.receipttracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.receipttracker.App
import com.example.receipttracker.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Launch MyReceipts fragment by default
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, MyReceiptsFragment()).commit()

        //Initialize Navigation Drawer
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)

        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView

        //Set listeners for nav drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true

            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.nav_category_my_receipts -> {
                    selectedFragment = MyReceiptsFragment()
                    title = "My Receipts"
                }
                R.id.nav_category_add_receipt -> {
                    selectedFragment = AddReceiptFragment()
                    title = "Add Receipt"
                }
                R.id.nav_category_charts -> {
                    selectedFragment = ChartFragment()
                    title = "Analysis"
                }
            }
            selectedFragment?.let { it1 ->

                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    it1
                ).commit()
            }

            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sign_out -> {
                App.sharedPrefs.edit().remove(App.NAME_PREF_KEY).commit()
                App.sharedPrefs.edit().remove(App.TOKEN_PREF_KEY).commit()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
