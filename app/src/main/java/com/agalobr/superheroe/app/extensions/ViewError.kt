package com.agalobr.superheroe.app.extensions

import com.agalobr.superheroe.databinding.ViewErrorBinding

fun ViewErrorBinding.errorInternet(){
    val internet = "ERROR DE RED"
    this.apply {
        imageError.loadUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqexFEYbtahxn-Rx4UOMzseAehcLZHGw5JldnGPDMhUddn2BteOvRYUazjVT2Qso1LOlA&usqp=CAU")
        textErrorMessage.text = internet
    }
}
fun ViewErrorBinding.errorDatabase(){
    val database = "ERROR EN LA BASE DE DATOS"
    this.apply {
        imageError.loadUrl("https://cdn4.iconfinder.com/data/icons/data-science-blue-red/60/028_-_Data_Error-512.png")
        textErrorMessage.text = database
    }
}
fun ViewErrorBinding.errorUnknown(){
    val unknown = "ERROR DESCONOCIDO"
    this.apply {
        imageError.loadUrl("https://ayudawp.com/wp-content/uploads/2012/03/aviso.png")
        textErrorMessage.text = unknown
    }
}