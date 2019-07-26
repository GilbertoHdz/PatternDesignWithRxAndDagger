package dev.manitos.ghg.api

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

  @Provides
  @Singleton
  fun provideImageService(retrofit: Retrofit): ImageService {
    return retrofit.create(ImageService::class.java)
  }
}
