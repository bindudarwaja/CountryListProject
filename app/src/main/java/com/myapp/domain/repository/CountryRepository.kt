package com.myapp.domain.repository

import com.myapp.data.utils.Result
import com.myapp.domain.model.Country

/**
 * Repository interface for fetching countries data.
 */
interface CountryRepository {

    /**
     * Fetches the list of countries from the API.
     * @return Result object containing either the list of countries on success or an error message on failure.
     */
    suspend fun getCountries(): Result<List<Country>>
}
