package com.dev.playground.presentation.ui.map_container

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.dev.playground.presentation.base.BaseFragment
import com.dev.playground.presentation.databinding.FragmentMapContainerBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import ted.gun0912.clustering.naver.TedNaverClustering

class MapContainerFragment :
    BaseFragment<FragmentMapContainerBinding>(FragmentMapContainerBinding::inflate),
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
                            icon = MarkerIcons.RED
                        }
                    }
                    .customCluster {
                        TextView(c).apply {
                            setBackgroundColor(android.graphics.Color.GREEN)
                            setTextColor(android.graphics.Color.WHITE)
                            text = "${it.size}개"
                            setPadding(10, 10, 10, 10)
                        }
                    }
                    .items(generateItems(this))
                    .make()
            }
        }
    }

    private fun initViews() = with(binding) {

    }

    private fun generateItems(map: NaverMap): ArrayList<MapItem> {
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