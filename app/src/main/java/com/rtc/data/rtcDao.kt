package com.rtc.data


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.rtc.model.rtcHome


class rtcDao {
    //Variable para registrar el correo del usuario autenticado
    private var codigoUsuario: String

    //Enlace hacia Firestore...
    private var firestore: FirebaseFirestore

    init {
        val usuario = Firebase.auth.currentUser?.email
        codigoUsuario = "$usuario"
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings =
            FirebaseFirestoreSettings.Builder().build()
    }

    fun savertc(rtc: rtcHome) {
        val documento: DocumentReference
        if (rtc.id.isEmpty()) {  //Es un documento nuevo... se debe crear...
            documento = firestore
                .collection("rtcApp")
                .document(codigoUsuario)
                .collection("misrtc")
                .document()
            rtc.id = documento.id
        } else { //El documento ya existe...
            documento = firestore
                .collection("rtcApp")
                .document(codigoUsuario)
                .collection("misrtc")
                .document(rtc.id)
        }
        documento.set(rtc)
            .addOnSuccessListener {
                Log.d("Firestores", "Documento almacenado")
            }
            .addOnCanceledListener {
                Log.d("Firestores", "Documento NO almacenado")
            }
    }

    fun getrtc(): MutableLiveData<List<rtcHome>> {
        val listaFinal = MutableLiveData<List<rtcHome>>()
        firestore
            .collection("rtcApp")
            .document(codigoUsuario)
            .collection("misrtc")
            .addSnapshotListener { instantanea, e ->  //Le toma una foto/recupera los rtc del usuario
                if (e != null) {
                    Log.d("Firestore", "Error recuperando datos")
                    return@addSnapshotListener
                }
                if (instantanea != null) {  //Hay datos en la recuperación
                    val lista = ArrayList<rtcHome>()
                    val rtcs = instantanea.documents
                    rtcs.forEach {
                        val rtc = it.toObject(rtcHome::class.java)
                        if (rtc != null) {  //Si se pudo convertir a un rtc
                            lista.add(rtc)
                        }
                    }
                    listaFinal.value = lista
                }
            }
        return listaFinal
    }

    fun deletertc(rtc: rtcHome) {
        if (rtc.id.isNotEmpty()) {  //Si el documento/rtc existe... lo podemos borrar
            firestore
                .collection("rtcApp")
                .document(codigoUsuario)
                .collection("misrtc")
                .document(rtc.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("Firestore","Documento eliminado")
                }
                .addOnCanceledListener {
                    Log.d("Firestore","Documento NO eliminado")
                }
        }
    }
}