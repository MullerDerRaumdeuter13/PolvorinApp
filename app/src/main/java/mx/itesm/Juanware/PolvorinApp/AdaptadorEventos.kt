package mx.itesm.Juanware.PolvorinApp

//Autor: Eduardo Muller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.renglon_evento.view.*

class AdaptadorEventos(private val arrEventos: MutableList<Evento>): RecyclerView.Adapter<AdaptadorEventos.VistaRenglonEventos>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaRenglonEventos {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.renglon_evento, parent, false)
        return VistaRenglonEventos(vista)
    }

    override fun getItemCount(): Int {
        return arrEventos.size
    }

    override fun onBindViewHolder(holder: VistaRenglonEventos, position: Int) {
        val evento = arrEventos[position]
        holder.set(evento)


    }


    class VistaRenglonEventos(val vistaRenglonEv: View): RecyclerView.ViewHolder(vistaRenglonEv) {
        fun set(evento: Evento){
            vistaRenglonEv.tituloEvento.text = "Evento"
            vistaRenglonEv.descripcionEvento.text = "Descripcio y/o Ubicacion del Evento"
        }
    }

}