package com.jacksafblaze.storeowner.presentation.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.jacksafblaze.storeowner.R
import com.jacksafblaze.storeowner.databinding.FragmentAddProductBinding


class AddProductFragment : Fragment() {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    val getContent : ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.GetContent()){ imageUri ->
        //TODO

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}