package com.example.didactic_app

import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.GpsStatus
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity(), MapListener, GpsStatus.Listener {


    private val COORDENADAS_SERANTES: DoubleArray = doubleArrayOf(43.3359377879001, -3.0619840315480364)
    private val COORDENADAS_SARDINERA: DoubleArray = doubleArrayOf(43.33437796738136, -3.039328003051773)
    private val SARE_JOSLEEN_LEKUA: DoubleArray = doubleArrayOf(43.33005582802109, -3.0309998673703076)
    private val ITSAS_MUSEOA: DoubleArray = doubleArrayOf(43.33077682113878, -3.030527877109482)
    private val SANTURTZIKO_PARKEA: DoubleArray = doubleArrayOf(43.32879964909476, -3.031697605351168)
    private val ARRAUN_UDAL_PABILOIA: DoubleArray = doubleArrayOf(43.33072217111924, -3.03158330305195)
    private val UDALA: DoubleArray = doubleArrayOf(43.32883180778487, -3.033054932499828)


    lateinit var mMap: MapView
    lateinit var controller: IMapController;
    lateinit var mMyLocationOverlay: MyLocationNewOverlay;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding = ActivityMapBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_map)
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
//        mMap = binding.osmmap
        mMap = findViewById(R.id.osmmap)
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())


        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        controller = mMap.controller

        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            runOnUiThread {
                controller.setCenter(mMyLocationOverlay.myLocation);
                controller.animateTo(mMyLocationOverlay.myLocation)
            }
        }



        val overlayItemSerantes = OverlayItem("Serantes Mendia", "Serantes Mendia", GeoPoint(COORDENADAS_SERANTES[0], COORDENADAS_SERANTES[1]))
        val overlayItemSardinera = OverlayItem("La Sardinera", "La Sardinera Eskultura", GeoPoint(COORDENADAS_SARDINERA[0], COORDENADAS_SARDINERA[1]))
        val overlayItemSareJosle = OverlayItem("Sare Josleen Lekua", "Sare josleen lan lekua", GeoPoint(SARE_JOSLEEN_LEKUA[0], SARE_JOSLEEN_LEKUA[1]))
        val overlayItemMuseo = OverlayItem("Istas Museoa", "Santurtziko Itsas Museoa", GeoPoint(ITSAS_MUSEOA[0], ITSAS_MUSEOA[1]))
        val overlayItemParque = OverlayItem("Parkea", "Santurtziko Parkea", GeoPoint(SANTURTZIKO_PARKEA[0], SANTURTZIKO_PARKEA[1]))
        val overlayItemRemo = OverlayItem("Arraun Kluba", "Santurtziko arraun udal pabiloia", GeoPoint(ARRAUN_UDAL_PABILOIA[0], ARRAUN_UDAL_PABILOIA[1]))
        val overlayItemUdala = OverlayItem("Udaletxea", "Santurtziko Udaletxea", GeoPoint(UDALA[0], UDALA[1]))

        val listaPoi = listOf<OverlayItem>(
            overlayItemSerantes,
            overlayItemSardinera,
            overlayItemSareJosle,
            overlayItemMuseo,
            overlayItemParque,
            overlayItemRemo,
            overlayItemUdala
            )
        // Crea una capa de iconos para los puntos de interés
        val itemizedIconOverlay = ItemizedIconOverlay<OverlayItem>(
            applicationContext, // Context
            listaPoi, // Lista de puntos de interés
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    // Muestra la burbuja de información al tocar el marcador
                    showInfoWindow(item)
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            }
        )


        controller.setZoom(14.0)

        Log.e("TAG", "onCreate:in ${controller.zoomIn()}")
        Log.e("TAG", "onCreate: out  ${controller.zoomOut()}")

        // controller.animateTo(mapPoint)
        mMap.overlays.add(mMyLocationOverlay)
        mMap.overlays.add(itemizedIconOverlay)

        mMap.addMapListener(this)


    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.getMapCenter()?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.getMapCenter()?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }

    override fun onGpsStatusChanged(event: Int) {


        TODO("Not yet implemented")
    }


    private inner class CustomInfoWindow(
        layoutResId: Int,
        mapView: MapView,
        private val overlayItem: OverlayItem
    ) : InfoWindow(layoutResId, mapView) {

        override fun onOpen(item: Any?) {
            // Aquí puedes personalizar la apariencia de la burbuja de información si es necesario
        }

        override fun onClose() {
            // Aquí puedes realizar acciones al cerrar la burbuja de información si es necesario
        }
    }

    private fun showInfoWindow(overlayItem: OverlayItem?) {
        if (overlayItem != null) {
            val marker = Marker(mMap)
            marker.position = overlayItem.point as GeoPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = overlayItem.title
            mMap.overlays.add(marker)
            mMap.invalidate()

            // Muestra la InfoWindow
            val infoWindow = CustomInfoWindow(org.osmdroid.library.R.layout.bonuspack_bubble, mMap, overlayItem)
            mMap.controller.animateTo(overlayItem.point)
        }
    }

}