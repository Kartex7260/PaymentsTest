package kanti.paymentstest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import kanti.paymentstest.databinding.ActivityMainBinding
import kanti.paymentstest.ui.fragments.common.ToolbarOwner

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarOwner {

	private lateinit var viewBinding: ActivityMainBinding
	override lateinit var toolbar: Toolbar

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(viewBinding.root)

		toolbar = viewBinding.toolbar
	}

}