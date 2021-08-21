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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.remote.request.CreatePocketRequest
import com.mkamilmistar.gold_market.data.remote.RetrofitInstance
import com.mkamilmistar.gold_market.data.repository.PocketRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentPocketBinding
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModelFactory
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.showOKDialog
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus

class PocketFragment : Fragment(), PocketAdapter.OnClickItemListener {

  private lateinit var binding: FragmentPocketBinding
  private lateinit var pocketAdapter: PocketAdapter
  private lateinit var pocketViewModel: PocketViewModel
  private lateinit var activateCustomer: String
  private lateinit var activateProduct: String

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
    val pocketApi = RetrofitInstance.pocketApi
    val pocketRepo = PocketRepositoryImpl(pocketApi)
    pocketViewModel = ViewModelProvider(this, PocketViewModelFactory(pocketRepo)).get(PocketViewModel::class.java)
  }

  private fun initShared() {
    val sharedPreferences = SharedPref(requireContext())
    activateCustomer = sharedPreferences.retrieved(Utils.CUSTOMER_ID).toString()
    activateProduct = sharedPreferences.retrieved(Utils.PRODUCT_ID).toString()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    initShared()

    if (!activateCustomer.equals(null)) {
      pocketViewModel.start(activateCustomer)
    } else {
      pocketViewModel.start("")
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
            val newPocket = CreatePocketRequest(
              pocketName = editTextInput, product = CreatePocketRequest.ProductId(activateProduct), customer = CreatePocketRequest.CustomerId(activateCustomer)
            )
            pocketViewModel.createPocket(newPocket, activateCustomer)
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
      pocketViewModel.pocketCustomerLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PocketApi", "Subscribe : ${it.data}")
            pocketAdapter.updateData(it.data)
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), "Gagal Mendapatkan Data Pocket List", Toast.LENGTH_SHORT).show()
            hideProgressBar()
          }
        }
      })
      pocketViewModel.isExistDataValidate.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PocketApi", "Subscribe : ${it.data}")
            if (it.data == false) {
              showOKDialog(requireContext(), "Gagal Menghapus Pocket", "Data Pocket Masih Ada!")
            }
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            hideProgressBar()
          }
        }
      })
    }
  }

  private fun navToHome() {
    findNavController().navigate(
      R.id.homeFragment, null,
      NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
    )
  }

  override fun onClickItem(position: Int) {
    val sharedPref = SharedPref(requireContext())
    val selectedPocket = pocketViewModel.getPocketById(position)
    sharedPref.save(Utils.POCKET_ID, selectedPocket.id)
    navToHome()
    Toast.makeText(context, "${selectedPocket.pocketName} Selected", Toast.LENGTH_SHORT).show()
  }

  override fun deleteItem(position: Int) {
    deleteDialog("Are you sure want to delete this pocket?", "Click OK to Continue", position)
  }

  override fun editItem(position: Int) {
    updatePocket(position)
  }

  private fun updatePocket(position: Int) {
    val inputName = EditText(requireActivity())
    inputName.setText(pocketViewModel.getPocketById(position).pocketName)
    val dialog = AlertDialog.Builder(requireContext())
      .setTitle("Update Pocket")
      .setMessage("Input New Pocket Name")
      .setView(inputName)
      .setPositiveButton("Update Pocket") { _, _ ->
        val editTextInput = inputName.text.toString()
        pocketViewModel.updatePocket(editTextInput, position, activateCustomer, activateProduct)
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun deleteDialog(title: String, message: String, position: Int) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
          pocketViewModel.deletePocket(position, activateCustomer)
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

  override fun onDestroy() {
    super.onDestroy()
    Log.d("PocketFragment", "DESTROYED")
  }
}
