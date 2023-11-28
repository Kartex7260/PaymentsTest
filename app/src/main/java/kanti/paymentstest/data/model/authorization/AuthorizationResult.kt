package kanti.paymentstest.data.model.authorization

import kanti.paymentstest.data.model.common.ErrorMessage

sealed class AuthorizationResult {

	class Success(
		val loginToken: LoginToken
	) : AuthorizationResult()

	data object NoConnection : AuthorizationResult()

	data object IncorrectCredentials : AuthorizationResult()

	class Error(
		val errorMessage: ErrorMessage
	) : AuthorizationResult()

	class Fail(
		val message: String,
		val throwable: Throwable? = null
	) : AuthorizationResult()

}