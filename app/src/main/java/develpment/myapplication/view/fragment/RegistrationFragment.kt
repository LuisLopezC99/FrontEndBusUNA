package develpment.myapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import develpment.myapplication.databinding.FragmentRegistrationBinding
import develpment.myapplication.model.UserSignupInput
import develpment.myapplication.repository.UserRepository
import develpment.myapplication.service.UserService
import develpment.myapplication.viewmodel.UserViewModel
import develpment.myapplication.viewmodel.UserViewModelFactory


class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentRegistrationBinding.inflate(inflater, container, false)



        binding.btnReturnReg.setOnClickListener {
            findNavController().popBackStack()
        }


        val userService = UserService.getInstance()
        val userRepository = UserRepository(userService)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]

        binding.btnRegistrarseReg.setOnClickListener {
            registerUser(userViewModel)
        }

        return binding.root
    }

    fun registerUser(userViewModel: UserViewModel){
        //ensure that all fields are filled and both passwords match
        if (!validaciones()){
            return
        }

        val usuario = UserSignupInput(
            binding.nameReg.text.toString(),
            binding.lastNameReg.text.toString(),
            binding.emailReg.text.toString(),
            binding.passwordReg.text.toString(),
            if (binding.carreraReg.text.toString() == "") "No suministrada" else binding.carreraReg.text.toString(),
            if (binding.spinnerOcupacion.selectedItem.toString() == "Estudiante") "student" else "driver"
        )
        println(usuario)

        userViewModel.registerUser(usuario)
        binding.loadingRegistration.visibility = View.VISIBLE
        userViewModel.done.observe(viewLifecycleOwner){
            if(it){
                binding.loadingRegistration.visibility = View.GONE
                Toast.makeText(requireContext(), "Usuario registrado", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }else{
                binding.loadingRegistration.visibility = View.GONE
                Toast.makeText(requireContext(), "Error al registrar usuario, el usuario ya esta creado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun validaciones(): Boolean{
        if(binding.nameReg.text.toString().isEmpty()){
            binding.nameReg.error = "Por favor ingrese su nombre"
            binding.nameReg.requestFocus()
            return false
        }
        if(binding.lastNameReg.text.toString().isEmpty()){
            binding.lastNameReg.error = "Por favor ingrese su apellido"
            binding.lastNameReg.requestFocus()
            return false
        }
        if(binding.emailReg.text.toString().isEmpty()){
            binding.emailReg.error = "Por favor ingrese su correo electrónico"
            binding.emailReg.requestFocus()
            return false
        }
        if(binding.passwordReg.text.toString().isEmpty()){
            binding.passwordReg.error = "Por favor ingrese su contraseña"
            binding.passwordReg.requestFocus()
            return false
        }
        if(binding.passwordRepReg.text.toString().isEmpty()){
            binding.passwordRepReg.error = "Por favor ingrese su contraseña"
            binding.passwordRepReg.requestFocus()
            return false
        }
        if(binding.passwordReg.text.toString() != binding.passwordRepReg.text.toString()){
            binding.passwordRepReg.error = "Las contraseñas no coinciden"
            binding.passwordRepReg.requestFocus()
            return false
        }
        return true
    }

}