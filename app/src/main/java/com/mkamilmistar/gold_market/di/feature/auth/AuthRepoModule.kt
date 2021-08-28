package com.mkamilmistar.gold_market.di.feature.auth

import com.mkamilmistar.gold_market.data.remote.api.AuthApi
import com.mkamilmistar.gold_market.data.repository.AuthRepository
import com.mkamilmistar.gold_market.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class AuthRepoModule {
  @Binds
  abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

  companion object {
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
      return retrofit.create(AuthApi::class.java)
    }
  }
}
