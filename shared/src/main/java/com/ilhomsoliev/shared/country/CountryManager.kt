package com.ilhomsoliev.shared.country

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.ilhomsoliev.tgcore.TelegramClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.drinkless.tdlib.TdApi
import org.json.JSONObject
import org.koin.androidx.compose.get
import java.security.InvalidParameterException
import java.util.Locale

@Immutable
data class Country(
    val name: String,
    val code: String,
    val phoneDial: String,
    val phoneMask: String,
) {

    constructor() : this(
        "Россия",
        "TJ",
        "+992",
        "+7 (###) ###-##-##"
    )

    val flag: ImageBitmap
        @Composable get() {
            return getFlagByCountryCode(code = code)
        }
    val clearPhoneDial by lazy {
        phoneDial.replace(Regex("\\D"), "")
    }
}


val DemoCountry = Country(
    "Россия", "RU", "+7", "+7 (###) ###-##-##"
)

@Composable
fun getFlagByCountryCode(code: String): ImageBitmap {
    val manager = get<CountryManager>()
    return manager.flagForCountry(code)
}

class CountryManager(
    private val context: Context,
    private val tgClient: TelegramClient
) {

    val defaultCountry: Country by lazy {
        countryFrom(Locale.getDefault())
    }

    private fun countryFrom(locale: Locale) =
        masks[locale.country]?.let {
            Country(
                name = Locale("", locale.country).displayName,
                code = locale.country,
                phoneDial = it.dial,
                phoneMask = it
            )
        } ?: Country()

    private fun countryFrom(code: String, mask: String) =
        Country(
            name = Locale("", code).displayName,
            code = code,
            phoneDial = mask.dial,
            phoneMask = mask
        )

    private val masks: Map<String, String> by lazy {
        val json = context.assets
            .open("country_phone_code_masks.json")
            .reader().use {
                JSONObject(it.readText())
            }

        mutableMapOf<String, String>().apply {
            json.keys().forEach {
                this[it] = json[it] as String
            }
        }
    }

    fun getCountries(): List<Country> {
        return masks.map {
            countryFrom(it.key, it.value)
        }
    }

    fun getCountriesFromTdLib() = callbackFlow {
        tgClient.baseClient.send(TdApi.GetCountries()) {
            when (it.constructor) {
                TdApi.Countries.CONSTRUCTOR -> {
                    trySend(it as TdApi.Countries).isSuccess
                }

                else -> {
                    error("")
                }
            }
        }
        awaitClose { }
    }

    fun getCountryFromCountryCode(countryCode: String) =
        getCountries().firstOrNull { it.code == countryCode }


    private val String.dial: String
        get() {
            val index = replace(" ", "")
                .indexOfAny(listOf("#", "-#", ")#", "(#"))
            return substring(0, index).let {
                if (it.contains("(") && !it.contains(")"))
                    "$it)"
                else
                    it
            }
        }

    companion object {

        const val FLAG_WIDTH = 32
        const val FLAG_HEIGHT = 22
    }

    private val flags = mutableMapOf<String, ImageBitmap>()

    fun flagForCountry(countryCode: String): ImageBitmap {

        flags[countryCode]?.let { return it }

        if (countryCode.length != 2) {
            throw InvalidParameterException("Country code is not valid. Supply a valid ISO two digit country code. Refer: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2")
        }
        val ch = countryCode.uppercase(Locale.getDefault()).toCharArray()
        val asciiIndex = 64
        val firstLetterPosition = ch[0].code - asciiIndex
        val secondLetterPosition = ch[1].code - asciiIndex
        val flagImage = getImageFromAssetsFile()
        val flagForCountry = Bitmap.createBitmap(
            /* source = */ flagImage,
            /* x = */ firstLetterPosition * FLAG_WIDTH,
            /* y = */ secondLetterPosition * FLAG_HEIGHT,
            /* width = */ FLAG_WIDTH,
            /* height = */ FLAG_HEIGHT
        )
        return flagForCountry.asImageBitmap()
            .also { flags[countryCode] = it }
    }

    private var flagImageBitmap: Bitmap? = null

    private fun getImageFromAssetsFile(): Bitmap {
        flagImageBitmap?.let { return it }
        val bitmap = context.assets.open("flags.png").use {
            BitmapFactory.decodeStream(it)
        }
        flagImageBitmap = bitmap
        return bitmap
    }
}