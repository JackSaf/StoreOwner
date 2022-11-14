package com.jacksafblaze.storeowner.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), ToolbarTitleSetter {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupNavigationView()
        setupNavigation()
    }

    private fun setupToolbar(){
        binding.toolbar.setNavigationOnClickListener{
            binding.drawer.open()
        }

    }

    private fun setupNavigationView(){
        val headerText: TextView = binding.homeNavigationView.getHeaderView(0).findViewById(R.id.header_text)
        headerText.text = arguments?.getString("email")
    }

    private fun setupNavigation(){
        val navHost = childFragmentManager.findFragmentById(R.id.home_host_fragment) as NavHostFragment
        val navController = navHost.navController
        binding.homeNavigationView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setTitle(title: String) {
        binding.toolbar.title = title
    }

}