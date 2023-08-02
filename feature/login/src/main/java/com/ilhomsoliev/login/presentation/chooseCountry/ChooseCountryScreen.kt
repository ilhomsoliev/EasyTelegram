package com.ilhomsoliev.login.presentation.chooseCountry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.ilhomsoliev.login.viewmodel.ChooseCountryViewModel
import com.ilhomsoliev.shared.country.Country
import kotlinx.coroutines.launch

@Composable
fun ChooseCountryScreen(
    vm: ChooseCountryViewModel,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    val countries by vm.countries.collectAsState()
    val searchText by vm.query.collectAsState()

    var searchState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { vm.loadCountries() }

    ChooseCountryContent(
        state = ChooseCountryState(searchText, searchState, countries),
        callback = object : ChooseCountryCallback {
            override fun onCountrySelect(country: Country) {
                scope.launch {
                    vm.select(country)
                    navController.popBackStack()
                }
            }

            override fun onSearchTextChange(text: String) {
                scope.launch { vm.changeQuery(text) }
            }

            override fun onSearchStateChange() {
                scope.launch {
                    vm.changeQuery("")
                    searchState = !searchState
                }
            }

            override fun onBack() {
                navController.popBackStack()
            }
        })


}