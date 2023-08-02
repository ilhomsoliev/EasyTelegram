package com.ilhomsoliev.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.shared.country.Country
import com.ilhomsoliev.shared.country.CountryManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn

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

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries
        .combine(_country) { list, current -> // Изменение порядка
            val default = countryManager.defaultCountry
            (if (default == current) listOf(default) else listOf(default, current)) +
                    (list - default - current)
        }
        .combine(query.debounce(250)) { list, str -> // Поиск
            list.filter {
                it.name.contains(str, true)
                        || it.code.contains(str, true)
                        || it.phoneDial.contains(str, true)
                        || it.clearPhoneDial.contains(str, true)
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, _countries.value)

    suspend fun loadCountries() {
        _countries.emit(
            countryManager.getCountries()
        )
    }

    suspend fun select(country: Country) {
      //  loginVm.selectCountry(country) TODO
    }

}
