package develpment.myapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import develpment.myapplication.DialogFragment
import develpment.myapplication.R
import develpment.myapplication.databinding.FragmentProfileBinding
import develpment.myapplication.model.UserLoginResult
import develpment.myapplication.repository.UserRepository
import develpment.myapplication.service.UserService
import develpment.myapplication.utils.DataContainers
import develpment.myapplication.view.MainActivity
import develpment.myapplication.viewmodel.UserViewModel
import develpment.myapplication.viewmodel.UserViewModelFactory


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel

    private lateinit var user : UserLoginResult
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val userService = UserService.getInstance()
        val userRepository = UserRepository(userService)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]
        binding.logoutButton.setOnClickListener {
            // En tu actividad o fragmento
            val customDialog = DialogFragment(requireContext()) {
                ( activity as MainActivity).finish()
            }

            customDialog.show()



        }
        llenarDatos(userViewModel)

        return binding.root
    }

    fun llenarDatos(userViewModel: UserViewModel){
        println("Mostrando datos de usuario")
        val user = DataContainers.user!!

        binding.nameValue.text = "${user.firstName} ${user.lastName}"
        binding.gmailValue.text = user.email
        binding.educationValue.text = user.carreer
    }
}