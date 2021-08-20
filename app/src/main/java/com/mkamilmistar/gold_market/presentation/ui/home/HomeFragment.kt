package com.mkamilmistar.gold_market.presentation.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.util.ArraySet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.*
import com.mkamilmistar.gold_market.data.model.entity.*
import com.mkamilmistar.gold_market.data.model.request.UpdatePocketRequest
import com.mkamilmistar.gold_market.data.model.response.Customer
import com.mkamilmistar.gold_market.data.model.response.Pocket
import com.mkamilmistar.gold_market.data.model.response.Purchase
import com.mkamilmistar.gold_market.data.model.response.PurchaseDetail
import com.mkamilmistar.gold_market.data.remote.RetrofitInstance
import com.mkamilmistar.gold_market.data.repository.*
import com.mkamilmistar.gold_market.databinding.FragmentHomeBinding
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModelFactory
import com.mkamilmistar.gold_market.presentation.viewModel.product.ProductViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.product.ProductViewModelFactory
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModelFactory
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModelFactory
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.formatDate
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus
import java.time.LocalDateTime

class HomeFragment : Fragment() {

  private lateinit var binding: FragmentHomeBinding
  private lateinit var purchaseViewModels: PurchaseViewModel
  private lateinit var productViewModels: ProductViewModel
  private lateinit var profileViewModels: ProfileViewModel
  private lateinit var pocketViewModels: PocketViewModel
  private lateinit var activateCustomer: String
  private var activatePocket: String = "1"
  private lateinit var purchase: com.mkamilmistar.gold_market.data.model.response.Purchase
  private lateinit var product: Product
  private lateinit var pocket: Pocket
  private lateinit var customerPockets: List<Pocket>

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
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
    val db = AppDatabase.getDatabase(requireContext())
    val api = RetrofitInstance
    val purchaseApi = api.purchaseApi
    val pocketApi = api.pocketApi
    val productApi = api.productApi
    val profileApi = api.profileApi
    val purchaseRepo = PurchaseRepositoryImpl(db, purchaseApi)
    val pocketRepo = PocketRepositoryImpl(pocketApi)
    val productRepository = ProductRepositoryImpl(db, productApi)
    val profileRepo = ProfileRepositoryImpl(db, profileApi)
    productViewModels = ViewModelProvider(
      this,
      ProductViewModelFactory(productRepository)
    ).get(ProductViewModel::class.java)
    purchaseViewModels = ViewModelProvider(
      this,
      PurchaseViewModelFactory(purchaseRepo)
    ).get(PurchaseViewModel::class.java)
    profileViewModels = ViewModelProvider(
      this,
      ProfileViewModelFactory(profileRepo)
    ).get(ProfileViewModel::class.java)
    pocketViewModels =
      ViewModelProvider(this, PocketViewModelFactory(pocketRepo)).get(PocketViewModel::class.java)
  }

  private fun initShared() {
    val sharedPreferences = SharedPref(requireContext())
    activateCustomer = sharedPreferences.retrived(Utils.CUSTOMER_ID).toString()
    activatePocket = sharedPreferences.retrived(Utils.POCKET_ID).toString()
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initShared()
    product = Product(
      productId = 1,
      productName = "TOLOL",
      productImage = "TEMPE",
      productPriceBuy = 100000,
      productPriceSell = 120000,
      productStatus = 1,
      updatedDate = "12 March 2021",
      createdDate = "10 March 2021"
    )
    pocketViewModels.start(activateCustomer)
    pocketViewModels.getPocketWithCustomerIdAndPocketId(activatePocket)
    productViewModels.getProduct("4028e4b97b5e973a017b5e99802a0000")
//    productViewModels.updateProduct(productId = 1)
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
            Log.d("PocketApiById", "Subscribe : ${it.data}")
            if (it.data != null) {
              pocket = it.data
              val totalAmount = (pocket.pocketQty * product.productPriceSell.toDouble())
              pocketNameText.text = pocket.pocketName
              totalGramText.text = "${pocket.pocketQty} /gr"
              totalPriceText.text = Utils.currencyFormatter(totalAmount)
            }
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            pocketNameText.text = "Create pocket first"
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
      productViewModels.productLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("ProductApi", "Subscribe : ${it.data}")
            val productData = it.data
            if (productData != null) {
              priceBuyAmount.text = Utils.currencyFormatter(productData.productPriceBuy)
              priceSellAmount.text = Utils.currencyFormatter(productData.productPriceSell)
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
            val customer = it.data
            if (customer != null) {
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
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), "Gagal Mendapatkan List Pocket", Toast.LENGTH_SHORT)
              .show()
            hideProgressBar()
          }
        }
      })
      pocketViewModels.pocketCustomerLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PocketApi", "Subscribe : ${it.data}")
            if (it.data != null){
              customerPockets = it.data
              totalPocketsText.text = "Your Total Pockets: ${customerPockets.size}"
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

  private fun purchaseDialog(title: String, message: String, purchaseType: Int) {
    val inputQty = EditText(requireActivity())
    inputQty.inputType = InputType.TYPE_CLASS_NUMBER
    inputQty.setRawInputType(Configuration.KEYBOARD_12KEY)
    val dialog = AlertDialog.Builder(requireContext())
      .setTitle("Create New Purchase")
      .setMessage("Input Quantity")
      .setView(inputQty)
      .setPositiveButton("Purchase") { _, _ ->
        val qty: Double = inputQty.text.toString().toDouble()
        val updatePocket: com.mkamilmistar.gold_market.data.model.response.Pocket =
          pocketViewModels.pocketCustomer.copy()
        val price = if (purchaseType == 0) {
          updatePocket.pocketQty += qty.toInt()
          product.productPriceBuy * qty
        } else {
          updatePocket.pocketQty -= qty.toInt()
          product.productPriceSell * qty
        }
//        purchase = Purchase(
//          purchaseType = 1, purchaseDate = "", purchaseDetail = ArraySet(1)
//        )
        pocketViewModels.updatePocket(UpdatePocketRequest(""), "")
//        showDialog(title, message, purchase)
        showDialog(title, message)
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun showDialog(title: String, message: String) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
//          purchaseViewModels.purchaseProduct("", purchase)
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
