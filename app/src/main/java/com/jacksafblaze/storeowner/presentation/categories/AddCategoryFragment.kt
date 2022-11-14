package com.jacksafblaze.storeowner.presentation.categories

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentAddCategoryBinding
import com.jacksafblaze.storeowner.presentation.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCategoryFragment : Fragment() {
    val viewModel: AddCategoryViewModel by viewModels()
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    private val getContent : ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()){ imageUri ->
        viewModel.setImage(imageUri = imageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindState()
        binding.name.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.setName(p0.toString())
            }

        })
        binding.addImage.setOnClickListener{
            getContent.launch("image/*")
        }
        binding.add.setOnClickListener {
            viewModel.addCategory()
        }
        binding.cancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindState(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest { state ->
                    if(state.imageUri != null){
                        Glide.with(requireView()).load(state.imageUri).into(binding.image)
                    }
                    if(state.isCategoryAdded){
                        val navController = findNavController()
                        if(navController.currentDestination?.id == R.id.addCategoryFragment) {
                            findNavController().navigateUp()
                        }
                    }
                    if(!state.message.isNullOrBlank()){
                        Snackbar.make(requireView(), state.message, Snackbar.LENGTH_LONG).show()
                        viewModel.userMessageShown()
                    }
                    binding.nameInputLayout.error = state.nameAlert
                    binding.nameInputLayout.isErrorEnabled = !state.nameAlert.isNullOrBlank()
                    binding.add.isEnabled = state.isAddButtonEnabled
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}