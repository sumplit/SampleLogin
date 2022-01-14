package id.test.tada.base

import com.google.gson.annotations.SerializedName

data class BaseApiModel<T>(
    @SerializedName("articles") val data: T? = null,
    @SerializedName("totalResults") val message: String?,
    @SerializedName("status") val status: String?
)
