package com.mkamilmistar.gold_market.ui.pocket

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.model.Pocket
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.databinding.FragmentPocketBinding
import com.mkamilmistar.gold_market.databinding.FragmentRegisterBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.ui.history.HistoryAdapter
import com.mkamilmistar.gold_market.ui.history.HistoryViewModel
import com.mkamilmistar.gold_market.utils.getRandomString
import kotlin.random.Random

class PocketFragment : Fragment(), PocketAdapter.OnClickItemListener {

  private lateinit var binding: FragmentPocketBinding
  private lateinit var pocketAdapter: PocketAdapter

  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().pocketViewModel as T
    }
  }
  private val viewModel: PocketViewModel by viewModels { factory }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentPocketBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    viewModel.start()
    binding.apply {
      recycleViewPocket.apply {
        layoutManager = LinearLayoutManager(context)
        adapter = pocketAdapter
      }

      fabAddPocket.setOnClickListener {
        val inputEditTextField = EditText(requireActivity())
        val dialog = AlertDialog.Builder(requireContext())
          .setTitle("Create New Pocket")
          .setMessage("Input Pocket Name")
          .setView(inputEditTextField)
          .setPositiveButton("Create Pocket") { _, _ ->
            val editTextInput = inputEditTextField.text.toString()
            val newPocket = Pocket(getRandomString(5), editTextInput, 0)
            viewModel.addPocket(newPocket)
          }
          .setNegativeButton("Cancel", null)
          .create()
        dialog.show()
      }
    }
  }

  private fun subscribe() {
    binding.apply {
      pocketAdapter = PocketAdapter(this@PocketFragment)
      val historyObserver: Observer<EventResult<List<Pocket>>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> Log.d("PocketFragment", "Loading...")
          is EventResult.Success -> {
            Log.d("PocketFragment", "Success...")
            pocketAdapter.updateData(event.data)
          }
          is EventResult.Failed -> Log.d("HistoryFragment", "FAILED")
          else -> {
          }
        }
      }
      viewModel.pocketLiveData.observe(viewLifecycleOwner, historyObserver)
    }
  }

  override fun onClickItem(position: Int) {
    Toast.makeText(context, viewModel.getPocket(position).id, Toast.LENGTH_SHORT).show()
  }

  override fun deleteItem(position: Int) {
    showDialog("Are you sure want to delete this pocket?", "Click OK to Continue", position)
  }

  override fun editItem(position: Int) {
    Toast.makeText(context, viewModel.getPocket(position).id, Toast.LENGTH_SHORT).show()
  }

  private fun showDialog(title: String, message: String, position: Int) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
          viewModel.deletePocket(position)
        }
        DialogInterface.BUTTON_NEUTRAL -> {
        }
      }
    }
    builder.setPositiveButton("YES", dialogClickListener)
    builder.setNeutralButton("CANCEL", dialogClickListener)
    dialog = builder.create()
    dialog.show()
  }
}
