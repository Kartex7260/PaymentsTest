package kanti.paymentstest.data.model.common

data class MetaArray<Data>(
	val success: Boolean,
	val response: List<Data>?,
	val error: ErrorMessage?
)
