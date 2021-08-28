package com.mkamilmistar.gold_market.di.feature.auth

import com.mkamilmistar.gold_market.data.repository.AuthRepository
import com.mkamilmistar.gold_market.di.annotation.AuthScope
import com.mkamilmistar.gold_market.di.data.DataComponent
import dagger.Component

@AuthScope
@Component(modules = [AuthRepoModule::class], dependencies = [DataComponent::class])
interface AuthComponent {
  fun provideAuthRepo(): AuthRepository
}
