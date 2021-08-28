package com.mkamilmistar.gold_market.di.app

import com.mkamilmistar.gold_market.BaseApplication
import com.mkamilmistar.gold_market.di.annotation.AppScope
import com.mkamilmistar.gold_market.di.feature.auth.AuthComponent
import com.mkamilmistar.gold_market.di.feature.history.HistoryComponent
import com.mkamilmistar.gold_market.di.feature.pocket.PocketComponent
import com.mkamilmistar.gold_market.di.feature.product.ProductComponent
import com.mkamilmistar.gold_market.di.feature.profile.ProfileComponent
import com.mkamilmistar.gold_market.di.feature.purchase.PurchaseComponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@AppScope
@Component(
  modules = [
    AndroidInjectionModule::class,
    AppModule::class],
  dependencies = [
    AuthComponent::class,
    PocketComponent::class,
    ProductComponent::class,
    ProfileComponent::class,
    PurchaseComponent::class,
    HistoryComponent::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {

}
