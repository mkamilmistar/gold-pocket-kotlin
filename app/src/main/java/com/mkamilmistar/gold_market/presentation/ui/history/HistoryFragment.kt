package com.mkamilmistar.gold_market.presentation.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.remote.RetrofitInstance
import com.mkamilmistar.gold_market.data.repository.PurchaseRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentHistoryBinding
import com.mkamilmistar.gold_market.presentation.viewModel.history.HistoryViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.history.HistoryViewModelFactory
import com.mkamilmistar.gold_market.presentation.viewModel.product.ProductViewModel
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.ViewModelFactoryBase
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HistoryFragment : DaggerFragment(), HistoryAdapter.OnClickItemListener{

  private lateinit var binding: FragmentHistoryBinding
  private lateinit var historyAdapter: HistoryAdapter
  private lateinit var activateCustomer: String

  private lateinit var sharedPreferences: SharedPref

  @Inject
  lateinit var historyViewModel: HistoryViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initShared()
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
    return binding.apply {
      lifecycleOwner = this@HistoryFragment
      viewmodel = historyViewModel
    }.root
  }

  private fun initShared() {
    sharedPreferences = SharedPref(requireContext())
    activateCustomer = sharedPreferences.retrieved(Utils.CUSTOMER_ID).toString()
  }

  private fun initViewModel() {
    ViewModelProvider(this, ViewModelFactoryBase {
      historyViewModel }).get(HistoryViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    historyViewModel.start(activateCustomer)
    binding.apply {
      recycleViewHistory.apply {
        layoutManager = LinearLayoutManager(context)
        adapter = historyAdapter
      }
    }
  }

  private fun subscribe() {
    hideProgressBar()
    binding.apply {
      historyAdapter = HistoryAdapter(this@HistoryFragment)
      historyViewModel.historyLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PurchaseAPI", "Subscribe : ${it.data}")
            historyAdapter.updateData(it.data)
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), "Failed Get History", Toast.LENGTH_SHORT).show()
            hideProgressBar()
          }
        }
      })
    }
  }

  override fun onClickItem(position: Int) {
    Toast.makeText(context, historyViewModel.getPurchaseById(position).id, Toast.LENGTH_SHORT).show()
  }

  private fun hideProgressBar() {
    binding.progressBarHistory.visibility = View.GONE
  }

  private fun showProgressBar() {
    binding.progressBarHistory.visibility = View.VISIBLE
  }
}
