package com.jacksafblaze.storeowner.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {
    val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.email.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validateEmail(p0.toString())
            }

        })
        binding.password.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.validatePassword(p0.toString())
            }

        })
        bindState()
        binding.login.setOnClickListener {
            viewModel.login()
        }
        binding.register.setOnClickListener {
            viewModel.register()
        }
    }

    private fun bindState(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest {
                    if(it.isLoading){
                        binding.loading.visibility = View.VISIBLE
                    }
                    else{
                        binding.loading.visibility = View.GONE
                    }
                    if(it.message != null){
                        Snackbar.make(requireView(), it.message, Snackbar.LENGTH_LONG).show()
                        viewModel.userMessageShown()
                    }
                    if(it.emailAlert != null){
                        binding.emailAlert.visibility = View.VISIBLE
                        binding.emailAlert.text = it.emailAlert
                    }
                    else if(it.isEmailOk){
                        binding.emailAlert.visibility = View.GONE
                    }
                    if(it.passwordAlert != null){
                        binding.passwordAlert.visibility = View.VISIBLE
                        binding.passwordAlert.text = it.passwordAlert
                    }
                    else if(it.isPasswordOk){
                        binding.passwordAlert.visibility = View.GONE
                    }
                    if(it.isLoggedIn && it.isVerified){
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                    binding.login.isEnabled = it.buttonsEnabled
                    binding.register.isEnabled = it.buttonsEnabled
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}