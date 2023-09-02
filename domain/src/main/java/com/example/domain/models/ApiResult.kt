package com.example.domain.models

sealed class ApiResult<out T> {
        object Loading : ApiResult<Nothing>()
        data class Success<out T>(val data: T) : ApiResult<T>()
        data class Error(val error: Throwable) : ApiResult<Nothing>() //TODO: We can return an error object which has response code and msg inside ApiResult.
    }

inline fun <T, R> ApiResult<T>.getResult(
    success: (ApiResult.Success<T>) -> R,
    error: (ApiResult.Error) -> R
): R = if (this is ApiResult.Success) success(this) else error(this as ApiResult.Error)

inline fun <T> ApiResult<T>.onSuccess(
    block: (T) -> Unit
): ApiResult<T> = if (this is ApiResult.Success) also { block(data) } else this

inline fun <T> ApiResult<T>.onError(
    block: (Throwable) -> Unit
): ApiResult<T> = if (this is ApiResult.Error) also { block(error) } else this