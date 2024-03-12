package develpment.myapplication.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import develpment.myapplication.R
import develpment.myapplication.databinding.FragmentLogInBinding
import develpment.myapplication.model.Authority
import develpment.myapplication.model.LoggedInUserView
import develpment.myapplication.model.LoginRequest
import develpment.myapplication.model.ScheduleDriver
import develpment.myapplication.repository.ScheduleRepository
import develpment.myapplication.repository.UserRepository
import develpment.myapplication.service.ScheduleService
import develpment.myapplication.service.UserService
import develpment.myapplication.utils.DataContainers
import develpment.myapplication.utils.SessionManager
import develpment.myapplication.viewmodel.LoginViewModel
import develpment.myapplication.viewmodel.LoginViewModelFactory
import develpment.myapplication.viewmodel.ScheduleViewModel
import develpment.myapplication.viewmodel.ScheduleViewModelFactory
import develpment.myapplication.viewmodel.UserViewModel
import develpment.myapplication.viewmodel.UserViewModelFactory


class LogInFragment : Fragment() {

    var _binding: FragmentLogInBinding? = null
    val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userType: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        
        sessionManager = SessionManager(requireContext())

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        binding.btnRegistrarse.setOnClickListener{
            findNavController().navigate(R.id.action_logInFragment2_to_registrationFragment3)
        }

        binding.btnCambiarContrasena.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment2_to_passwordChangeFragment)
        }

        loginViewModel.loginFormState.observe(viewLifecycleOwner) {
            val loginState = it ?: return@observe

            // disable login button unless both username / password is valid
            binding.btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            }
        }

        loginViewModel.loginResponse.observe(viewLifecycleOwner) {
            val loginResult = it ?: return@observe

            binding.loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
        }
        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                LoginRequest(
                    username = binding.username.text.toString(),
                    password = binding.password.text.toString()
                )
            )
        }

        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    LoginRequest(
                        username = binding.username.text.toString(),
                        password = binding.password.text.toString()
                    )
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            LoginRequest(
                                username = binding.username.text.toString(),
                                password = binding.password.text.toString()
                            )
                        )
                }
                false
            }

            binding.btnLogin.setOnClickListener {
                binding.loading.visibility = View.VISIBLE
                loginViewModel.login(
                    LoginRequest(
                        username = binding.username.text.toString(),
                        password = binding.password.text.toString()
                    )
                )
            }
        }


        return binding.root
    }

    /**
     * Success login to redirect the app to the next Screen
     */
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val username = model.username
        val auth = model.authorities
        for (authority in auth) {
            println(authority.authority.contains("student"))
        }

        // Initiate successful logged in experience
        // Cambiar dependiendo de usuario
        Toast.makeText(context, "$welcome $username", Toast.LENGTH_LONG ).show()


        when
        {
            auth[0].authority.contains("student") -> {
                Toast.makeText(context, "$welcome $username", Toast.LENGTH_LONG ).show()
                //findNavController().navigate(R.id.action_logInFragment2_to_mainActivity)
                userType = "student"
                loadUsersInformation(username)
            }
            auth[0].authority.contains("driver") -> {
                Toast.makeText(context, "$welcome $username", Toast.LENGTH_LONG ).show()
                //findNavController().navigate(R.id.action_logInFragment2_to_driversActivity)
                userType = "driver"
                loadUsersInformation(username)
            }
            else -> {
                Toast.makeText(
                    context,
                    "No tiene permisos para ingresar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
    /**
     * Unsuccessful login
     */
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun loadUsersInformation(username: String) {
        val userService = UserService.getInstance()
        val userRepository = UserRepository(userService)
        val userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]

        userViewModel.getUserData(username)

        userViewModel.done.observe(viewLifecycleOwner){
            if(it){
                if (userType == "student"){
                    loadStudentsInformation()
            }else if (userType == "driver"){
                    loadDriversInformation()
                }
            }else{
                Toast.makeText(context, "Error al cargar los datos del usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadStudentsInformation(){
        val scheduleService = ScheduleService.getInstance()
        val scheduleRepository = ScheduleRepository(scheduleService)
        val scheduleViewModel = ViewModelProvider(this, ScheduleViewModelFactory(scheduleRepository))[ScheduleViewModel::class.java]
        scheduleViewModel.getScheduleFilterOnlyDay()

        scheduleViewModel.done.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_logInFragment2_to_mainActivity)
                activity?.finish()
            }else{
                Toast.makeText(context, "Error al cargar los datos del estudiante", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun loadDriversInformation(){
        val scheduleService = ScheduleService.getInstance()
        val scheduleRepository = ScheduleRepository(scheduleService)
        val scheduleViewModel = ViewModelProvider(this, ScheduleViewModelFactory(scheduleRepository))[ScheduleViewModel::class.java]
        scheduleViewModel.getScheduleDriver(ScheduleDriver(DataContainers.user!!.id.toString()))

        scheduleViewModel.done.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_logInFragment2_to_driversActivity)
                activity?.finish()
            }else{
                Toast.makeText(context, "Error al cargar los datos del conductor", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}