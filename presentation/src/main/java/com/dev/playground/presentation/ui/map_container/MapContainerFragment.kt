package com.dev.playground.presentation.ui.map_container

import android.os.Bundle
import android.view.View
import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.databinding.FragmentMapContainerBinding
import com.dev.playground.presentation.ui.add.AddMemoryActivity
import com.dev.playground.presentation.ui.map_container.MapContainerContract.State.Success
import com.dev.playground.presentation.util.currentDate
import com.dev.playground.presentation.util.repeatOnLifecycleState
import com.dev.playground.presentation.util.startActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ted.gun0912.clustering.naver.TedNaverClustering

class MapContainerFragment :
    BaseFragment<FragmentMapContainerBinding>(R.layout.fragment_map_container),
    OnMapReadyCallback {

    private lateinit var map: NaverMap
    private var mapItem: TedNaverClustering<MapItem>? = null

    private val viewModel by viewModel<MapContainerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        initViews()
        initCollects()
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
    }
}