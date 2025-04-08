package com.myapp.domain.usecase

import com.myapp.domain.model.Country
import com.myapp.domain.repository.CountryRepository
import com.myapp.data.utils.Result
import javax.inject.Inject

/**
 * Use case responsible for fetching the list of countries.
 */
class GetCountriesUseCase @Inject constructor(private val countryRepository: CountryRepository) {

    suspend fun execute(): Result<List<Country>> {
        return countryRepository.getCountries()
    }
}