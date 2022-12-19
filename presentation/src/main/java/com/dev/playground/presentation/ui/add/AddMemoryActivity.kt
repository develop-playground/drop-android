package com.dev.playground.presentation.ui.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
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
import com.dev.playground.presentation.ui.add.AddMemoryContract.AddMemoryState.Empty
import com.dev.playground.presentation.ui.add.AddMemoryContract.AddMemoryState.SelectedPhoto
import com.dev.playground.presentation.ui.add.AddMemoryContract.Effect.Dropped
import com.dev.playground.presentation.ui.add.AddMemoryContract.Effect.ShowErrorToast.*
import com.dev.playground.presentation.ui.add.AddMemoryContract.Event.OnClickDrop
import com.dev.playground.presentation.util.*
import com.google.android.material.tabs.TabLayoutMediator
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
        val latLng = result.firstOrNull()?.getLatLng(this)

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
            uiState.collect { state ->
                when (state.addMemoryState) {
                    is SelectedPhoto -> photoAdapter.submitList(
                        viewModel.mapToUIModel(state.addMemoryState)
                    )
                    is Empty -> {
                        binding.tvAddMemoryLocation.text = ""
                        photoAdapter.submitList(emptyList())
                    }
                }
            }
        }
        repeatOnLifecycleState(state = Lifecycle.State.RESUMED) {
            effect.collect {
                when (it) {
                    Dropped -> finish()
                    FailUpload -> showToast(getString(R.string.add_memory_fail_upload))
                    NotSelectPhoto -> showToast(getString(R.string.add_memory_not_select_photo))
                    EmptyLocation -> showToast(getString(R.string.add_memory_missing_locate_information))
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