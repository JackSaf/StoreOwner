package com.jacksafblaze.storeowner.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentCategoryListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CategoryListFragment : Fragment() {
    val viewModel: CategoryListViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        bindState()
    }

    private fun bindState(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest { state ->
                    if(state.isLoading){
                        showProgressBar()
                    }
                    else{
                        hideProgressBar()
                    }
                    if(state.message != null){
                        Snackbar.make(requireView(), state.message, Snackbar.LENGTH_LONG).show()
                        viewModel.userMessageShown()
                    }
                    if(state.categories.isNotEmpty()){
                        binding.noCategories.visibility = View.GONE
                        adapter.setList(state.categories)
                    }
                    else{
                        binding.noCategories.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = CategoryAdapter()
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun showProgressBar(){

    }
    private fun hideProgressBar(){

    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}