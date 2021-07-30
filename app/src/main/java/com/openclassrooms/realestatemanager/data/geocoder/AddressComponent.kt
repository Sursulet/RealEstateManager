package com.openclassrooms.realestatemanager.data.geocoder

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddressComponent (
    @SerializedName("long_name")
    @Expose
    var longName: String,

    @SerializedName("short_name")
    @Expose
    var shortName: String,

    @SerializedName("types")
    @Expose
    var types: List<String>
)