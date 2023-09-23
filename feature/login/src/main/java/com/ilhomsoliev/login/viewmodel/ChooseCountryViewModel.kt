package com.ilhomsoliev.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.shared.country.CountryManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import org.drinkless.tdlib.TdApi
import java.util.Locale

class ChooseCountryViewModel(
    private val countryManager: CountryManager
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    suspend fun changeQuery(query: String) {
        _query.emit(query)
    }

    private val _country = MutableStateFlow(countryManager.defaultCountry)
    val country = _country.asStateFlow()

    private val _countries = MutableStateFlow<List<TdApi.CountryInfo>>(emptyList())
    val countries = _countries
        .combine(_query.debounce(250)) { list, str -> // Поиск
            if (str.isEmpty()) list else list.filter {
                it.name.lowercase(Locale.ROOT).contains(
                    str.lowercase(Locale.ROOT)
                )
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, _countries.value)

    suspend fun loadCountries() {
        _countries.emit(
            countryManager.getCountriesFromTdLib().first().countries.toList()
        )
    }

    suspend fun select(country: TdApi.CountryInfo) {
        //  loginVm.selectCountry(country) TODO
    }

}
