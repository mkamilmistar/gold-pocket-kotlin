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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.remote.request.PocketRequest
import com.mkamilmistar.gold_market.data.remote.request.PurchaseDetailRequest
import com.mkamilmistar.gold_market.data.remote.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.remote.response.*
import com.mkamilmistar.gold_market.data.remote.RetrofitInstance
import com.mkamilmistar.gold_market.data.remote.entity.Customer
import com.mkamilmistar.gold_market.data.remote.entity.Pocket
import com.mkamilmistar.gold_market.data.remote.entity.Product
import com.mkamilmistar.gold_market.data.repository.*
import com.mkamilmistar.gold_market.databinding.FragmentHomeBinding
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
import com.mkamilmistar.gold_market.utils.showOKDialog
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus

class HomeFragment : Fragment() {

  private lateinit var binding: FragmentHomeBinding
  private lateinit var purchaseViewModels: PurchaseViewModel
  private lateinit var productViewModels: ProductViewModel
  private lateinit var profileViewModels: ProfileViewModel
  private lateinit var pocketViewModels: PocketViewModel
  private lateinit var activateCustomer: String
  private var activatePocket: String = "1"
  private lateinit var purchase: PurchaseRequest
  private lateinit var purchaseDetail: PurchaseDetailRequest
  private lateinit var pocketRequest: PocketRequest
  private lateinit var product: Product
  private lateinit var pocket: Pocket
  private lateinit var customer: Customer
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
    val productRepository = ProductRepositoryImpl(productApi)
    val profileRepo = ProfileRepositoryImpl(profileApi)
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
      productName = "TOLOL",
      productImage = "TEMPE",
      productPriceBuy = 100000,
      productPriceSell = 120000,
      productStatus = 1,
      updatedDate = "12 March 2021",
      createdDate = "10 March 2021",
      historyPrices = listOf()
    )
    pocketViewModels.start(activateCustomer)
    pocketViewModels.getPocketCustomerById(activatePocket)
    productViewModels.getProductList()
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
      productViewModels.productListLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("ProductApi", "Subscribe : ${it.data}")
            if (it.data != null) {
              product = it.data.last()
              priceBuyAmount.text = Utils.currencyFormatter(product.productPriceBuy)
              priceSellAmount.text = Utils.currencyFormatter(product.productPriceSell)
              val sharedPreferences = SharedPref(requireContext())
              sharedPreferences.save(Utils.PRODUCT_ID, product.id)
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
        val qty: Double = inputQty.text.toString().toDouble()

        val pocketRequest = PocketRequest(id = activatePocket)
        val purchaseDetailRequest =
          PurchaseDetailRequest(quantityInGram = qty, pocket = pocketRequest)
        val purchaseRequest = PurchaseRequest(
          purchaseType = purchaseType,
          purchaseDetails = listOf(purchaseDetailRequest)
        )
        when {
          customerPockets.isNotEmpty() -> {
            showDialog(title, message, purchaseRequest)
          }
          else -> {
            showOKDialog(requireContext(), "Failed Purchase", "Your Pocket is Not Selected or Not Exist!")
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
