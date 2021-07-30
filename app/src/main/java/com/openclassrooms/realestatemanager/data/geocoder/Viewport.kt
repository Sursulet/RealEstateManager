package com.openclassrooms.realestatemanager.data.geocoder

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Viewport (
    @SerializedName("northeast")
    @Expose
    var northeast: Northeast,

    @SerializedName("southwest")
    @Expose
    var southwest: Southwest
)