package com.tergeo.testcoordinatepoints.presentation.ui.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.tergeo.testcoordinatepoints.presentation.ui.base.BaseViewModel
import com.tergeo.domain.entity.model.Point
import com.tergeo.domain.usecase.GetPointsUseCase
import com.tergeo.testcoordinatepoints.R
import com.tergeo.testcoordinatepoints.presentation.ui.base.SideEffect
import com.tergeo.testcoordinatepoints.presentation.ui.views.chart.ChartPoint
import kotlinx.coroutines.launch

data class MainState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val points: List<Point> = emptyList(),
    val count: Int? = null
)

sealed interface MainEffect: SideEffect {

    class Next(val points: Array<ChartPoint>): MainEffect
}

class MainViewModel(
    app: Application,
    private val getPointsUseCase: GetPointsUseCase
): BaseViewModel<MainState>(MainState()) {

    private val countError = app.getString(R.string.error_count)

    fun changeCount(count: String?){
        val countInt = count?.toIntOrNull()

        if (countInt == null){
            setState { copy(error = countError) }
        } else{
            setState { copy(count = countInt, error = null) }
        }
    }

    fun getPoints(){
        val count = state.value.count

        if (count == null){
            setState { copy(error = countError) }
            return
        }

        viewModelScope.launch(exceptionHandler) {
            setState { copy(isLoading = true, error = null) }

            val result = getPointsUseCase.execute(count)
                .getOrThrow()
                .map { ChartPoint(x = it.x, y = it.y) }

            setState { copy(isLoading = false) }
            sendEffect(MainEffect.Next(result.toTypedArray()))
        }
    }

    override fun handleException(throwable: Throwable) {
        super.handleException(throwable)
        setState { copy(isLoading = false) }
    }
}