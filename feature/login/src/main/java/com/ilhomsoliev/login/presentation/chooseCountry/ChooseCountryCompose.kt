package com.ilhomsoliev.login.presentation.chooseCountry

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.shared.common.CustomSearchTextField
import com.ilhomsoliev.shared.country.getFlagByCountryCode
import org.drinkless.tdlib.TdApi

data class ChooseCountryState(
    val query: String,
    val searchState: Boolean,
    val countries: List<TdApi.CountryInfo>,
)

interface ChooseCountryCallback {
    fun onSearchTextChange(text: String)
    fun onSearchStateChange()
    fun onBack()
    fun onCountrySelect(country: TdApi.CountryInfo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCountryContent(
    state: ChooseCountryState,
    callback: ChooseCountryCallback,
) {

    val isSearchBarVisible = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    callback.onBack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
                AnimatedVisibility(
                    visible = !isSearchBarVisible.value,
                    modifier = Modifier,
                    enter = fadeIn(), exit = fadeOut()
                ) {
                    Text(text = "Выберите страну")
                }
                AnimatedVisibility(
                    modifier = Modifier.weight(1f),
                    visible = isSearchBarVisible.value,
                    enter = slideInHorizontally { it / 2 } + fadeIn(),
                    exit = slideOutHorizontally { it / 2 } + fadeOut()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CustomSearchTextField(
                            modifier = Modifier.weight(1f),
                            value = state.query,
                            onValue = {
                                callback.onSearchTextChange(it)
                            },
                            onCancelClick = {
                                isSearchBarVisible.value = false
                                callback.onSearchTextChange("")
                            })
                    }
                }

            }
            if (!isSearchBarVisible.value)
                IconButton(onClick = {
                    callback.onSearchStateChange()
                    isSearchBarVisible.value = true
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }

        }
    }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it), content = {
            itemsIndexed(
                state.countries
            ) { index, item ->
                if (index == 0 || state.countries[index].name[0] != state.countries[index - 1].name[0]) {
                    LetterIndicator(state.countries[index].name[0])
                }
                CountryItem(
                    modifier = Modifier.background(Color.White),
                    country = item,
                ) { callback.onCountrySelect(item) }
            }
        })
    }
}

@Composable
private fun LetterIndicator(letter: Char) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEFEFEF))
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            text = letter.toString().toUpperCase()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryItem(
    country: TdApi.CountryInfo,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit,
) {
    Card(
        onClick = onSelect,
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CountryLabel(
                flag = getFlagByCountryCode(country.countryCode ?: ""),
                name = country.name,
                modifier = Modifier.weight(1f)
            )
            CountryDial(country.callingCodes.getOrNull(0) ?: "None")
        }
    }
}

@Composable
private fun CountryLabel(
    flag: ImageBitmap,
    name: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = flag,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp, 14.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = name,
            modifier = Modifier
                .padding(start = 16.dp),
            style = MaterialTheme.typography.bodyMedium
                .copy(MaterialTheme.colorScheme.tertiary)
        )
    }
}

@Composable
private fun CountryDial(
    dial: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.width(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dial,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            style = MaterialTheme.typography.bodyMedium
                .copy(MaterialTheme.colorScheme.scrim)
        )
    }
}