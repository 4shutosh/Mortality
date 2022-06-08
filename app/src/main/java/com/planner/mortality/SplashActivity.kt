package com.planner.mortality

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.planner.mortality.ui.MortalityMainActivity
import com.planner.mortality.ui.holder.HolderActivity
import com.planner.mortality.ui.setup.FragmentSetup
import dagger.hilt.android.AndroidEntryPoint

// custom splash screen for the splash screen api
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.onBoardingViewState.value?.isReady == true) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            })
    }

    private fun setUpObservers() {
        val content: View = findViewById(android.R.id.content)
        viewModel.onBoardingViewState.observe(this) { viewState ->
            if (viewState.isReady) {
                if (viewState.isUserSetupDone) {
                    val mainActivityIntent = MortalityMainActivity.intent(this, true)
                    mainActivityIntent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(mainActivityIntent)
                } else {
                    val setupScreenIntent =
                        HolderActivity.intent(this, FragmentSetup.FRAGMENT_SETUP_KEY)
                    setupScreenIntent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(setupScreenIntent)
                }

            }
        }
    }
}