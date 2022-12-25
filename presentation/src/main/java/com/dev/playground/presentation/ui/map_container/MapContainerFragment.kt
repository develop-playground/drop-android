package com.dev.playground.presentation.ui.map_container

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.databinding.FragmentMapContainerBinding
import com.dev.playground.presentation.ui.add.AddMemoryActivity
import com.dev.playground.presentation.util.startActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.skydoves.balloon.textForm
import ted.gun0912.clustering.naver.TedNaverClustering

class MapContainerFragment :
    BaseFragment<FragmentMapContainerBinding>(R.layout.fragment_map_container),
    OnMapReadyCallback {

    private lateinit var map: NaverMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        initViews()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
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
                TedNaverClustering.with<MapItem>(c, this)
                    .customMarker { clusterItem ->
                        Marker(clusterItem.position).apply {
                            val markerView = layoutInflater.inflate(R.layout.item_marker, null)
                            icon = OverlayImage.fromView(markerView)
                        }
                    }
                    .customCluster { cluster ->
                        MarkerView(c).apply {
                            // TODO cluster size 만큼 item_marker TextView에 매핑
                        }
                    }
                    .items(generateItems(this))
                    .make()
            }
        }
    }

    private fun initViews() = with(binding) {
        ivAddMemory.setOnClickListener {
            context?.startActivity<AddMemoryActivity> { }
        }
    }

    private fun generateItems(map: NaverMap): ArrayList<MapItem> {
        // TODO 추억 리스트를 조회하여 각 추억 위경도를 받아 매핑시켜야 함
        return ArrayList<MapItem>().apply {
            repeat(50) {
                val bounds = map.contentBounds
                val temp = MapItem(
                    LatLng(
                        (bounds.northLatitude - bounds.southLatitude) * Math.random() + bounds.southLatitude,
                        (bounds.eastLongitude - bounds.westLongitude) * Math.random() + bounds.westLongitude
                    )
                )
                add(temp)
            }
        }
    }

    companion object {
        fun newInstance() = MapContainerFragment()

        val TAG: String = MapContainerFragment::class.java.simpleName
    }

}