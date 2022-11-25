package com.example.hiltwithcompose

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

@RequiresApi(Build.VERSION_CODES.M)
class NetworkChecker(context: Context) :
    LiveData<NetworkStatus>() {
    @RequiresApi(Build.VERSION_CODES.M)
    val connectionManager:ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val valideNetworkConnections: ArrayList<Network> = ArrayList()
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        connectivityManagerCallback = netWorkCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectionManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectionManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    private fun netWorkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: android.net.Network) {
            super.onAvailable(network)
            val networkCapability = connectionManager.getNetworkCapabilities(network)
            val hasNetworkConnection =
                networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
            if (hasNetworkConnection) {
                // network connection available
                valideNetworkConnections.add(network)
                announceStatus()
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            valideNetworkConnections.remove(network)
            announceStatus()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                valideNetworkConnections.add(network)
            } else {
                valideNetworkConnections.remove(network)
            }
            announceStatus()
        }
    }

    fun announceStatus() {
        if (valideNetworkConnections.isNotEmpty()) {
            postValue(NetworkStatus.Available)
        } else {
            postValue(NetworkStatus.Unavailable)
        }
    }
}

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}