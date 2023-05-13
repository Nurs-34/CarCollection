package kg.surfit.carcollection.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kg.surfit.carcollection.R
import kg.surfit.carcollection.databinding.ActivityMainBinding
import kg.surfit.carcollection.ui.home.fragments.AddCarFragment
import kg.surfit.carcollection.ui.home.fragments.HomeFragment
import kg.surfit.carcollection.ui.home.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadFragment(HomeFragment())

        bottomNav = binding.bottomNavigation
        bottomNav.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.home_fragment -> selectedFragment = HomeFragment()
                R.id.add_fragment -> selectedFragment = AddCarFragment()
                R.id.settings_fragment -> selectedFragment = SettingsFragment()
            }

            selectedFragment?.let { loadFragment(it) }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}