package kanti.paymentstest.data.model.authorization.datasource.local

import kanti.paymentstest.data.model.authorization.LoginToken

sealed class TokenLocalResult {

	class Success(val token: LoginToken) : TokenLocalResult()

	data object NotLoggedIn : TokenLocalResult()

}