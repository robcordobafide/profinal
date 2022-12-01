package com.rtc.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Color
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rtc.databinding.RtcFilaBinding
import com.rtc.model.rtcHome
import com.rtc.ui.slideshow.SlideshowFragmentDirections


class RtcAdapter: RecyclerView.Adapter<RtcAdapter.HomeViewHolder>() {

    //Se define la lista que se usa para mostrar la información en el recycler
    private var listaRTC = emptyList<rtcHome>()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        //Genera una "vista" de lo creado en lugar_fila.xml (una cajita)
        val itemBinding = RtcFilaBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        //Se "extrae" el dato de la lista.. para dibujarlo
        val rtc=listaRTC[position]

        //Efectivamente se dibuja la información...
        holder.bind(rtc)

    }

    override fun getItemCount(): Int {
        return listaRTC.size
    }

    fun setData(rtc: List<rtcHome>) {
        listaRTC = rtc
        notifyDataSetChanged()
    }


    //Esta clase interna se usa para "dibujar" la información de un registro
    //en una "caja" para cada fila de la lista/o tabla SQLite
    inner class HomeViewHolder(private val itemBinding: RtcFilaBinding):
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(rtc:rtcHome) {
            itemBinding.tvNombre.text = rtc.nombre
            itemBinding.tvServicios.text = rtc.servicios
            itemBinding.tvExperiencia.text = rtc.experiencia
            itemBinding.tvPrecio.text = rtc.precio
            //Mostrar imagen
           // Glide.with(itemBinding.root.context)
                //.load(rtc.rutaImagen)
                //.circleCrop()
                //.into(itemBinding.filaImagen)

            itemBinding.vistaFila.setOnClickListener {
                //Genero la acción de pasarse al update con el objeto ...
               val action = SlideshowFragmentDirections
                    .actionNavSlideshowToVisualizarFragment(rtc)
                //Efectivamente se pasa...
                itemView.findNavController().navigate(action)
            }
        }

    }
}