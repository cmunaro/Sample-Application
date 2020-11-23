package com.cmunaro.surfingspot.data.datasource.remote

import com.cmunaro.surfingspot.data.Resource
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

abstract class RemoteDataSource {
    protected suspend fun<EXT, INT> fetch(
        fetchFunction: (suspend () -> ApiResponse<EXT>),
        mapper: suspend (result: ApiResponse.Success<EXT>) -> INT
    ) = flow<Resource<INT>> {

        emit(Resource.Loading())

        fetchFunction()
            .suspendOnSuccess { emit(Resource.Success(mapper(this@suspendOnSuccess))) }
            .suspendOnError { emit(Resource.Error(raw.code, raw.message)) }
            .suspendOnException { emit(Resource.Error(exception = exception)) }
    }.retry(3) { throwable ->
        (throwable is Exception).also { if (it) delay(500) }
    }.flowOn(Dispatchers.IO)
}