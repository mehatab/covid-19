package info.covid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavController.Companion.TAB1
import com.ncapdevi.fragnav.FragNavController.Companion.TAB2
import com.ncapdevi.fragnav.FragNavController.Companion.TAB3
import info.covid.home.HomeFragment
import info.covid.info.InfoFragment
import info.covid.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener {

    private lateinit var fragNavController: FragNavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragNavController = FragNavController(supportFragmentManager, R.id.container)
        fragNavController.rootFragmentListener = this
        fragNavController.initialize(TAB1, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    fragNavController.switchTab(TAB1)
                }

                R.id.map -> {
                    fragNavController.switchTab(TAB2)
                }

                R.id.about -> {
                    fragNavController.switchTab(TAB3)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }


    override val numberOfRootFragments: Int = 3

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            TAB1 -> return HomeFragment.newInstance()
            TAB2 -> return MapFragment.newInstance()
            TAB3 -> return InfoFragment.newInstance()
        }

        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }
}
