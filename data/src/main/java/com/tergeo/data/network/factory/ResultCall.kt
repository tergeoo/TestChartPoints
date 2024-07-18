package com.tergeo.data.network.factory

import com.tergeo.data.network.exception.BackendErrorException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NoContent

class ResultCall<T>(private val delegate: Call<T>) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        if (response.code() == 204) {
                            @Suppress("UNCHECKED_CAST")
                            val result = Result.success(NoContent as T)
                            callback.onResponse(
                                this@ResultCall,
                                Response.success(result)
                            )
                        } else {
                            val body = response.body()
                            if (body != null) {
                                val result = Result.success(body)
                                callback.onResponse(
                                    this@ResultCall,
                                    Response.success(result)
                                )
                            } else {
                                val throwable = Throwable("Response body is null")
                                callback.onResponse(
                                    this@ResultCall,
                                    Response.success(Result.failure(throwable))
                                )
                            }
                        }
                    } else {
                        val message = response.errorBody()?.string().orEmpty()
                        val throwable = BackendErrorException(message)
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(throwable))
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            Result.failure(t)
                        )
                    )
                }
            }
        )
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun execute(): Response<Result<T>> {
        return Response.success(Result.success(delegate.execute().body()!!))
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun clone(): Call<Result<T>> {
        return ResultCall(delegate.clone())
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}
