package com.tergeo.data.repository

import com.tergeo.data.network.api.Api
import com.tergeo.domain.entity.model.Point
import com.tergeo.domain.repository.PointsRepository

class PointsRepositoryImpl(
    private val api: Api
): PointsRepository {

    override suspend fun getPoints(count: Int): Result<List<Point>> =
        api.getPoints(count).map { it.points }
}