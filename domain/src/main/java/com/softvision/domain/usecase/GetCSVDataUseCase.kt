/*
 * Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */
package com.softvision.domain.usecase

import com.softvision.domain.repository.FileRepository
import javax.inject.Inject

class GetCSVDataUseCase @Inject constructor(
    private val fileRepository: FileRepository
) : AsyncUseCase<Void?, String?>() {
    /**
     * Read the CSV data from the interval CSV file.
     */
    @Throws(Exception::class)
    override suspend fun execute(params: Void?): String? =
        fileRepository.getCsvData()
}
