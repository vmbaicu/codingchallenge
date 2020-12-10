/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.data.repository

import android.content.Context
import com.softvision.data.R
import com.softvision.domain.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.lang.Exception
import java.nio.charset.Charset
import javax.inject.Inject


class FileRepositoryImpl @Inject constructor(private val context: Context) : FileRepository {

    @Throws(Exception::class)
    override suspend fun getCsvData(): String? {
        var csvData = ""

        withContext(Dispatchers.IO) {
            val input: InputStream = context.resources.openRawResource(R.raw.issues)
            val reader = BufferedReader(
                InputStreamReader(input, Charset.forName("UTF-8"))
            )

            reader.readLines().forEach {
                //get a string array of all items in this line
                val items = it.split(",")
                for (item: String in items) {
                    csvData += "$item "
                }
                csvData += "\n"
            }
        }

        return csvData
    }
}
