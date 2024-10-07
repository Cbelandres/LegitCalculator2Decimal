package com.example.bottomnav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bottom Navigation setup
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> loadFragment(ProfileFragment())
                R.id.nav_calculator -> loadFragment(CalculatorFragment())  // Placeholder for Calculator
                R.id.nav_todolist -> loadFragment(ToDoListFragment())  // Placeholder for ToDoList
            }
            true
        }

        // Set the Profile as the default tab
        bottomNav.selectedItemId = R.id.nav_profile
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
