package com.mkamilmistar.gold_market.di.app


import com.mkamilmistar.gold_market.di.feature.auth.AuthModule
import com.mkamilmistar.gold_market.di.feature.history.HistoryModule
import com.mkamilmistar.gold_market.di.feature.pocket.PocketModule
import com.mkamilmistar.gold_market.di.feature.product.ProductModule
import com.mkamilmistar.gold_market.di.feature.profile.ProfileModule
import com.mkamilmistar.gold_market.di.feature.purchase.PurchaseModule
import dagger.Module

@Module(
  includes = [
    AuthModule::class,
    PocketModule::class,
    ProductModule::class,
    ProfileModule::class,
    PurchaseModule::class,
    HistoryModule::class
  ]
)
class FeatureModule
