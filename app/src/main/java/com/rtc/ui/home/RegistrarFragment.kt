package com.rtc.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.rtc.BuildConfig
import com.rtc.R
import com.rtc.databinding.FragmentHomeBinding
import com.rtc.databinding.FragmentRegistrarBinding
import com.rtc.model.rtcHome
import com.rtc.utiles.ImagenUtiles
import com.rtc.viewmodel.HomeViewModel
import com.rtc.utiles.OtrosUtiles
import java.io.File


class RegistrarFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentRegistrarBinding? = null
    private val binding get() = _binding!!

    private lateinit var imagenUtiles: ImagenUtiles
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentRegistrarBinding.inflate(inflater, container, false)

        binding.btAgregar.setOnClickListener{
            //Validar si al menos el lugar tiene un nombre...
            if (binding.etNombre.text.toString().isNotEmpty()) {
                //Aparece el dialogo de circulo ...
                binding.progressBar.visibility = ProgressBar.VISIBLE
                binding.msgMensaje.text = getString(R.string.msg_subiendo_registro)
                binding.msgMensaje.visibility = TextView.VISIBLE
                //subeImagen()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.msgFaltanDatos), Toast.LENGTH_SHORT
                ).show()
            }
            agregarServicios()
        }



        //Se recibe la imagen tomada por la camara y se para a refrescar en
        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                resultado -> if(resultado.resultCode == Activity.RESULT_OK){
            imagenUtiles.actualizaFoto()
            }
        }

        imagenUtiles = ImagenUtiles(requireContext(),
        binding.btPhoto,binding.imagen,tomarFotoActivity)


 return binding.root

}


private fun agregarServicios() {
val nombre = binding.etNombre.text.toString()
val apellido = binding.etApellidoPaterno.text.toString()
val edad = binding.etEdad.text.toString()
val telefono = binding.etTelefono.text.toString()
val servicios = binding.etServicios.text.toString()
val experiencia = binding.etExperiencia.text.toString()
val precio = binding.etPrecio.text.toString()
 if (validos(nombre,apellido,edad,telefono,servicios,experiencia,precio)){
     val rtc= rtcHome("",nombre,apellido,edad,telefono,servicios,experiencia,precio)
     homeViewModel.addrtc(rtc)
     Toast.makeText(
         requireContext(),
         getString(R.string.msgServiciosAgregado),
         Toast.LENGTH_LONG).show()
     findNavController().navigate(R.id.nav_slideshow)
 } else {
     Toast.makeText(
         requireContext(),
         getString(R.string.msgFaltanDatos),
         Toast.LENGTH_LONG).show()
 }
}


private fun validos(nombre: String, apellido: String, edad: String, telefono: String, servicios: String, experiencia: String, monto: String): Boolean {
 return !(nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty()
         || telefono.isEmpty() || servicios.isEmpty() || experiencia.isEmpty() || monto.isEmpty())
}


override fun onDestroyView() {
 super.onDestroyView()
 _binding = null
}
}