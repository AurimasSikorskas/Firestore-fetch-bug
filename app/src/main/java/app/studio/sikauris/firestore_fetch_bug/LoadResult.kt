package app.studio.sikauris.firestore_fetch_bug

sealed class LoadResult<out R> {

    data class Success<out T>(val data: T) : LoadResult<T>()
    data class Error(val exception: Exception) : LoadResult<Nothing>() {
        constructor(throwable: Throwable) : this(java.lang.Exception(throwable))
    }

    object Loading : LoadResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}
