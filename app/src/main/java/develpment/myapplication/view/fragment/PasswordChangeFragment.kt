package develpment.myapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import develpment.myapplication.R
import develpment.myapplication.databinding.FragmentPasswordChangeBinding
import develpment.myapplication.model.UserChangePassword
import develpment.myapplication.repository.UserRepository
import develpment.myapplication.service.UserService
import develpment.myapplication.viewmodel.UserViewModel
import develpment.myapplication.viewmodel.UserViewModelFactory


class PasswordChangeFragment : Fragment() {
    private var _binding: FragmentPasswordChangeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentPasswordChangeBinding.inflate(inflater, container, false)


        binding.btnReturnPC.setOnClickListener {
            findNavController().popBackStack()
        }

        val userService = UserService.getInstance()
        val userRepository = UserRepository(userService)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]

        binding.btnChangePassword.setOnClickListener {
            changePasswordProcess(userViewModel)
        }

        return binding.root
    }

    fun changePasswordProcess(userViewModel: UserViewModel) {
        // ensure that all fields are filled and both passwords match
        if (!validaciones()) {
            return
        }

        val userChangePassword = UserChangePassword(
            binding.emailPasswordChange.text.toString(),
            binding.passwordPasswordChange.text.toString()
        )
        println(userChangePassword)

        userViewModel.updateUser(userChangePassword)
        binding.loadingChangePass.visibility = View.VISIBLE
        binding.btnChangePassword.visibility = View.GONE
        binding.btnReturnPC.visibility = View.GONE
        userViewModel.done.observe(viewLifecycleOwner) {
            if (it) {
                println("Contraseña cambiada con éxito")
                Toast.makeText(requireContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                binding.btnChangePassword.visibility = View.VISIBLE
                binding.btnReturnPC.visibility = View.VISIBLE
                println("Error al cambiar la contraseña")
                Toast.makeText(requireContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // function to check if all fields are filled and both passwords match
    fun validaciones(): Boolean{
        // one more validation to check if emailPasswordChange is empty
        if (binding.emailPasswordChange.text.toString().isEmpty()){
            binding.emailPasswordChange.error = "Este campo no puede estar vacío"
            return false
        }

        if (binding.passwordPasswordChange.text.toString().isEmpty()){
            binding.passwordPasswordChange.error = "Este campo no puede estar vacío"
            return false
        }
        if (binding.passwordPasswordChange.text.toString().length < 4){
            binding.passwordPasswordChange.error = "La contraseña debe tener al menos 5 caracteres"
            return false
        }
        if (binding.passwordPasswordChange.text.toString() != binding.passwordRepPasswordChange.text.toString()){
            binding.passwordRepPasswordChange.error = "Las contraseñas no coinciden"
            return false
        }
        return true
    }

}