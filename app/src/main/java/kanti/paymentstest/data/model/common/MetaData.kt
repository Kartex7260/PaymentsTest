package kanti.paymentstest.data.model.common

import com.google.gson.annotations.SerializedName

data class MetaData<Data>(
	val success: Boolean,
	val response: Data?,
	val error: ErrorMessage?
)
