package develpment.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import develpment.myapplication.databinding.ActivityDriversBinding

class DriversActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDriversBinding //nos permitira tener acceso a los elementos de la vista
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriversBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostController = supportFragmentManager.findFragmentById(R.id.navHostFragment3) as NavHostFragment

        navController = navHostController.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view_driver)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.schedulesDriver, R.id.scheduleBusXDay, R.id.aboutD, R.id.profileD)
        )

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() // super.onSupportNavigateUp()
    }
}