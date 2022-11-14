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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentCategoryListBinding
import com.jacksafblaze.storeowner.presentation.ToolbarTitleSetter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
        changeToolbarTitle()
        setupFab()
        setupRecyclerView()
        bindState()
    }

    private fun bindState(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect { state ->
                    if(state.isLoading){
                        showProgressBar()
                    }
                    else{
                        hideProgressBar()
                    }
                    if(state.categories.isNotEmpty()){
                        hideNoCategoriesText()
                        adapter.setList(state.categories)
                    }
                    else{
                        showNoCategoriesText()
                    }
                    if(state.message != null){
                        Snackbar.make(requireView(), state.message, Snackbar.LENGTH_LONG).show()
                        viewModel.userMessageShown()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(){
        adapter = CategoryAdapter{categoryId, categoryName ->
            val navController = findNavController()
            if(navController.currentDestination?.id == R.id.categoryListFragment){
                val action = CategoryListFragmentDirections.actionCategoryListFragmentToProductListFragment(categoryId, categoryName)
                navController.navigate(action)
            }
        }
        binding.categoryList.adapter = adapter
        binding.categoryList.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun setupFab(){
        binding.addCategoryFab.setOnClickListener{
            val navController = findNavController()
            if(navController.currentDestination?.id == R.id.categoryListFragment) {
                val action =
                    CategoryListFragmentDirections.actionCategoryListFragmentToAddCategoryFragment()
                navController.navigate(action)
            }
        }
    }
    private fun changeToolbarTitle(){
        val navHostFragment = parentFragment as NavHostFragment
        (navHostFragment.parentFragment as ToolbarTitleSetter).setTitle("Categories")
    }
    private fun showNoCategoriesText(){
        binding.noCategories.visibility = View.VISIBLE
    }
    private fun hideNoCategoriesText(){
        binding.noCategories.visibility = View.GONE
    }
    private fun showProgressBar(){
        binding.categoriesProgressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        binding.categoriesProgressBar.visibility = View.GONE
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}