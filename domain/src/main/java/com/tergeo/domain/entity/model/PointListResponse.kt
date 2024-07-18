package com.tergeo.domain.entity.model


import com.google.gson.annotations.SerializedName

data class PointListResponse(
    @SerializedName("points")
    val points: List<Point>
)