package com.tergeo.data.network.api

import com.tergeo.domain.entity.model.PointListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api/test/points")
    suspend fun getPoints(
        @Query("count") count: Int
    ): Result<PointListResponse>
}