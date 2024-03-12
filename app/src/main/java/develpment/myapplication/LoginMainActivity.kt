package develpment.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import develpment.myapplication.databinding.ActivityLoginMainBinding
import develpment.myapplication.utils.SessionManager
import develpment.myapplication.viewmodel.LoginViewModel

class LoginMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginMainBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
