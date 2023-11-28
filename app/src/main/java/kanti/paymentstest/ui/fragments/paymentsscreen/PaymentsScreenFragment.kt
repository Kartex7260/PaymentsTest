package kanti.paymentstest.ui.fragments.paymentsscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kanti.paymentstest.R
import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.databinding.FragmentScreenPaymentsBinding
import kanti.paymentstest.ui.fragments.common.hideErrorCard
import kanti.paymentstest.ui.fragments.common.observe
import kanti.paymentstest.ui.fragments.common.setUpToolbar
import kanti.paymentstest.ui.fragments.common.showErrorCard
import kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel.PaymentsScreenViewModel
import kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel.PaymentsUiState
import kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel.SyncPaymentsUiState
import kanti.paymentstest.ui.fragments.paymentsscreen.viewmodel.TokenUiState
import kanti.paymentstest.ui.view.recycleradapters.PaymentsRecyclerAdapter

@AndroidEntryPoint
class PaymentsScreenFragment : Fragment() {

	private var _viewBinding: FragmentScreenPaymentsBinding? = null
	private val viewBinding: FragmentScreenPaymentsBinding
		get() = _viewBinding!!

	private val viewModel by viewModels<PaymentsScreenViewModel>()
	private var token: LoginToken? = null

	private val paymentsRecyclerAdapter = PaymentsRecyclerAdapter()

	private val logTag = javaClass.simpleName

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		if (_viewBinding == null) {
			_viewBinding = FragmentScreenPaymentsBinding
				.inflate(inflater, container, false)
			viewBinding.paymentsRecyclerView.adapter = PaymentsRecyclerAdapter()
		}
		return viewBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setUpToolbar(
			title = getString(R.string.payments),
			navIcon = R.drawable.baseline_logout_24
		) {
			viewModel.logout()
		}

		observe(viewModel.payments) { uiState ->
			when (uiState) {
				is PaymentsUiState.Success -> {
					paymentsRecyclerAdapter.payments = uiState.payments
					viewBinding.paymentsRecyclerView.adapter = paymentsRecyclerAdapter
				}
				is PaymentsUiState.Empty -> {}
			}
		}
		observe(viewModel.token) { uiState ->
			when (uiState) {
				is TokenUiState.Empty -> {}
				is TokenUiState.Success -> {
					this.token = uiState.token
					viewModel.syncPayments(uiState.token)
				}
				is TokenUiState.NotLoggedIn -> {
					val navDirections = PaymentsScreenFragmentDirections.actionPaymentsToLogin()
					findNavController().navigate(navDirections)
				}
				is TokenUiState.Fail -> {
					Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
				}
			}
		}
		observe(viewModel.syncPayments) { uiState ->
			viewBinding.paymentsProgressIndicator.visibility = View.GONE

			when (uiState) {
				is SyncPaymentsUiState.Empty -> {
					hideErrorCard()
				}
				is SyncPaymentsUiState.Success -> {
					hideErrorCard()
				}
				is SyncPaymentsUiState.IncorrectToken -> {
					hideErrorCard()
					val navDirections = PaymentsScreenFragmentDirections.actionPaymentsToLogin()
					findNavController().navigate(navDirections)
				}
				is SyncPaymentsUiState.NoConnection -> {
					showErrorCard(
						getString(R.string.no_connection),
						getString(R.string.refresh)
					) {
						token?.let {
							viewModel.syncPayments(it)
						}
					}
				}
				is SyncPaymentsUiState.Error -> {
					hideErrorCard()
					Log.e(logTag, uiState.error.toString())
					Toast.makeText(
						requireContext(),
						uiState.error.errorMessage,
						Toast.LENGTH_SHORT
					).show()
				}
				is SyncPaymentsUiState.Process -> {
					viewBinding.paymentsProgressIndicator.visibility = View.VISIBLE
				}
				is SyncPaymentsUiState.Fail -> {
					hideErrorCard()
					Log.e(logTag, uiState.message, uiState.th)
					Toast.makeText(
						requireContext(),
						uiState.message,
						Toast.LENGTH_SHORT
					).show()
				}
			}
		}
	}

}