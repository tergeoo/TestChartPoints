package com.tergeo.domain.usecase

import com.tergeo.domain.repository.PointsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPointsUseCase(
    private val repository: PointsRepository
) {

    suspend fun execute(count: Int) = withContext(Dispatchers.IO) {
        repository.getPoints(count)
    }
}