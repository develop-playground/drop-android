package com.dev.playground.presentation.ui.map_container

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.charlezz.pickle.util.ext.showToast
import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.databinding.FragmentMapContainerBinding
import com.dev.playground.presentation.ui.add.AddMemoryActivity
import com.dev.playground.presentation.ui.map_container.MapContainerContract.State.Success
import com.dev.playground.presentation.util.hasPermission
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ted.gun0912.clustering.naver.TedNaverClustering

class MapContainerFragment :
    BaseFragment<FragmentMapContainerBinding>(R.layout.fragment_map_container),
    OnMapReadyCallback {

    private lateinit var map: NaverMap
    private var mapItem: TedNaverClustering<MapItem>? = null

    private lateinit var locationSource: FusedLocationSource

    private val needPermission =
        arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)

    private val viewModel by viewModel<MapContainerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        initViews()
        initCollects()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.all { it == PERMISSION_GRANTED }) {
                map.locationTrackingMode = LocationTrackingMode.Follow
            } else {
                map.locationTrackingMode = LocationTrackingMode.None
                showToast(getString(R.string.map_please_grant_permissions))
                val intent = Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)
                startActivity(intent)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetch()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroyView() {
        binding.mapView.onDestroy()
        super.onDestroyView()
    }

    override fun onLowMemory() {
        binding.mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onMapReady(p0: NaverMap) {
        map = p0
        map.locationSource = locationSource

        with(map) {
            context?.let { c ->
                mapItem = TedNaverClustering.with<MapItem>(c, this)
                    .customMarker { clusterItem ->
                        Marker(clusterItem.position).apply {
                            icon = OverlayImage.fromView(MarkerView(c))
                        }
                    }
                    .customCluster { cluster ->
                        MarkerView(c).apply {
                            setMarkerPoint(cluster.size)
                        }
                    }
                    .minClusterSize(1)
                    .make()
            }
        }
    }

    private fun initViews() = with(binding) {
        viewModel.fetch()

        ivAddMemory.setOnClickListener {
            context?.startActivity<AddMemoryActivity> { }
        }

        ivFusedLocation.setOnClickListener {
            if (requireActivity().hasPermission(*needPermission)) {
                map.locationTrackingMode = LocationTrackingMode.Follow
            } else {
                requestPermissions(needPermission, LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                uiState.collect { state ->
                    when (state) {
                        is Success -> {
                            generateItems(state.itemList)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun generateItems(updateList: List<Memory>) {
        updateList.map {
            val temp =
                MapItem(LatLng(it.location.latitude, it.location.longitude), it.imageUrlList[0])
            mapItem?.addItem(temp)
        }
    }

    companion object {
        fun newInstance() = MapContainerFragment()

        val TAG: String = MapContainerFragment::class.java.simpleName
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}