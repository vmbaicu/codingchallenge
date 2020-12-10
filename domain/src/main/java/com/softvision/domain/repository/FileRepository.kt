package com.softvision.domain.repository

import java.lang.Exception

interface FileRepository {

    @Throws(Exception::class)
    suspend fun getCsvData(): String?
}
