package com.mkamilmistar.gold_market

import com.mkamilmistar.gold_market.di.data.DaggerDataComponent
import com.mkamilmistar.gold_market.di.data.DataComponent
import com.mkamilmistar.gold_market.di.feature.auth.AuthComponent
import com.mkamilmistar.gold_market.di.feature.auth.DaggerAuthComponent
import com.mkamilmistar.gold_market.di.feature.pocket.DaggerPocketComponent
import com.mkamilmistar.gold_market.di.feature.pocket.PocketComponent
import com.mkamilmistar.gold_market.di.feature.product.DaggerProductComponent
import com.mkamilmistar.gold_market.di.feature.product.ProductComponent
import com.mkamilmistar.gold_market.di.feature.profile.DaggerProfileComponent
import com.mkamilmistar.gold_market.di.feature.profile.ProfileComponent
import com.mkamilmistar.gold_market.di.feature.purchase.DaggerPurchaseComponent
import com.mkamilmistar.gold_market.di.feature.purchase.PurchaseComponent
import com.mkamilmistar.gold_market.di.app.DaggerAppComponent

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication: DaggerApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.builder()
      .authComponent(provideAuthComponent())
      .build()
  }

  private fun provideDataComponent(): DataComponent {
    return DaggerDataComponent.builder().application(this).build()
  }

  private fun provideAuthComponent(): AuthComponent {
    return DaggerAuthComponent.builder().dataComponent(provideDataComponent()).build()
  }

  private fun providePocketComponent(): PocketComponent {
    return DaggerPocketComponent.builder().dataComponent(provideDataComponent()).build()
  }

  private fun provideProductComponent(): ProductComponent {
    return DaggerProductComponent.builder().dataComponent(provideDataComponent()).build()
  }

  private fun provideProfileComponent(): ProfileComponent {
    return DaggerProfileComponent.builder().dataComponent(provideDataComponent()).build()
  }

  private fun providePurchaseComponent(): PurchaseComponent {
    return DaggerPurchaseComponent.builder().dataComponent(provideDataComponent()).build()
  }

}
