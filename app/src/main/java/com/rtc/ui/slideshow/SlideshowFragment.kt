package com.rtc.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rtc.R
import com.rtc.adapter.RtcAdapter
import com.rtc.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        //Activamos el adapter para ver la info..
        val rtcAdapter = RtcAdapter()

        //Activamos el recyclerView (el reciclador)
        val reciclador = binding.reciclador

        //Se asocia el adapterLugar al reciclerView
        reciclador.adapter=rtcAdapter

        //usualmente SIEMPRE va esta línea
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        slideshowViewModel.getAllData.observe(
            viewLifecycleOwner,{
                  //  rtc -> rtcAdapter.setData(rtc)
            }
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}