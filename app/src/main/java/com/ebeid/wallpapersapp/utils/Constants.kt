package com.ebeid.wallpapersapp.utils
import com.ebeid.wallpapersapp.BuildConfig

object Constants {
    const val CLIENT_ID = BuildConfig.API_KEY
    const val BASE_URL = "https://api.pexels.com/"
    const val DATABASE_NAME = "pexels-database"
    const val DEFAULT_PICTURE_PAGE_SIZE = 15
    const val DEFAULT_VIDEO_PAGE_SIZE = 10
    const val VIDEO_ITEM = 1
    const val PICTURE_ITEM = 0
    const val PICTURE_ONLY = 0
    const val VIDEO_ONLY = 1
    const val NETHER_VIDEO_NOR_PICTURE = -1
}