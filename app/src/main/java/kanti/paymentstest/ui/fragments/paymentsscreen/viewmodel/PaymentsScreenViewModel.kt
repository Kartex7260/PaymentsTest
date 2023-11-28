package kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.paymentstest.data.model.authorization.AuthorizationRepository
import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.authorization.TokenResult
import kanti.paymentstest.data.model.payments.PaymentsRepository
import kanti.paymentstest.data.model.payments.PaymentsResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsScreenViewModel @Inject constructor(
	private val paymentsRepository: PaymentsRepository,
	private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

	val payments: StateFlow<PaymentsUiState> = paymentsRepository.payments
		.map {
			PaymentsUiState.Success(it)
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.Lazily,
			initialValue = PaymentsUiState.Empty
		)

	val token: StateFlow<TokenUiState> = authorizationRepository.token
		.map {
			when (it) {
				is TokenResult.Success -> TokenUiState.Success(it.token)
				is TokenResult.NotLoggedIn -> TokenUiState.NotLoggedIn
				is TokenResult.Fail -> TokenUiState.Fail(it.message, it.th)
			}
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.Lazily,
			initialValue = TokenUiState.Empty
		)

	private val _syncPayments = MutableStateFlow<SyncPaymentsUiState>(SyncPaymentsUiState.Empty)
	val syncPayments: StateFlow<SyncPaymentsUiState> = _syncPayments.asStateFlow()

	fun logout() {
		viewModelScope.launch {
			authorizationRepository.logout()
		}
	}

	fun syncPayments(authToken: LoginToken) {
		viewModelScope.launch {
			_syncPayments.value = SyncPaymentsUiState.Process

			_syncPayments.value = when (
				val payments = paymentsRepository.getPayments(authToken.token)
			) {
				is PaymentsResult.Success -> {
					SyncPaymentsUiState.Success
				}
				is PaymentsResult.NoConnection -> {
					SyncPaymentsUiState.NoConnection
				}
				is PaymentsResult.IncorrectToken -> {
					SyncPaymentsUiState.IncorrectToken
				}
				is PaymentsResult.Error -> {
					SyncPaymentsUiState.Error(payments.error)
				}
				is PaymentsResult.Fail -> {
					SyncPaymentsUiState.Fail(payments.message, payments.th)
				}
			}
		}
	}

}