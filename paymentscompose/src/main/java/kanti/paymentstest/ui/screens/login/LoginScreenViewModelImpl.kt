package kanti.paymentstest.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.paymentstest.data.model.authorization.AuthorizationRepository
import kanti.paymentstest.data.model.authorization.AuthorizationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModelImpl @Inject constructor(
	private val authorizationRepository: AuthorizationRepository
) : ViewModel(), LoginScreenViewModel {

	private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
	override val loginUiState = _loginUiState.asStateFlow()

	override fun login(login: String, password: String) {
		viewModelScope.launch {
			_loginUiState.value = LoginUiState.Process
			val result = authorizationRepository.login(login, password)

			_loginUiState.value = when (result) {
				is AuthorizationResult.Success -> LoginUiState.Success
				is AuthorizationResult.NoConnection -> LoginUiState.NoConnection
				is AuthorizationResult.IncorrectCredentials -> LoginUiState.IncorrectCredentials
				is AuthorizationResult.Fail -> LoginUiState.Fail(result.message, result.throwable)
				is AuthorizationResult.Error -> LoginUiState.Error(result.errorMessage)
			}
		}
	}

}