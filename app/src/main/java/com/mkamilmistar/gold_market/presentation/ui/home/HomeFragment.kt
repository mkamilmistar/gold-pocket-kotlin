package com.mkamilmistar.gold_market.presentation.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.remote.request.PocketRequest
import com.mkamilmistar.gold_market.data.remote.request.PurchaseDetailRequest
import com.mkamilmistar.gold_market.data.remote.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.remote.response.*
import com.mkamilmistar.gold_market.data.remote.entity.Customer
import com.mkamilmistar.gold_market.data.remote.entity.Pocket
import com.mkamilmistar.gold_market.data.remote.entity.Product
import com.mkamilmistar.gold_market.data.repository.*
import com.mkamilmistar.gold_market.databinding.FragmentHomeBinding
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.product.ProductViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModel
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.ViewModelFactoryBase
import com.mkamilmistar.gold_market.utils.showOKDialog
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

  private lateinit var binding: FragmentHomeBinding
  private lateinit var activateCustomer: String
  private var activatePocket: String = "1"
  private lateinit var product: Product
  private lateinit var pocket: Pocket
  private lateinit var customer: Customer
  private lateinit var customerPockets: List<Pocket>

  @Inject
  lateinit var purchaseViewModels: PurchaseViewModel

  @Inject
  lateinit var productViewModels: ProductViewModel

  @Inject
  lateinit var profileViewModels: ProfileViewModel

  @Inject
  lateinit var pocketViewModels: PocketViewModel

  private lateinit var sharedPreferences: SharedPref

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    sharedPreferences = SharedPref(requireContext())
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
    return binding.apply {
      lifecycleOwner = this@HomeFragment
      fragment = this@HomeFragment
      pocketViewModel = pocketViewModels
      productViewModel = productViewModels
      profileViewModel = profileViewModels
      purchaseViewModel = purchaseViewModels
    }.root
  }

  private fun initViewModel() {
    ViewModelProvider(this, ViewModelFactoryBase {
      productViewModels }).get(ProductViewModel::class.java)
    ViewModelProvider(this, ViewModelFactoryBase {
      profileViewModels }).get(ProfileViewModel::class.java)
    ViewModelProvider(this, ViewModelFactoryBase {
      purchaseViewModels }).get(PurchaseViewModel::class.java)
    ViewModelProvider(this, ViewModelFactoryBase {
      pocketViewModels }).get(PocketViewModel::class.java)
  }

  private fun initShared() {
    activateCustomer = sharedPreferences.retrieved(Utils.CUSTOMER_ID).toString()
    activatePocket = sharedPreferences.retrieved(Utils.POCKET_ID).toString()
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initShared()
    pocketViewModels.start(activateCustomer)
    pocketViewModels.getPocketCustomerById(activatePocket)
    productViewModels.getProductList()
    profileViewModels.getCustomerById(activateCustomer)
    subscribe()
    binding.apply {
    }
  }

  fun createPocketHandle() {
    findNavController()
      .navigate(R.id.action_homeFragment_to_pocketFragment)
  }

  fun changePocketHandle() {
    findNavController()
      .navigate(R.id.action_homeFragment_to_pocketFragment)
  }

  fun buyProductHandle() {
    purchaseDialog("Are sure want to BUY this product?", "Click OK to Continue", 0)
  }

  fun sellProductHandle() {
    purchaseDialog("Are sure want to SELL this product?", "Click OK to Continue", 1)
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    hideProgressBar()
    binding.apply {
      pocketViewModels.pocketLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PocketApi", "Subscribe : ${it.data}")
            if (it.data != null) {
              pocket = it.data
              val totalAmount = (pocket.pocketQty * pocket.product.productPriceSell.toDouble())
              pocketNameText.text = pocket.pocketName
              totalGramText.text = "${pocket.pocketQty} /gr"
              totalPriceText.text = Utils.currencyFormatter(totalAmount)
            }
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            pocketNameText.text = "-"
            totalGramText.text = "0 /gr"
            totalPriceText.text = Utils.currencyFormatter(0.0)
            Toast.makeText(
              requireContext(),
              "Gagal Mendapatkan Pocket Customer",
              Toast.LENGTH_SHORT
            ).show()
            hideProgressBar()
          }
        }
      })
      productViewModels.productListLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("ProductApi", "Subscribe : ${it.data}")
            if (it.data?.isNotEmpty() == true) {
              product = it.data.last()
              priceBuyAmount.text = Utils.currencyFormatter(product.productPriceBuy)
              priceSellAmount.text = Utils.currencyFormatter(product.productPriceSell)
              sharedPreferences.save(Utils.PRODUCT_ID, product.id)
            } else {
              productViewModels.createProduct()
            }
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), "Gagal Mendapatkan Product", Toast.LENGTH_SHORT).show()
            hideProgressBar()
          }
        }
      })
      profileViewModels.customerLivedata.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("ProfileApi", "Subscribe : ${it.data}")
            if (it.data != null) {
              customer = it.data
              greetingHomeText.text = "Hi, ${customer.firstName} ${customer.lastName}"
            }
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), "Gagal Mendapatkan Data Customer", Toast.LENGTH_SHORT)
              .show()
            hideProgressBar()
          }
        }
      })
      purchaseViewModels.purchaseLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PocketApi", "Subscribe : ${it.data}")
            val purchaseResponse = it.data
            if (purchaseResponse != null) {
              if (purchaseResponse.isSuccess) {
                pocketViewModels.getPocketCustomerById(activatePocket)
                Toast.makeText(context, "Purchased Success", Toast.LENGTH_SHORT).show()
                hideProgressBar()
              }
            }
          }
          ResourceStatus.ERROR -> {
            val message = "Failed to Purchase"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            hideProgressBar()
          }
        }
      })
      pocketViewModels.pocketCustomerLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PocketApi", "Subscribe : ${it.data}")
            if (it.data != null) {
              customerPockets = it.data
              totalPocketsText.text = "Your Total Pockets: ${customerPockets.size}"
            }
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), "Gagal Mendapatkan List Pocket", Toast.LENGTH_SHORT)
              .show()
            hideProgressBar()
          }
        }
      })
    }
  }

  private fun purchaseDialog(title: String, message: String, purchaseType: Int) {
    val inputQty = EditText(requireActivity())
    inputQty.inputType = InputType.TYPE_CLASS_NUMBER
    inputQty.setRawInputType(Configuration.KEYBOARD_12KEY)
    val dialog = AlertDialog.Builder(requireContext())
      .setTitle("Create New Purchase")
      .setMessage("Input Quantity")
      .setView(inputQty)
      .setPositiveButton("Purchase") { _, _ ->
        var qty = 0.0
        inputQty.text.toString().apply {
          if(this.isNotEmpty()) {
            qty = this.toDouble()
          }
        }

        val pocketRequest = PocketRequest(id = activatePocket)
        val purchaseDetailRequest =
          PurchaseDetailRequest(quantityInGram = qty, pocket = pocketRequest)
        val purchaseRequest = PurchaseRequest(
          purchaseType = purchaseType,
          purchaseDetails = listOf(purchaseDetailRequest)
        )
        when {
          qty.toInt() <= 0 -> {
            showOKDialog(
              requireContext(),
              "Failed Purchase",
              "Input Was Wrong"
            )
          }
          activatePocket.isEmpty() -> {
            showOKDialog(
              requireContext(),
              "Failed Purchase",
              "Your Pocket is Not Selected or Not Exist!"
            )
          }
          purchaseType == 1 && pocket.pocketQty - qty < 0 -> {
            showOKDialog(
              requireContext(),
              "Failed Purchase",
              "Your Pocket Quantity Cannot be Minus"
            )
          }
          customerPockets.isNotEmpty() -> {
            showDialog(title, message, purchaseRequest)
          }
        }
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun showDialog(title: String, message: String, purchase: PurchaseRequest) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
          purchaseViewModels.purchaseProduct(activateCustomer, purchase)
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
    binding.progressBarHome.visibility = View.GONE
  }

  private fun showProgressBar() {
    binding.progressBarHome.visibility = View.VISIBLE
  }
}
