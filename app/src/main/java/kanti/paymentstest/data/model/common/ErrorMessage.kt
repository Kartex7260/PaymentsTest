package kanti.paymentstest.data.model.common

import com.google.gson.annotations.SerializedName

data class ErrorMessage(
	@SerializedName("error_code") val errorCode: Int,
	@SerializedName("error_msg") val errorMessage: String
)
