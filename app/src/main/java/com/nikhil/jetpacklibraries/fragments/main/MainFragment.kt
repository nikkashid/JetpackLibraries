package com.nikhil.jetpacklibraries.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nikhil.jetpacklibraries.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var mainBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController: NavController = Navigation.findNavController(view)
        val action =
            MainFragmentDirections.actionMainFragmentToWorkManagerFragment(
                "Testing the safeArg",
                28
            )

        mainBinding.btnWorkManager.setOnClickListener {
            navController.navigate(action)
        }

    }

}