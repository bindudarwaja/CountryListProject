package com.myapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.domain.model.Country

@Composable
fun CountryListScreen(mainViewModel: MainViewModel) {
    val countries by mainViewModel.responseContainer.observeAsState(emptyList())
    val isLoading by mainViewModel.isShowProgress.observeAsState(false)
    val errorMessage by mainViewModel.errorMessage.observeAsState("")

    LaunchedEffect(true) {
        mainViewModel.getCountriesFromRepository()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            CountryList(countries)
        }
    }
}

@Composable
fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = Color.Red,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}

@Composable
fun CountryList(countries: List<Country>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(countries) { country ->
            CountryItem(country)
        }
    }
}

@Composable
fun CountryItem(country: Country) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Name, Region, and Code in one row (left-aligned for name/region, right-aligned for code)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${country.name}, ${country.region}",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = country.code ?: "Unknown",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Capital on the next line, left-aligned
        Text(
            text = country.capital ?: "Unknown",
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Divider (to separate countries)
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
