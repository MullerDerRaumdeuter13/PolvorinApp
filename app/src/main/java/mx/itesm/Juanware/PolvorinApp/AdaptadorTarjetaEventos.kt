package mx.itesm.Juanware.PolvorinApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.renglon_evento.view.*

class AdaptadorTarjetaEventos(private val arrDatos: MutableList<Evento>): RecyclerView.Adapter<AdaptadorTarjetaEventos.VistaRenglon>() {

    class VistaRenglon (val vistaRenglonTarjeta: View): RecyclerView.ViewHolder(vistaRenglonTarjeta) {
        fun set(tarjeta: Evento){
            println("setting Tarjeta")
            vistaRenglonTarjeta.nombreEvento.text = tarjeta.nombreEvento
            vistaRenglonTarjeta.idEvento.text = tarjeta.idEvento.toString()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VistaRenglon {
        println("arrdatos")
        println(arrDatos)
        val vista = LayoutInflater.from(parent.context)
                .inflate(R.layout.renglon_evento,parent,false)
        return VistaRenglon(vista)
    }

    override fun onBindViewHolder(holder:VistaRenglon, position: Int) {

        val tarjetaEventos = arrDatos[position]
        holder.set(tarjetaEventos)

    }

    override fun getItemCount(): Int {
        return arrDatos.size
    }


}