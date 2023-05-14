package kg.surfit.carcollection.ui.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import kg.surfit.carcollection.R
import kg.surfit.carcollection.databinding.ActivityMainBinding
import kg.surfit.carcollection.db.AppDatabase

import kg.surfit.carcollection.ui.home.fragments.AddCarFragment
import kg.surfit.carcollection.ui.home.fragments.HomeFragment
import kg.surfit.carcollection.ui.home.fragments.SettingsFragment
import kg.surfit.carcollection.utils.SubCounter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val subCounter = SubCounter(applicationContext)
        var addCounter = subCounter.getAddCount()
        Log.e("Count", addCounter.toString())

        loadFragment(HomeFragment())

//        val dialogFragment = BuySubDialog()
//        dialogFragment.show(supportFragmentManager, "subscription_dialog")

        bottomNav = binding.bottomNavigation
        bottomNav.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.home_fragment -> selectedFragment = HomeFragment()
                R.id.add_fragment -> { if (addCounter != addCar){
                selectedFragment = AddCarFragment()}
                else {
                    selectedFragment = HomeFragment()
                    Toast.makeText(applicationContext, "Please enter valid car details", Toast.LENGTH_SHORT).show()
//                    dialogFragment.show(supportFragmentManager, "subscription_dialog")
                 }}
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

    companion object {
        const val showCar = 3
        const val addCar = 2
    }
}