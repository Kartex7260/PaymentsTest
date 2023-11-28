package kanti.paymentstest.ui.fragments.loginscreen

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
import kanti.paymentstest.databinding.FragmentScreenLoginBinding
import kanti.paymentstest.ui.fragments.common.observe
import kanti.paymentstest.ui.fragments.common.setUpToolbar
import kanti.paymentstest.ui.fragments.loginscreen.viewmodel.LoginScreenViewModel
import kanti.paymentstest.ui.fragments.loginscreen.viewmodel.LoginUiState

@AndroidEntryPoint
class LoginScreenFragment : Fragment() {

	private var _viewBinding: FragmentScreenLoginBinding? = null
	private val viewBinding: FragmentScreenLoginBinding
		get() = _viewBinding!!

	private val viewModel by viewModels<LoginScreenViewModel>()

	private lateinit var loginUiBehavior: LoginUiBehavior
	private var loginCallback: LoginClickCallback? = null

	private val logTag = javaClass.simpleName

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		if (_viewBinding == null) {
			_viewBinding = FragmentScreenLoginBinding
				.inflate(inflater, container, false)
			loginUiBehavior = LoginUiBehavior(
				requireContext(),
				viewBinding.screenLoginEditTextLogin,
				viewBinding.screenLoginEditTextPassword,
				viewBinding.screenLoginButtonLogin,
				::login
			)
		}
		return viewBinding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setUpToolbar(
			title = getString(R.string.login_account)
		)

		observe(viewModel.loginUiState) { uiState ->
			viewBinding.screenLoginProgressIndicator.visibility = View.GONE

			when (uiState) {
				is LoginUiState.Empty -> {
					hideError()
					loginCallback?.onCallback(LoginClickCallback.CallbackType.Success)
				}
				is LoginUiState.Success -> {
					hideError()
					loginCallback?.onCallback(LoginClickCallback.CallbackType.Success)
					val navDirections = LoginScreenFragmentDirections.actionLoginToPayments()
					findNavController().navigate(navDirections)
				}
				is LoginUiState.Process -> {
					viewBinding.screenLoginProgressIndicator.visibility = View.VISIBLE
				}
				is LoginUiState.NoConnection -> {
					showError(getString(R.string.no_connection))
					loginCallback?.onCallback(LoginClickCallback.CallbackType.Success)
				}
				is LoginUiState.IncorrectCredentials -> {
					hideError()
					loginCallback?.onCallback(LoginClickCallback.CallbackType.IncorrectCredentials)
				}
				is LoginUiState.Error -> {
					hideError()
					loginCallback?.onCallback(LoginClickCallback.CallbackType.Success)
					Log.e(logTag, uiState.errorMessage.toString())
					Toast.makeText(
						requireContext(),
						uiState.errorMessage.errorMessage,
						Toast.LENGTH_SHORT
					).show()
				}
				is LoginUiState.Fail -> {
					hideError()
					loginCallback?.onCallback(LoginClickCallback.CallbackType.Success)
					Log.e(logTag, uiState.message, uiState.throwable)
					Toast.makeText(
						requireContext(),
						uiState.message,
						Toast.LENGTH_SHORT
					).show()
				}
			}
		}
	}

	override fun onResume() {
		super.onResume()
		loginUiBehavior.resetStates()
	}

	private fun login(login: String, password: String, callback: LoginClickCallback) {
		viewModel.login(login, password)
		loginCallback = callback
	}

	private fun showError(text: String) {
		viewBinding.screenLoginTextViewError.apply {
			visibility = View.VISIBLE
			this.text = text
		}
	}

	private fun hideError() {
		viewBinding.screenLoginTextViewError.apply {
			visibility = View.GONE
			text = null
		}
	}

}