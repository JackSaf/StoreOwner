package com.jacksafblaze.storeowner.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentProductListBinding
import com.jacksafblaze.storeowner.presentation.ToolbarTitleSetter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    val viewModel: ProductListViewModel by viewModels()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeToolBarTitle()
        setupFab()
        setupRecyclerView()
        bindState()
        viewProducts()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setupRecyclerView(){
        adapter = ProductAdapter()
        binding.productList.adapter = adapter
        binding.productList.layoutManager = LinearLayoutManager(requireContext())
    }
    private fun bindState(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.uiState.collectLatest {state ->
                if(state.isLoading){
                    showProgressBar()
                }
                else{
                    hideProgressBar()
                }
                if(state.products.isNotEmpty()){
                    hideNoProductsText()
                    adapter.setList(state.products)
                }
                else{
                    showNoProductsText()
                }
            }
        }

    }
    private fun setupFab(){
        binding.addProductFab.setOnClickListener{
            val navController = findNavController()
            if(navController.currentDestination?.id == R.id.productListFragment){
                val action = ProductListFragmentDirections.actionProductListFragmentToAddProductFragment(viewModel.uiState.value.categoryId!!)
                navController.navigate(action)
            }
        }
    }
    private fun viewProducts(){
        val categoryId = arguments?.getString("categoryId")
        viewModel.viewProductsFromCategory(categoryId!!)
    }
    private fun changeToolBarTitle(){
        val categoryName = arguments?.getString("categoryName")
        val navHostFragment = parentFragment as NavHostFragment
        (navHostFragment.parentFragment as ToolbarTitleSetter).setTitle(categoryName!!)
    }
    private fun showNoProductsText(){
        binding.noProducts.visibility = View.VISIBLE
    }
    private fun hideNoProductsText(){
        binding.noProducts.visibility = View.GONE
    }
    private fun showProgressBar(){
        binding.productsProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.productsProgressBar.visibility = View.GONE
    }
}