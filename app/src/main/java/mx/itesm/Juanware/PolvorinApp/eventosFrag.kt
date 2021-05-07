package mx.itesm.Juanware.PolvorinApp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_eventos.*
import kotlinx.android.synthetic.main.fragment_eventos.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

lateinit var arrEventos: MutableList<Evento>
class eventosFrag : Fragment(), clickListenerEventos {


    private lateinit var baseDatos: FirebaseDatabase

    var rango = 3000000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        baseDatos = FirebaseDatabase.getInstance()
        arrEventos = mutableListOf()

        //grabarEnBD(1,  "Evento1")
        //grabarEnBD(2, "Evento2")
        //grabarEnBD(3, "Evento3")
        //grabarEnBD(4, "Evento4")

        leerDatos()

    }


    fun loadPreferences() {
        val pref = activity?.getSharedPreferences("rango", Context.MODE_PRIVATE)
        if (pref != null) {
             this.rango = (pref.getInt("DIST_KEY",10) * 1000).toDouble()
            println(rango.toString())
        }
    }



    fun leerDatos() {

        val baseDatos = FirebaseDatabase.getInstance()
        val referencia = baseDatos.getReference("/Eventos/")

        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrEventos.clear()
                for (registro in snapshot.children) {
                    val dist = FloatArray(3)

                    val latEvento = (registro.child("latEvento").value).toString().toDouble()
                    val longEvento = (registro.child("longEvento").value).toString().toDouble()
                    Location.distanceBetween(latitud, longitude,latEvento, longEvento, dist)

                    println("DISTANCIA" + dist[0].toString())

                    if (dist[0] <= rango) {

                        val nombreEvento = (registro.child("nombreEvento").value).toString()
                        val descripcionEvento =
                            (registro.child("descripcionEvento").value).toString()
                        val tipoEvento = (registro.child("tipoEvento").value).toString()
                        val idCreadorEvento = (registro.child("idCreadorEvento").value).toString()
                        //val latEvento = (registro.child("latEvento").value).toString().toDouble()
                        //val longEvento = (registro.child("longEvento").value).toString().toDouble()
                        val maxParticiantes =
                            (registro.child("maxParticipantes").value).toString().toInt()
                        val participantes = (registro.child("participantes").value)
                        val nombreParticipantes = (registro.child("nombreParticipantes").value)
                        val idEvento = (registro.child("idEvento").value).toString()

                        var participantesActuales =
                            registro.child("participantes").childrenCount.toInt()

                        println("participantes hasta ahora: $participantesActuales")
                        println(participantes)


                        //println(idEvento)
                        //println(nombreEvento)

                        val evento = Evento(
                            nombreEvento, descripcionEvento,
                            tipoEvento, idCreadorEvento, latEvento,
                            longEvento, maxParticiantes, participantes as ArrayList<String>,
                            idEvento, nombreParticipantes as ArrayList<String>
                        )
                        arrEventos.add(evento)
                    }


                }
//                println(arrEventos)
            }

            override fun onCancelled(error: DatabaseError) {
                println("ERROR EN BASE DE DATOS")
            }
        })
    }


    private fun configurarRV(rvTarjetas: RecyclerView){
        println("setup RB")
        val layoutManager = LinearLayoutManager(this.context)
        val  adaptador = AdaptadorEventos(arrEventos)
        rvTarjetas.layoutManager = layoutManager
        rvTarjetas.adapter=adaptador
        adaptador.listener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crearEventobutton.setOnClickListener{
            activity?.let {
                val intent = Intent(it, agregarEvento::class.java)
                it.startActivity(intent)
            }
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("ONCREATEVIEW")

        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_eventos, container, false)
        var rvTarjetas: RecyclerView = view.findViewById(R.id.recyclerViewTarjetas)


        loadPreferences()

        configurarRV(rvTarjetas)
        return view
    }

    override fun clicked(position: Int) {
        println("Click en un evento ${position}")
        activity?.let {
            val intent = Intent(it, DetallesEvento::class.java)
            intent.putExtra("EVENTO", position)
            it.startActivity(intent)
        }
    }
    override fun onStop(){
        super.onStop()
    }
}

