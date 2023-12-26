package kanti.paymentstest.ui.screens.login

import androidx.compose.runtime.Stable
import kanti.paymentstest.data.model.common.ErrorMessage

@Stable
sealed class LoginUiState {

	data object Empty : LoginUiState()

	data object Process : LoginUiState()

	data object Success : LoginUiState()

	data object NoConnection : LoginUiState()

	data object IncorrectCredentials : LoginUiState()

	@Stable
	class Error(
		val errorMessage: ErrorMessage
	) : LoginUiState()

	@Stable
	class Fail(
		val message: String,
		val throwable: Throwable? = null
	) : LoginUiState()

}