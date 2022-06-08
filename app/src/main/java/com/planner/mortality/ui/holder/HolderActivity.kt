package com.planner.mortality.ui.holder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.planner.mortality.R
import com.planner.mortality.databinding.ActivityHolderBinding
import com.planner.mortality.ui.setup.FragmentSetup
import com.planner.mortality.ui.setup.FragmentSetup.Companion.FRAGMENT_SETUP_KEY
import com.planner.mortality.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HolderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val fragmentId = intent.extras?.getInt(KEY_HOLDER_ACTIVITY_FRAGMENT) ?: 0
        val fragment = fragmentFromId(fragmentId)
        if (fragment == null) {
            showToast("Feature Not Yet Implemented!")
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_holder_fragment, fragment)
                .commit()
        }
    }

    private fun fragmentFromId(id: Int): Fragment? {
        return when (id) {
            FRAGMENT_SETUP_KEY -> FragmentSetup()
            else -> {
                null
            }
        }
    }

    companion object {

        private const val KEY_HOLDER_ACTIVITY_FRAGMENT = "HOLDER_ACTIVITY_FRAGMENT_KEY"
        fun intent(context: Context, fragmentToHold: Int): Intent {
            val intent = Intent(context, HolderActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(KEY_HOLDER_ACTIVITY_FRAGMENT, fragmentToHold)
            intent.putExtras(bundle)
            return intent
        }
    }
}