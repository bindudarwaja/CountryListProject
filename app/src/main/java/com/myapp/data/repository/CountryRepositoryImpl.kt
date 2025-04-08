// Data layer - CountryRepositoryImpl.kt
package com.myapp.data.repository

import com.myapp.data.utils.Result
import com.myapp.data.service.APIService
import com.myapp.domain.model.Country
import com.myapp.domain.repository.CountryRepository

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of the [CountryRepository] interface.
 * Retrieves countries data from the API and handles error cases.
 */
class CountryRepositoryImpl @Inject constructor(private val apiService: APIService) :
    CountryRepository {

    /**
     * Fetches the list of countries from the API.
     * @return Result object containing either the list of countries on success or an error message on failure.
     */
    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            val response = apiService.getCountries()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error("HTTP Error: ${response.code()}")
            }
        } catch (e: IOException) {
            Result.Error("Network Error: ${e.message}")
        } catch (e: HttpException) {
            Result.Error("HTTP Error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Error: ${e.message}")
        }
    }
}
