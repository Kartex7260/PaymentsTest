package kanti.paymentstest.ui.fragments.loginscreen.viewmodel

import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.common.ErrorMessage

sealed class LoginUiState {

	data object Empty : LoginUiState()

	data object Process : LoginUiState()

	data object Success : LoginUiState()

	data object NoConnection : LoginUiState()

	data object IncorrectCredentials : LoginUiState()

	class Error(
		val errorMessage: ErrorMessage
	) : LoginUiState()

	class Fail(
		val message: String,
		val throwable: Throwable? = null
	) : LoginUiState()

}
