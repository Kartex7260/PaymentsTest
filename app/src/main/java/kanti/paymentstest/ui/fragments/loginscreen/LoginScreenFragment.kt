package kanti.paymentstest.ui.fragments.loginscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kanti.paymentstest.databinding.FragmentScreenLoginBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginScreenFragment : Fragment() {

	private var _viewBinding: FragmentScreenLoginBinding? = null
	private val viewBinding: FragmentScreenLoginBinding
		get() = _viewBinding!!

	private lateinit var loginUiBehavior: LoginUiBehavior

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
	}

	override fun onResume() {
		super.onResume()
		loginUiBehavior.resetStates()
	}

	private fun login(login: String, password: String, callback: () -> Unit) {
		viewLifecycleOwner.lifecycleScope.launch {
			delay(500L)
			callback()
		}
	}

}