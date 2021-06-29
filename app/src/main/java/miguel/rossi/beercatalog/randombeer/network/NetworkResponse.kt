package miguel.rossi.beercatalog.randombeer.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import miguel.rossi.beercatalog.randombeer.domain.RandomBeerModel

@JsonClass(generateAdapter = true)
data class NetworkResponse(
    @Json(name = "name") val name: String?,
    @Json(name = "abv") val abv: Double?,
    @Json(name = "description") val description: String?,
    @Json(name = "brewers_tips") val brewersTips: String?
)

fun List<NetworkResponse>.toBeerModel(): RandomBeerModel {
    return if (isNullOrEmpty()) {
        RandomBeerModel(name = "", abv = 0.0, description = "", brewersTips = "")
    } else {
        RandomBeerModel(
            name = get(0).name ?: "",
            abv = get(0).abv ?: 0.0,
            description = get(0).description ?: "",
            brewersTips = get(0).brewersTips ?: ""
        )
    }
}
