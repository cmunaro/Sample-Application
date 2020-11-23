package com.cmunaro.surfingspot.data.datasource

import com.cmunaro.surfingspot.data.Resource
import kotlinx.coroutines.flow.*

abstract class DataSourceService {
    protected suspend fun<T> getFromDataSource(
        localFetch: (suspend () -> Flow<Resource<T>>),
        remoteFetch: (suspend () -> Flow<Resource<T>>),
        localInsert: (suspend (T) -> Unit),
        shouldWaitRemoteResult: Boolean
    ): Flow<Resource<T>> {
        val dataFromLocal = localFetch()
        val dataFromRemote = remoteFetch().map { remoteResource ->
            if (remoteResource is Resource.Success) {
                localInsert(remoteResource.data)
            }
            remoteResource
        }
        return dataFromLocal.combineTransform(dataFromRemote) { localResource, remoteResource ->
            when {
                remoteResource is Resource.Error -> emit(remoteResource)
                shouldWaitRemoteResult && remoteResource is Resource.Loading -> emit(Resource.Loading<T>())
                else -> emit(localResource)
            }
        }
    }
}