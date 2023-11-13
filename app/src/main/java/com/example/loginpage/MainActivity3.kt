// MainActivity3.kt
package com.example.loginpage

import FilterFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity3 : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Set the initial fragment
        replaceFragment(MapFragment())

        // Set up a listener for bottom navigation item selection
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_map -> replaceFragment(MapFragment())
                R.id.menu_filter -> replaceFragment(FilterFragment())
                R.id.menu_bird -> replaceFragment(BirdFragment())
                R.id.menu_journal -> replaceFragment(JournalFragment())
            }
            true
        }

        // Select the MapFragment by default
        bottomNavigation.selectedItemId = R.id.menu_map
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mapContainer, fragment)
            .commit()
    }
}
