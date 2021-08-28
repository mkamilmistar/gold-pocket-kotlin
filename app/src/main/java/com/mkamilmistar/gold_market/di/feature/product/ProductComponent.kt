package com.mkamilmistar.gold_market.di.feature.product

import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.data.repository.ProductRepository
import com.mkamilmistar.gold_market.di.annotation.ProductScope
import com.mkamilmistar.gold_market.di.data.DataComponent
import dagger.Component

@ProductScope
@Component(modules = [ProductRepoModule::class], dependencies = [DataComponent::class])
interface ProductComponent {
  fun provideProductRepo(): ProductRepository
}
