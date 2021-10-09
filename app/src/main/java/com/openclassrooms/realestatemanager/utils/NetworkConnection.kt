package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow

class NetworkConnection(
    context: Context
) : Flow<Boolean> {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @ExperimentalCoroutinesApi
    val networkStatus = callbackFlow {
        val networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

        object : ConnectivityManager.NetworkCallback() {

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(NetworkStatus.Unavailable)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(NetworkStatus.Available)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(NetworkStatus.Unavailable)
            }
        }

        val builder = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(builder, networkCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<Boolean>) {
        TODO("Not yet implemented")
    }

    sealed class NetworkStatus {
        object Available : NetworkStatus()
        object Unavailable : NetworkStatus()
    }
}