package kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel

import kanti.paymentstest.data.model.authorization.LoginToken

sealed class TokenUiState {

	class Success(val token: LoginToken) : TokenUiState()

	data object NotLoggedIn : TokenUiState()

	data object Empty : TokenUiState()

	class Fail(
		val message: String,
		val th: Throwable? = null
	) : TokenUiState()

}