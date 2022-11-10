package com.rtc.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class rtcHome(
    var id: String,
    val nombre: String,
    val apellidoPaterno: String,
    val edad: String,
    val telefono: String,
    val servicios: String,
    val experiencia: String,
    val precio: String,
    val rutaImagen: String?
): Parcelable{
    constructor(): this("","","","","","","","","")
}
