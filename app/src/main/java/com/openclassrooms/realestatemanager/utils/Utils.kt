package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.wifi.WifiManager
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Philippe on 21/02/2018.
 */

object Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * 0.82).roundToInt()
    }

    fun convertEuroToDollar(dollars: Int): Int {
        return (dollars * 1.22).roundToInt()
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    fun getTodayDate(): String? {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        return dateFormat.format(Date())
    }


    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifi.isWifiEnabled
    }

    fun formattedPrice(value: Double): String? {
        val df = DecimalFormat("###,###,###")
        return df.format(value)
    }

    fun formattedAddress(address: String): String {
        val split = splitAddress(address)
        var sentence = ""
        split.forEach { i -> sentence = "$sentence \n $i" }
        return sentence
    }

    fun splitAddress(address: String): List<String> {
        return address.split(",")
    }

    fun getCoordinates(context: Context, locationName: String): LatLng {

        var latLng = LatLng(0.0, 0.0)

        try {
            val coder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = coder.getFromLocationName(locationName, 1)

            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                val lat: Double = address.latitude
                val lng: Double = address.longitude
                Log.d("PEACH", "geoLocate: $lat $lng")
                latLng = LatLng(lat, lng)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("PEACH", "Could not get address..!")
        }

        Log.d("PEACH", "getCoordinates: $latLng")

        return latLng
    }
}