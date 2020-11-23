package com.cmunaro.surfingspot.data

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val code: Int?=null, val message: String?=null, val exception: Throwable?=null) : Resource<T>()
    class Loading<T>: Resource<T>()
}
