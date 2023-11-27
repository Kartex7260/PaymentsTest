package kanti.paymentstest.data.model.authorization.datasource.remote

import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.common.ErrorMessage

sealed class AuthorizationRemoteResult {

	class Success(
		val loginToken: LoginToken
	) : AuthorizationRemoteResult()

	data object IncorrectCredentials : AuthorizationRemoteResult()

	class Error(errorMessage: ErrorMessage) : AuthorizationRemoteResult()

	class Fail(
		val message: String
	) : AuthorizationRemoteResult()

}