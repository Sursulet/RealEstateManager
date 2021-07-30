package com.openclassrooms.realestatemanager.data.geocoder

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlusCode (
    @SerializedName("compound_code")
    @Expose
    var compoundCode: String,

    @SerializedName("global_code")
    @Expose
    var globalCode: String
)