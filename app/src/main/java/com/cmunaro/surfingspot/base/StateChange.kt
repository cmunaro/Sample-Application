package com.cmunaro.surfingspot.base

import com.cmunaro.surfingspot.data.Resource.Error

interface StateChange {
    class NetworkError(
        val resource: Error<*>
    ) : StateChange
}
