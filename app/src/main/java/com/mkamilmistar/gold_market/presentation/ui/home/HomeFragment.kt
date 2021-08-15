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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.*
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.model.entity.Product
import com.mkamilmistar.gold_market.data.model.entity.Purchase
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
import java.time.LocalDateTime

class HomeFragment : Fragment() {

  private lateinit var binding: FragmentHomeBinding
  private lateinit var purchaseViewModels: PurchaseViewModel
  private lateinit var productViewModels: ProductViewModel
  private lateinit var profileViewModels: ProfileViewModel
  private lateinit var pocketViewModels: PocketViewModel
  private lateinit var activateCustomer: String
  private var activatePocket: String = "1"
  private lateinit var purchase: Purchase
  private lateinit var product: Product
  private lateinit var pocket: Pocket

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
    val purchaseRepo = PurchaseRepositoryImpl(db)
    val pocketRepo = PocketRepositoryImpl(db)
    val productRepository = ProductRepositoryImpl(db)
    val profileRepo = ProfileRepositoryImpl(db)
    productViewModels = ViewModelProvider(this, ProductViewModelFactory(productRepository)).get(ProductViewModel::class.java)
    purchaseViewModels = ViewModelProvider(this, PurchaseViewModelFactory(purchaseRepo)).get(PurchaseViewModel::class.java)
    profileViewModels = ViewModelProvider(this, ProfileViewModelFactory(profileRepo)).get(ProfileViewModel::class.java)
    pocketViewModels = ViewModelProvider(this, PocketViewModelFactory(pocketRepo)).get(PocketViewModel::class.java)
  }

  private fun initShared() {
    val sharedPreferences = SharedPref(requireContext())
    activateCustomer = sharedPreferences.retrived("ID").toString()
    sharedPreferences.retrived("POCKET_ID").toString().apply {
      try {
        if (!this.equals(null)) {
          activatePocket = this
        }
      } catch (e: Exception) {
        activatePocket = "1"
      }
    }
  }

  @SuppressLint("SetTextI18n")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initShared()
    product = Product(
      productId = 1, productName = "TOLOL", productImage = "TEMPE", productPriceBuy = 100000, productPriceSell = 120000,
      productStatus = 1, updatedDate = "12 Maret 2021", createdDate = "10 Maret 2021"
    )
    productViewModels.createProduct(product)
    productViewModels.updateProduct(productId = 1)
    pocketViewModels.getPocketWithCustomerIdAndPocketId(activateCustomer.toInt(), activatePocket.toInt())
    profileViewModels.getCustomerById(activateCustomer.toInt())
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
      val pocketObserver: Observer<EventResult<Pocket>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            Log.d("HomeFragment", "Success Get Pocket...")
            pocket = event.data
            val totalAmount = (pocket.pocketQty.toDouble() * product.productPriceSell.toDouble())
            pocketNameText.text = pocket.pocketName
            totalGramText.text = "${pocket.pocketQty} /gr"
            totalPriceText.text = Utils.currencyFormatter(totalAmount)
//            val pockets = PocketRepositoryImpl(AppDatabase.getDatabase(requireContext())).pocketDBImport
            totalPocketsText.text = "Your total pockets: null"
            hideProgressBar()
          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            hideProgressBar()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      val productObserver: Observer<EventResult<Product>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            Log.d("HomeFragment", "Success Get Product...")
            product = event.data
            hideProgressBar()
          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            hideProgressBar()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      val profileObserver: Observer<EventResult<Customer>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            Log.d("HomeFragment", "Success Get Customer...")
            val customer = event.data
            greetingHomeText.text = "Hi, ${customer.firstName} ${customer.lastName}"

            hideProgressBar()
          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            hideProgressBar()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      val purchaseObserver: Observer<EventResult<Boolean>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            val isSuccessPurchase = event.data
            hideProgressBar()
            if (isSuccessPurchase) {
              Toast.makeText(context, "Purchased Success", Toast.LENGTH_SHORT).show()
            }
          }
          is EventResult.Failed -> {
            val message = "Failed to Purchase"
            hideProgressBar()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      productViewModels.productLiveData.observe(viewLifecycleOwner, productObserver)
      profileViewModels.customerLivedata.observe(viewLifecycleOwner, profileObserver)
      pocketViewModels.pocketLiveData.observe(viewLifecycleOwner, pocketObserver)
      purchaseViewModels.isSuccess.observe(viewLifecycleOwner, purchaseObserver)
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
        val price = if (purchaseType == 0) {
          product.productPriceBuy * qty
        } else {
          product.productPriceSell * qty
        }
        purchase = Purchase(
          purchaseDate = formatDate(LocalDateTime.now().toString()),
          purchaseType = purchaseType, price = price.toInt(), qtyInGram = qty,
          customerPurchaseId = activateCustomer.toInt(), pocketPurchaseId = activatePocket.toLong())
        val updatePocket: Pocket = pocket.copy(
          pocketQty = + qty.toInt()
        )
        pocketViewModels.updatePocket(updatePocket, activateCustomer.toInt())
        showDialog(title, message, purchase)
      }
      .setNegativeButton("Cancel", null)
      .create()
    dialog.show()
  }

  private fun showDialog(title: String, message: String, purchase: Purchase) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
          purchaseViewModels.purchaseProduct(purchase)
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
