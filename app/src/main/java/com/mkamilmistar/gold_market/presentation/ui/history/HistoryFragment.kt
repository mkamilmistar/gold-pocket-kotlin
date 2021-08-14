package com.mkamilmistar.gold_market.presentation.ui.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.PurchaseRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentHistoryBinding
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModelFactory

class HistoryFragment : Fragment(), HistoryAdapter.OnClickItemListener{

  private lateinit var binding: FragmentHistoryBinding
  private lateinit var historyAdapter: HistoryAdapter

  private lateinit var purchaseViewModel: PurchaseViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
    return binding.apply {
      lifecycleOwner = this@HistoryFragment
      viewmodel = purchaseViewModel
    }.root
  }

  private fun initViewModel() {
    val db = AppDatabase.getDatabase(requireContext())
    val repo = PurchaseRepositoryImpl(db)
    purchaseViewModel = ViewModelProvider(this, PurchaseViewModelFactory(repo)).get(
      PurchaseViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    purchaseViewModel.start()
    binding.apply {
      recycleViewHistory.apply {
        layoutManager = LinearLayoutManager(context)
        adapter = historyAdapter
      }
    }
  }

  private fun subscribe() {
    binding.apply {
      historyAdapter = HistoryAdapter(this@HistoryFragment)
      val historyObserver: Observer<EventResult<List<Purchase>>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> Log.d("HistoryFragment", "Loading...")
          is EventResult.Success -> {
            Log.d("HistoryFragment", "Success...")
            historyAdapter.updateData(event.data)
          }
          is EventResult.Failed -> Log.d("HistoryFragment", "FAILED")
          else -> {
          }
        }
      }
//      transactionViewModel.historyLiveData.observe(viewLifecycleOwner, historyObserver)
    }
  }

  override fun onClickItem(position: Int) {
//    Toast.makeText(context, transactionViewModel.getHistory(position).purchaseId, Toast.LENGTH_SHORT).show()
  }
}
