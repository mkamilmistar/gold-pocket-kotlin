package com.mkamilmistar.gold_market.di.feature.history

import com.mkamilmistar.gold_market.data.repository.HistoryRepository
import com.mkamilmistar.gold_market.di.annotation.HistoryScope
import com.mkamilmistar.gold_market.di.data.DataComponent
import dagger.Component

@HistoryScope
@Component(modules = [HistoryRepoModule::class], dependencies = [DataComponent::class])
interface HistoryComponent {
  fun provideHistoryRepo(): HistoryRepository
}
