package miguel.rossi.beercatalog.randombeer.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomBeerModel(
    val name: String,
    val abv: Double,
    val description: String,
    val brewersTips: String
) : Parcelable
