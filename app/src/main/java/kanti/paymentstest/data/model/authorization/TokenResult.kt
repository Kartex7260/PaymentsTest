package kanti.paymentstest.data.model.authorization

sealed class TokenResult {

	class Success(val token: LoginToken) : TokenResult()

	data object NotLoggedIn : TokenResult()

	class Fail(
		val message: String,
		val th: Throwable? = null
	) : TokenResult()

}