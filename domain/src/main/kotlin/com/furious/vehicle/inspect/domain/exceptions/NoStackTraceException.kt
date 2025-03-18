package com.furious.vehicle.inspect.domain.exceptions

open class NoStackTraceException(message: String, cause: Throwable? = null) : RuntimeException(message, cause, true, false)