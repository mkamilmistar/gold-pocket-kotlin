package com.mkamilmistar.gold_market.presentation.ui.pocket

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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.repository.CustomerRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.PocketRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentPocketBinding
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModelFactory
import com.mkamilmistar.gold_market.utils.SharedPref

class PocketFragment : Fragment(), PocketAdapter.OnClickItemListener {

  private lateinit var binding: FragmentPocketBinding
  private lateinit var pocketAdapter: PocketAdapter
  private lateinit var pocketViewModel: PocketViewModel
  private lateinit var activateCustomer: String

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pocket, container, false)
    return binding.apply {
      lifecycleOwner = this@PocketFragment
      viewmodel = pocketViewModel
    }.root
  }

  private fun initViewModel() {
    val db = AppDatabase.getDatabase(requireContext())
    val customerRepo = CustomerRepositoryImpl(db)
    val pocketRepo = PocketRepositoryImpl(db)
    pocketViewModel = ViewModelProvider(this, PocketViewModelFactory(customerRepo, pocketRepo)).get(PocketViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    val sharedPreferences = SharedPref(requireContext())
    activateCustomer = sharedPreferences.retrived("ID").toString()
    if (!activateCustomer.equals(null)) {
      pocketViewModel.start(activateCustomer.toInt())
    } else {
      pocketViewModel.start(0)
    }

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
            val newPocket = Pocket(pocketName = editTextInput, pocketQty = 0, customerPocketId = activateCustomer.toInt(), productPocketId = 1)
            pocketViewModel.createPocket(newPocket, activateCustomer.toInt())
          }
          .setNegativeButton("Cancel", null)
          .create()
        dialog.show()
      }
    }
  }

  private fun subscribe() {
    hideProgressBar()
    binding.apply {
      pocketAdapter = PocketAdapter(this@PocketFragment)
      val pocketObserver: Observer<EventResult<CustomerWithPockets>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            pocketAdapter.updateData(event.data)
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
      pocketViewModel.pocketCustomerLiveData.observe(viewLifecycleOwner, pocketObserver)
    }
  }

  override fun onClickItem(position: Int) {
    Toast.makeText(context, pocketViewModel.getPocketById(position).pocketName, Toast.LENGTH_SHORT).show()
  }

  override fun deleteItem(position: Int) {
    showDialog("Are you sure want to delete this pocket?", "Click OK to Continue", position)
  }

  override fun editItem(position: Int) {
    Toast.makeText(context, pocketViewModel.getPocketById(position).customerPocketId, Toast.LENGTH_SHORT).show()
  }

  private fun showDialog(title: String, message: String, position: Int) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
          pocketViewModel.deletePocket(position, activateCustomer.toInt())
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

  private fun hideProgressBar() {
    binding.progressBarPocket.visibility = View.GONE
  }

  private fun showProgressBar() {
    binding.progressBarPocket.visibility = View.VISIBLE
  }
}
