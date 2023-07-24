package com.ebeid.wallpapersapp.domain.models

class SavedItemsState<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val isEmpty: Boolean? = false
) {
    enum class Status {
        LOADING, SUCCESS, FAIL
    }

    companion object {
        fun <T> loading(): SavedItemsState<T> {
            return SavedItemsState(Status.LOADING)
        }

        fun <T> success(data: T?, isEmpty: Boolean?): SavedItemsState<T> {
            return SavedItemsState(Status.SUCCESS, data, isEmpty = isEmpty)
        }

        fun <T> error(error: String): SavedItemsState<T> {
            return SavedItemsState(Status.FAIL, message = error)
        }
    }
}
