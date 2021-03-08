package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.net.wifi.WifiManager
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

    fun withLargeIntegers(value: Double): String? {
        val df = DecimalFormat("###,###,###")
        return df.format(value)
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
}