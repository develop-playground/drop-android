package com.dev.playground.presentation.ui.map_container

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.charlezz.pickle.util.DeviceUtil.hasPermission
import com.charlezz.pickle.util.ext.showToast
import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.databinding.FragmentMapContainerBinding
import com.dev.playground.presentation.model.base.UiEvent.NavigationEvent.RequestRouteAdd
import com.dev.playground.presentation.ui.main.MainViewModel
import com.dev.playground.presentation.ui.map_container.MapContainerContract.State.Success
import com.dev.playground.presentation.util.hasPermission
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.requestPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ted.gun0912.clustering.clustering.Cluster
import ted.gun0912.clustering.naver.TedNaverClustering

class MapContainerFragment : BaseFragment<FragmentMapContainerBinding>(R.layout.fragment_map_container), OnMapReadyCallback {

    companion object {
        fun newInstance() = MapContainerFragment()

        val TAG: String = MapContainerFragment::class.java.simpleName

        private const val REQUEST_CODE = 1000
        private const val MIN_CLUSTER_SIZE = 2
    }

    private val needPermission = arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION)

    private lateinit var naverMap: NaverMap
    private var naverClustering: TedNaverClustering<DropClusterItem>? = null
    private lateinit var locationSource: FusedLocationSource

    private val viewModel by viewModel<MapContainerViewModel>()
    private val sharedViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, REQUEST_CODE)

        initViews()
        initCollects()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.all { it == PERMISSION_GRANTED }) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            } else {
                naverMap.locationTrackingMode = LocationTrackingMode.None
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
        naverMap = p0

        with(naverMap) {
            locationSource = this@MapContainerFragment.locationSource
            uiSettings.isZoomControlEnabled = false
            uiSettings.isCompassEnabled = false
        }
        moveCurrentLocation()
    }

    private fun initViews() = with(binding) {
        ivAddMemory.setOnClickListener {
            sharedViewModel.setEvent(RequestRouteAdd)
        }

        ivFusedLocation.setOnClickListener {
            moveCurrentLocation()
        }
    }

    private fun moveCurrentLocation() {
        activity?.let {
            if (it.hasPermission(*needPermission)) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            } else {
                it.requestPermission(
                    permissions = needPermission,
                    requestCode = REQUEST_CODE
                )
            }
        }
    }

    private fun initCollects() = with(viewModel) {
        repeatOnLifecycleState {
            launch {
                uiState.collect { state ->
                    when (state) {
                        is Success -> {
                            binding.tvDropPointCount.text = state.itemList.size.toString()
                            binding.mapView.getMapAsync {
                                makeClustering()
                                addMarkerList(state.itemList)
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun makeClustering() {
        context?.let { c ->
            naverClustering?.clearItems()
            naverClustering = TedNaverClustering.with<DropClusterItem>(c, naverMap)
                .customMarker {
                    Marker(it.position).apply {
                        bindMarker(it, this)
                    }
                }
                .customCluster { cluster -> cluster.items.firstOrNull()?.view ?: MarkerView(c) }
                .clusterAddedListener { cluster, tedMarker -> bindCluster(cluster, tedMarker.marker) }
                .markerAddedListener { item, tedMarker -> bindMarker(item, tedMarker.marker) }
                .minClusterSize(MIN_CLUSTER_SIZE)
                .make()
        }
    }

    private fun bindCluster(cluster: Cluster<DropClusterItem>, marker: Marker) {
        cluster.items.firstOrNull()?.bind(count = cluster.items.sumOf { it.count }) {
            marker.icon = OverlayImage.fromView(it)
        }
    }

    private fun bindMarker(item: DropClusterItem, marker: Marker) {
        item.bind(count = item.count) {
            marker.icon = OverlayImage.fromView(it)
        }
    }

    private fun addMarkerList(memoryList: List<Memory>) {
        context?.let { c ->
            val temp = memoryList
                .groupBy { it.location }
                .map {
                    with(it.value.first()) {
                        DropClusterItem(
                            position = LatLng(location.latitude, location.longitude),
                            imageUrl = imageUrlList.firstOrNull(),
                            count = it.value.size,
                            view = MarkerView(c)
                        )
                    }
                }
            naverClustering?.addItems(temp)
        }
    }

}