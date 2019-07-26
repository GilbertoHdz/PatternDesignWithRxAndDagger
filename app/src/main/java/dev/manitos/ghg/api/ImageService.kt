package dev.manitos.ghg.api

import dev.manitos.ghg.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface ImageService {

  /**
   * @EndPoint Get all public images
   * @Description Get a random image by search
   */
  @GET("images/search")
  fun getSearch(
    @Header("x-api-key") apiKey: String
  ): Single<List<SearchResponse>>
}