package com.ilhomsoliev.login.presentation.chooseCountry

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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.shared.country.Country

data class ChooseCountryState(
    val text: String,
    val searchState: Boolean,
    val countries: List<Country>,
)

interface ChooseCountryCallback {
    fun onSearchTextChange(text: String)
    fun onSearchStateChange()
    fun onBack()
    fun onCountrySelect(country: Country)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCountryContent(
    state: ChooseCountryState,
    callback: ChooseCountryCallback,
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Выберите страну")
        }, navigationIcon = {
            IconButton(onClick = {
                callback.onBack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = {
                callback.onSearchStateChange()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        })
    }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it), content = {
            itemsIndexed(state.countries) { index, item ->
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
    country: Country,
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
                flag = country.flag,
                name = country.name,
                modifier = Modifier.weight(1f)
            )
            CountryDial(country.phoneDial)
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