package kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel

import kanti.paymentstest.data.model.common.ErrorMessage

sealed class SyncPaymentsUiState {

	data object Empty : SyncPaymentsUiState()

	data object Process: SyncPaymentsUiState()

	data object Success : SyncPaymentsUiState()

	data object NoConnection : SyncPaymentsUiState()

	data object IncorrectToken : SyncPaymentsUiState()

	class Error(
		val error: ErrorMessage
	) : SyncPaymentsUiState()

	class Fail(
		val message: String,
		val th: Throwable? = null
	) : SyncPaymentsUiState()

}
