package com.mkamilmistar.gold_market.presentation.ui.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.PurchaseRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentHistoryBinding
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.presentation.viewModel.history.HistoryViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.history.HistoryViewModelFactory
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModelFactory

class HistoryFragment : Fragment(), HistoryAdapter.OnClickItemListener{

  private lateinit var binding: FragmentHistoryBinding
  private lateinit var historyAdapter: HistoryAdapter
  private lateinit var historyViewModel: HistoryViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
    return binding.apply {
      lifecycleOwner = this@HistoryFragment
      viewmodel = historyViewModel
    }.root
  }

  private fun initViewModel() {
    val db = AppDatabase.getDatabase(requireContext())
    val repo = PurchaseRepositoryImpl(db)
    historyViewModel = ViewModelProvider(this, HistoryViewModelFactory(repo)).get(
      HistoryViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    historyViewModel.start(1)
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
      val historyObserver: Observer<EventResult<CustomerWithPurchases>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            Log.d("HistoryFragment", "Success...")
            historyAdapter.updateData(event.data)
            hideProgressBar()
          }
          is EventResult.Failed -> {
            hideProgressBar()
            Toast.makeText(context, event.errorMessage.toString(), Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      historyViewModel.historyLiveData.observe(viewLifecycleOwner, historyObserver)
    }
  }

  override fun onClickItem(position: Int) {
    Toast.makeText(context, historyViewModel.getPurchaseById(position).purchaseId.toString(), Toast.LENGTH_SHORT).show()
  }

  private fun hideProgressBar() {
    binding.progressBarHistory.visibility = View.GONE
  }

  private fun showProgressBar() {
    binding.progressBarHistory.visibility = View.VISIBLE
  }
}