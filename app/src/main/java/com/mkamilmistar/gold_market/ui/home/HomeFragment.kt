package com.mkamilmistar.gold_market.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.model.Pocket
import com.mkamilmistar.gold_market.data.model.ProductHistory
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.data.repository.CustomerRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.PocketRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentHomeBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.utils.currencyFormatter
import com.mkamilmistar.gold_market.utils.formatDate
import java.time.LocalDateTime

class HomeFragment : Fragment() {

  private lateinit var binding: FragmentHomeBinding
  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().homeViewModel as T
    }
  }
  private val homeViewModel: HomeViewModel by viewModels { factory }
  private lateinit var prodHist: List<ProductHistory>
  private lateinit var purchaseBuy: Purchase
  private lateinit var purchaseSell: Purchase

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
    return binding.apply {
      lifecycleOwner = this@HomeFragment
      viewmodel = homeViewModel
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val passEmail = arguments?.getString("EMAIL")
    val passPwd = arguments?.getString("PASSWORD")
    subscribe()
    val dummyUser = CustomerRepositoryImpl().customerDBImport
    homeViewModel.start(dummyUser.email, dummyUser.password, 0)
    binding.apply {
      btnCreatePocket.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_homeFragment_to_pocketFragment)
      }

      btnChangePocket.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_homeFragment_to_pocketFragment)
      }

      btnBuyProduct.setOnClickListener {
        showDialog("Are sure want to buy this product?", "Click OK to Continue", purchaseBuy)
      }

      btnSellProduct.setOnClickListener {
        showDialog("Are sure want to sell this product?", "Click OK to Continue", purchaseSell)
      }
    }
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    binding.apply {
      val customerObserver: Observer<EventResult<Customer>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> Log.d("HomeFragment", "Loading...")
          is EventResult.Success -> {
            Log.d("HomeFragment", "Success Get Customer...")
            val customer = event.data
            greetingHomeText.text = "Hi, ${customer.firstName}"
          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      val pocketObserver: Observer<EventResult<Pocket>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> Log.d("HomeFragment", "Loading...")
          is EventResult.Success -> {
            Log.d("HomeFragment", "Success Get Pocket...")
            val pocket = event.data
            val totalAmount = (pocket.pocketQty.toDouble() * prodHist.last().priceSell.toDouble())

            pocketNameText.text = pocket.pocketName
            totalGramText.text = "${pocket.pocketQty} /gr"
            totalPriceText.text = currencyFormatter(totalAmount)
            val pockets = PocketRepositoryImpl().pocketDBImport
            totalPocketsText.text = "Your total pockets: ${pockets.size}"
          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      val productHistoryObserver: Observer<EventResult<List<ProductHistory>>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> Log.d("HomeFragment", "Loading...")
          is EventResult.Success -> {
            Log.d("HomeFragment", "Success Get Product History...")
            val productHistory = event.data
            prodHist = productHistory
            priceBuyAmount.text = currencyFormatter(productHistory.last().priceBuy)
            priceSellAmount.text = currencyFormatter(productHistory.last().priceSell)

            purchaseBuy =
              Purchase("PURCHASE-BUY", formatDate(LocalDateTime.now().toString()), 0, prodHist.last().priceBuy, 1.0)
            purchaseSell =
              Purchase("PURCHASE-SELL", formatDate(LocalDateTime.now().toString()), 1, prodHist.last().priceSell, 1.0)

          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      homeViewModel.customerLiveData.observe(viewLifecycleOwner, customerObserver)
      homeViewModel.productHistoryLiveData.observe(viewLifecycleOwner, productHistoryObserver)
      homeViewModel.pocketLiveData.observe(viewLifecycleOwner, pocketObserver)
    }
  }

  private fun showDialog(title: String, message: String, purchase: Purchase) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
          homeViewModel.purchaseProduct(purchase)
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
