package kanti.paymentstest.ui.fragments.paymentsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kanti.paymentstest.databinding.FragmentScreenPaymentsBinding
import kanti.paymentstest.ui.view.recycleradapters.PaymentsRecyclerAdapter

@AndroidEntryPoint
class PaymentsScreenFragment : Fragment() {

	private var _viewBinding: FragmentScreenPaymentsBinding? = null
	private val viewBinding: FragmentScreenPaymentsBinding
		get() = _viewBinding!!

	private val paymentsRecyclerAdapter = PaymentsRecyclerAdapter()

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

}