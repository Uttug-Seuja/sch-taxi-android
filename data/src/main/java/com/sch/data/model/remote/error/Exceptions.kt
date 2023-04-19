package com.sch.data.model.remote.error

import java.io.IOException

class InvalidAccessTokenException(e: Throwable?, val url: String? = null) : IOException(e)
class InvalidAccessTokenExpire(e: Throwable?, val url: String? = null) : IOException(e)
class ServerNotFoundException(e: Throwable?, val url: String? = null) : IOException(e)
class InternalServerErrorException(e: Throwable?, val url: String? = null) : IOException(e)
class BadRequestException(e: Throwable?, val url: String? = null) : IOException(e)
