package com.ebeid.wallpapersapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class PexelsModel(
    val id: Int,
    val width: Int,
    val height: Int,
    val primaryImageUrl: String,
    val viewImageUrl: String,
    val photographerName: String,
    val type: Int
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PexelsModel

        if (id != other.id) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (primaryImageUrl != other.primaryImageUrl) return false
        if (photographerName != other.photographerName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + primaryImageUrl.hashCode()
        result = 31 * result + photographerName.hashCode()
        return result
    }
}