package develpment.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import develpment.myapplication.view.fragment.FormSearchScheduleFragment
import develpment.myapplication.view.fragment.ProfileFragment
import develpment.myapplication.R
import develpment.myapplication.view.fragment.MyReservationsFragment
import develpment.myapplication.view.fragment.aboutFragment
import develpment.myapplication.databinding.ActivityMainBinding
import develpment.myapplication.view.fragment.schedulesFragment



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding //nos permitira tener acceso a los elementos de la vista
    private lateinit var navController: NavController


    //add the adapter to the recycler view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostController = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        navController = navHostController.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.book, R.id.reservation, R.id.profile, R.id.scheduleBus, R.id.about)
        )

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() // super.onSupportNavigateUp()
    }

}