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
import kanti.paymentstest.databinding.FragmentScreenPaymentsBinding
import kanti.paymentstest.ui.fragments.common.observe
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
					viewModel.syncPayments(uiState.token.token)
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
				is SyncPaymentsUiState.Empty -> {}
				is SyncPaymentsUiState.Success -> {}
				is SyncPaymentsUiState.IncorrectToken -> {
					val navDirections = PaymentsScreenFragmentDirections.actionPaymentsToLogin()
					findNavController().navigate(navDirections)
				}
				is SyncPaymentsUiState.NoConnection -> {
				}
				is SyncPaymentsUiState.Error -> {
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