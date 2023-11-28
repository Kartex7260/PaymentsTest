package kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel

import kanti.paymentstest.data.model.payments.Payment

sealed class PaymentsUiState {

	class Success(
		val payments: List<Payment>
	) : PaymentsUiState()

	data object Empty : PaymentsUiState()

}
