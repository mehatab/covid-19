package info.covid.data.utils

import java.io.IOException
import info.covid.data.models.Result
import kotlinx.coroutines.flow.flow
import retrofit2.Response

private suspend fun <T : Any> persistenceApiCall(
    fetchFromRemote: suspend () -> Response<T>,
    saveRemoteData: (suspend (T) -> Unit)?,
    fetchFromLocal: (suspend () -> Result<T>)?,
    errorMessage: String
) = flow {
    emit(Result.Loading(true))
    try {
        if (fetchFromLocal != null) {
            emit(fetchFromLocal())
            emit(Result.Loading(false))
        }
        val response = fetchFromRemote()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (saveRemoteData != null) {
                    saveRemoteData(body)
                } else {
                    emit(Result.Success(body))
                    emit(Result.Loading(false))
                }
            }
        } else
            emit(Result.Error(IOException("$errorMessage ${response.code()} ${response.message()}")))
    } catch (e: Exception) {
        emit(Result.Error(IOException("$errorMessage: ${e.localizedMessage}")))
    }
    if (fetchFromLocal != null) {
        emit(fetchFromLocal())
        emit(Result.Loading(false))
    }
}

suspend fun <T : Any> safeApiCall(
    fetchFromRemote: suspend () -> Response<T>,
    errorMessage: String
) = persistenceApiCall(
    fetchFromRemote,
    null,
    null,
    errorMessage
)

suspend fun <T : Any> safeApiCall(
    fetchFromRemote: suspend () -> Response<T>,
    saveRemoteData: (suspend (T) -> Unit),
    fetchFromLocal: (suspend () -> Result<T>),
    errorMessage: String
) = persistenceApiCall(
    fetchFromRemote,
    saveRemoteData,
    fetchFromLocal,
    errorMessage
)