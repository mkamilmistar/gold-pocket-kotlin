package com.mkamilmistar.gold_market.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.repository.CustomerRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentHomeBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.utils.Utils

class HomeFragment : Fragment() {

  private lateinit var binding: FragmentHomeBinding
  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().homeViewModel as T
    }
  }
  private val viewModel: HomeViewModel by viewModels { factory }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val passEmail = arguments?.getString("EMAIL")
    val passPwd = arguments?.getString("PASSWORD")
    subscribe()
    val dummyUser = CustomerRepositoryImpl().customerDBImport
    viewModel.start(dummyUser.email, dummyUser.password)
    binding.apply {
      btnCreatePocket.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_homeFragment_to_pocketFragment)
      }

      btnChangePocket.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_homeFragment_to_pocketFragment)
      }
    }
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    binding.apply {
      val customerObserver: Observer<EventResult> = Observer<EventResult> { event ->
        when (event) {
          is EventResult.Loading -> Log.d("HomeFragment", "Loading...")
          is EventResult.Success -> {
            Log.d("HomeFragment", "Success...")
            val customer = event.data as Customer
            greetingHomeText.text = "Hi, ${customer.firstName}"
          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      viewModel.customerLiveData.observe(viewLifecycleOwner, customerObserver)
    }
  }

}
