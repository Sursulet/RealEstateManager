package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.net.wifi.WifiManager
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

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
        val date = LocalDate.now()
        val format = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(format)
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

                latLng = LatLng(lat, lng)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return latLng
    }

    fun calculatePeriod(number: Long, time: String): LocalDate {
        lateinit var period: LocalDate
        val date = LocalDate.now()

        when (time) {
            "days" -> period = date.minusDays(number)
            "weeks" -> period = date.minusWeeks(number)
            "month" -> period = date.minusMonths(number)
            "years" -> period = date.minusYears(number)
        }

        return period
    }

    fun getBitmapFromURL(src: String?): Bitmap {
        val url = URL(src)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        return BitmapFactory.decodeStream(input)
    }

    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1))
                * sin(deg2rad(lat2))
                + (cos(deg2rad(lat1))
                * cos(deg2rad(lat2))
                * cos(deg2rad(theta))))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}