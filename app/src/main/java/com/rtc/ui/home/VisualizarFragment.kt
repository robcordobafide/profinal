package com.rtc.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rtc.R
import com.rtc.databinding.FragmentHomeBinding
import com.rtc.databinding.FragmentVisualizarBinding
import com.rtc.model.rtcHome
import com.rtc.viewmodel.HomeViewModel


class VisualizarFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentVisualizarBinding? = null
    private val binding get() = _binding!!


    //le define el objeto para leer el argumento pasado al fragmento
    private val args by navArgs<VisualizarFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentVisualizarBinding.inflate(inflater, container, false)

        //actualizo la información de los controles visuales...
        binding.etNombreV.setText(args.rtcHome.nombre)
        binding.etApellidoV.setText(args.rtcHome.apellidoPaterno)
        binding.etEdadV.setText(args.rtcHome.edad)
        binding.etTelefonoV.setText(args.rtcHome.telefono)
        binding.etServiciosV.setText(args.rtcHome.servicios)
        binding.etExperienciaV.setText(args.rtcHome.experiencia)
        binding.etPrecioV.setText(args.rtcHome.precio)

        //Si hay ruta de imagen... se dibuja la imagen...
        if (args.rtcHome.rutaImagen?.isNotEmpty() == true) {
            Glide.with(requireContext())
                .load(args.rtcHome.rutaImagen)
                .fitCenter()
                .into(binding.image)
        }

        binding.btWhatsapp.setOnClickListener { enviarWhatsapp() }
        binding.btTelefono.setOnClickListener { llamar() }



        return binding.root

    }

    private fun enviarWhatsapp() {
        //Extraemos el telefono del código
        val telefono = binding.etTelefonoV.text.toString()
        if(telefono.isNotEmpty()) {  //si tiene algo... se envia el mensaje
            //se crea un intento para hacer una vista de un recurso
            val intent =Intent(Intent.ACTION_VIEW)
            //Se define que se hace una llamada y se coloca el el url con la info
            intent.data = Uri.parse("whatsapp://send?phone=506" +
                    "$telefono&text="+getString(R.string.msg_saludos))
            intent.setPackage("com.whatsapp")
            startActivity(intent)
        } else {  //No tiene un telefono...
            Toast.makeText(requireContext(),
                getString(R.string.msg_datos),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun llamar() {
        //Extraemos el telefono del código
        val telefono = binding.etTelefonoV.text.toString()
        if(telefono.isNotEmpty()) {  //si tiene algo... se llama
            //se crea un intento para hacer una llamada
            val intent = Intent(Intent.ACTION_CALL)
            //Se define que se hace una llamada y se coloca el número
            intent.data = Uri.parse("tel:$telefono")
            //Se piden los permisos
            if(requireActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
                requireActivity().requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),105)
            } else {
                requireActivity().startActivity(intent)
            }
        } else {  //No tiene un telefono...
            Toast.makeText(requireContext(),
                getString(R.string.msg_datos),
                Toast.LENGTH_SHORT
            ).show()
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