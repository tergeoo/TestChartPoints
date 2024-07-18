package com.tergeo.domain.entity.model


import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("x")
    val x: Float,
    @SerializedName("y")
    val y: Float
)