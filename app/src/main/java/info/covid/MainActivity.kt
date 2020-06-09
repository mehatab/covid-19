package info.covid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragNavHost)

        bottom_nav.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
                topLevelDestinationIds = setOf(
                        R.id.home,
                        R.id.notification,
                        R.id.state_list,
                        R.id.essentials,
                        R.id.about
                )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottom_nav.visibility = when (destination.id) {
                R.id.state_wise_info, R.id.country -> View.GONE
                else -> View.VISIBLE
            }
        }

    }

    override fun onSupportNavigateUp() = NavHostFragment.findNavController(fragNavHost).navigateUp()

    override fun onBackPressed() {
        if (onBackPressedDispatcher.hasEnabledCallbacks())
            onBackPressedDispatcher.onBackPressed()
        else
            super.onBackPressed()
    }
}
