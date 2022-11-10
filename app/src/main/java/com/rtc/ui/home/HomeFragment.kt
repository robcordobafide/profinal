package com.rtc.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.rtc.R
import com.rtc.databinding.FragmentHomeBinding
import com.rtc.opcionAcceder
import com.rtc.viewmodel.HomeViewModel

class HomeFragment : Fragment() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mAuth = FirebaseAuth.getInstance()
        binding.btCerrarSesion.setOnClickListener { salir() }

        return root


    }

/*
        //Se obtiene el nombre del usuario y sus campos de nombre y correo , para mostrarlos en pantalla.

        val currentUser = mAuth.currentUser //obtenemos el usuario
        binding.nombreUsuario.text = currentUser?.displayName //obtenemos el nombre del user y lo desplegamos en la pantalla mediante el campo NombreUsuario
        binding.correoUsuario.text = currentUser?.email //obtenemos el correo del user y lo desplegamos en la pantalla mediante el campo correoUsuario

        mAuth = FirebaseAuth.getInstance()
        binding.btCerrarSesion.setOnClickListener { salir() }


    }*/

    private fun salir() {
        mAuth.signOut()
        val cerrar = Intent(this.requireContext(),opcionAcceder::class.java)
        startActivity(cerrar)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}