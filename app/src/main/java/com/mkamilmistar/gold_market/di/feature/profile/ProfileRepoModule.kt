package com.mkamilmistar.gold_market.di.feature.profile

import com.mkamilmistar.gold_market.data.remote.api.ProfileApi
import com.mkamilmistar.gold_market.data.repository.ProfileRepository
import com.mkamilmistar.gold_market.data.repository.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class ProfileRepoModule {
  @Binds
  abstract fun bindsProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

  companion object {
    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi {
      return retrofit.create(ProfileApi::class.java)
    }
  }
}
