package com.tergeo.domain.repository

import com.tergeo.domain.entity.model.Point

interface PointsRepository {

    suspend fun getPoints(count: Int): Result<List<Point>>
}