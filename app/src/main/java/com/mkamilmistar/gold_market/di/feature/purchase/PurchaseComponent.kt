package com.mkamilmistar.gold_market.di.feature.purchase

import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.di.annotation.PurchaseScope
import com.mkamilmistar.gold_market.di.data.DataComponent
import dagger.Component

@PurchaseScope
@Component(modules = [PurchaseRepoModule::class], dependencies = [DataComponent::class])
interface PurchaseComponent {
  fun providePurchaseRepo(): PurchaseRepository
}
