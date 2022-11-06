package com.jacksafblaze.storeowner.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentProductListBinding


class ProductListFragment : Fragment() {
    val viewModel: ProductListViewModel by viewModels()
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun setupRecyclerView(){
        binding.productList.adapter = ProductAdapter()
        binding.productList.layoutManager = LinearLayoutManager(requireContext())
    }
    fun bindState(){

    }
}