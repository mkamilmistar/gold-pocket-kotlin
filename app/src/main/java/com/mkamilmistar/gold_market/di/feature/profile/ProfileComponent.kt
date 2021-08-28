package com.mkamilmistar.gold_market.di.feature.profile

import com.mkamilmistar.gold_market.di.annotation.ProfileScope
import com.mkamilmistar.gold_market.di.data.DataComponent
import dagger.Component

@ProfileScope
@Component(modules = [ProfileRepoModule::class], dependencies = [DataComponent::class])
interface ProfileComponent
