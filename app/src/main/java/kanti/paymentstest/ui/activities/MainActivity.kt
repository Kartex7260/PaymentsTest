package kanti.paymentstest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kanti.paymentstest.databinding.ActivityMainBinding
import kanti.paymentstest.ui.fragments.common.ErrorCardOwner
import kanti.paymentstest.ui.fragments.common.ToolbarOwner

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarOwner, ErrorCardOwner {

	private lateinit var viewBinding: ActivityMainBinding
	override lateinit var toolbar: Toolbar

	override lateinit var root: View
	override lateinit var title: MaterialTextView
	override lateinit var button: MaterialButton

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(viewBinding.root)

		toolbar = viewBinding.toolbar

		root = viewBinding.mainErrorCard
		title = viewBinding.mainErrorTitle
		button = viewBinding.mainErrorButton
	}

}