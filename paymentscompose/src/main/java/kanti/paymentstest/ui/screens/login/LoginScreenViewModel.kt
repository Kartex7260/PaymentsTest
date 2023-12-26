package kanti.paymentstest.ui.screens.login

import kotlinx.coroutines.flow.StateFlow

interface LoginScreenViewModel {

	val loginUiState: StateFlow<LoginUiState>

	fun login(login: String, password: String)
}