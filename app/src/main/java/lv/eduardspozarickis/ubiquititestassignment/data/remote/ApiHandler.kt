package lv.eduardspozarickis.ubiquititestassignment.data.remote

import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiHandler {

    suspend fun <T> handleApi(
        execute: suspend () -> Response<T>
    ): ApiResult<T> {
        return try {
            val response = execute()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                ApiResult.Success(body)
            } else {
                ApiResult.Error.ServerError(
                    response.code(), response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> ApiResult.Error.NetworkError
                is ConnectException -> ApiResult.Error.NetworkError
                is SocketTimeoutException -> ApiResult.Error.TimeoutError
                else -> ApiResult.Error.UnknownError(e.message)
            }
        }
    }
}

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    sealed class Error : ApiResult<Nothing>() {
        data class ServerError(val code: Int, val errorMessage: String?) : Error()
        data class UnknownError(val errorMessage: String?) : Error()
        object NetworkError : Error()
        object TimeoutError : Error()
    }
}

