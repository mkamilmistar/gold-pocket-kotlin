package com.mkamilmistar.gold_market.di.feature.pocket

import com.mkamilmistar.gold_market.di.annotation.PocketScope
import com.mkamilmistar.gold_market.di.data.DataComponent
import dagger.Component

@PocketScope
@Component(modules = [PocketRepoModule::class], dependencies = [DataComponent::class])
interface PocketComponent
