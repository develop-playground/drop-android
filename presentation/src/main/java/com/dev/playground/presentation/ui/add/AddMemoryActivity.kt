package com.dev.playground.presentation.ui.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.MarginPageTransformer
import com.charlezz.pickle.Config
import com.charlezz.pickle.Pickle
import com.charlezz.pickle.data.entity.Media
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.base.SimpleBindingAdapter
import com.dev.playground.presentation.base.SimpleBindingViewHolder
import com.dev.playground.presentation.databinding.ActivityAddMemoryBinding
import com.dev.playground.presentation.model.PhotoUIModel
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteLoginPage
import com.dev.playground.presentation.ui.add.AddMemoryContract.AddMemoryState.Idle
import com.dev.playground.presentation.ui.add.AddMemoryContract.AddMemoryState.SelectedPhoto
import com.dev.playground.presentation.ui.add.AddMemoryContract.Effect
import com.dev.playground.presentation.ui.add.AddMemoryContract.Event.OnClickDrop
import com.dev.playground.presentation.ui.login.LoginActivity
import com.dev.playground.presentation.ui.main.MainActivity
import com.dev.playground.presentation.util.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddMemoryActivity : BaseActivity<ActivityAddMemoryBinding>(R.layout.activity_add_memory) {

    companion object {
        private const val REQUEST_CODE = 7777
        private const val PREFIX_PACKAGE_NAME = "package:"
    }

    private val needPermissionList = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            plus(Manifest.permission.ACCESS_MEDIA_LOCATION)
        }
    }

    private val viewModel by viewModel<AddMemoryViewModel>()

    private val photoAdapter = SimpleBindingAdapter<PhotoUIModel, SimpleBindingViewHolder<PhotoUIModel>>()

    private val launcher = Pickle.register(this, object : Pickle.Callback {
        override fun onResult(result: ArrayList<Media>) {
            onSelectedPhotoList(result)
        }
    })

    private fun onSelectedPhotoList(result: ArrayList<Media>) {
        val latLng = result.findLatLng(this)

        viewModel.selectPhotoList(
            result = result.mapNotNull { it.mapToFile(this) },
            latLong = latLng
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initCollects()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionsResult(requestCode, grantResults)
    }

    private fun initViews() = with(binding) {
        vm = viewModel

        vpAddMemoryPhotoList.apply {
            adapter = photoAdapter
            offscreenPageLimit = 1
            setPageTransformer(MarginPageTransformer(resources.getDimensionPixelSize(R.dimen.spacing_4)))
        }
        TabLayoutMediator(tlPhotoPager, vpAddMemoryPhotoList) { _, _ -> }.attach()
        tvClose.setOnClickListener {
            finish()
        }
        flAddMemory.setOnClickListener {
            if (hasPermission(*needPermissionList)) {
                launcher.launch(Config.getDefault())
            } else {
                requestPermission(permissions = needPermissionList, requestCode = REQUEST_CODE)
            }
        }
        btAddMemory.setOnClickListener {
            viewModel.setEvent(OnClickDrop)
        }
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                uiState.collect { state ->
                    when (state.addMemoryState) {
                        is SelectedPhoto -> photoAdapter.submitList(
                            viewModel.mapToUIModel(state.addMemoryState)
                        )
                        is Idle -> {
                            binding.tvAddMemoryLocation.text = ""
                            photoAdapter.submitList(emptyList())
                        }
                    }
                    if (state.isDropped) {
                        setResult(MainActivity.REFRESH_RESULT_CODE)
                        finish()
                    }
                }
            }
            launch {
                effect.collect {
                    when (it) {
                        is Effect -> showToast(it.message)
                        is RouteLoginPage -> {
                            if (it.force) {
                                showToast(R.string.please_re_log_in)
                            }
                            startActivity<LoginActivity> { }
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun handlePermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.all { it == PERMISSION_GRANTED }) {
                launcher.launch(Config.getDefault())
            } else {
                if (needPermissionList.any { !ActivityCompat.shouldShowRequestPermissionRationale(this, it) }) {
                    showToast(getString(R.string.add_memory_please_grant_permissions))
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("$PREFIX_PACKAGE_NAME$packageName"))
                    startActivity(intent)
                } else {
                    showToast(getString(R.string.add_memory_permission_denied))
                }
            }
        }
    }

}