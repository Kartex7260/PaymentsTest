package kanti.paymentstest.data.model.authorization

sealed class TokenResult {

	class Success(val token: LoginToken) : TokenResult()

	data object NotLoggedIn : TokenResult()

}