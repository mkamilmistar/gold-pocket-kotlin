package com.mkamilmistar.gold_market.ui.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkamilmistar.gold_market.data.model.ProductHistory
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.databinding.FragmentHistoryBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.helpers.EventResult

class HistoryFragment : Fragment(), HistoryAdapter.OnClickItemListener{

  private lateinit var binding: FragmentHistoryBinding
  private lateinit var historyAdapter: HistoryAdapter

  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().historyViewModel as T
    }
  }
  private val viewModel: HistoryViewModel by viewModels { factory }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentHistoryBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    viewModel.start()
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
      viewModel.historyLiveData.observe(viewLifecycleOwner, historyObserver)
    }
  }

  override fun onClickItem(position: Int) {
    Toast.makeText(context, viewModel.getHistory(position).id, Toast.LENGTH_SHORT).show()
  }
}
