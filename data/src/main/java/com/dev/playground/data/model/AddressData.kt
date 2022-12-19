package com.dev.playground.data.model

data class AddressData(
    val results: List<Information>,
) {
    data class Information(val region: Region) {
        data class Region(
            val area1: Area,
            val area2: Area,
            val area3: Area,
        ) {
            data class Area(val name: String)

            fun getFullRegion() = "${area1.name} ${area2.name} ${area3.name}"
        }
    }

}

fun AddressData.toAddressString(): String = results.joinToString {
    it.region.getFullRegion()
}