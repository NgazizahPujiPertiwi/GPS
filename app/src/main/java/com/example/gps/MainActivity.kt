package com.example.gps

import android.Manifest
import android.content.ComponentCallbacks
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncRequest
import android.content.pm.PackageManager
import android.location.LocationProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import  android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var providerClient: FusedLocationProviderClient
    lateinit var request: LocationRequest
    lateinit var callback: LocationCallback

    val REQUEST_CODE = 1000;

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(
                            this@MainActivity,
                            "Permition denied",
                            Toast.LENGTH_SHORT
                        ).show() else
                        Toast.makeText(
                            this@MainActivity,
                            "Permition denied",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Melakukan cek permision//
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        else {

            LocationRequest()
            buildcallBack()

            // Membuat FusedProviderClient//

            providerClient = LocationServices.getFusedLocationProviderClient(this)

            // Set Event

            start_update.setOnClickListener(View.OnClickListener {

                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                )
                    OnClickLister@
                    {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_CODE
                        )
                        return@OnClickLister
                    }
                providerClient.requestLocationUpdates(request, callback, Looper.myLooper())

                // Merubah Status Tombol,,

                start_update.isEnabled = !start_update.isEnabled
                stop_update.isEnabled = !stop_update.isEnabled
            });

            stop_update.setOnClickListener(View.OnClickListener {
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                )
                    OnClickLister@
                    {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_CODE
                        )
                        return@OnClickLister
                    }
                providerClient.removeLocationUpdates(callback)

                //merubah status tombol

                start_update.isEnabled = !start_update.isEnabled
                stop_update.isEnabled = !stop_update.isEnabled
            });
        }
    }

    private fun buildcallBack() {
        callback = object : LocationCallback() {

            //CTRL+O

            override fun onLocationResult(p0: LocationResult?) {
                var location = p0!!.locations.get(p0!!.locations.size - 1)

                text_location.text =
                    location.latitude.toString() + "/" + location.longitude.toString()
            }

        }
    }

    private fun buildrequest() {
        request = LocationRequest()
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        request.fastestInterval = 3000
        request.smallestDisplacement = 10f
    }
}