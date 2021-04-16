package mx.itesm.Juanware.PolvorinApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_eventos.*
import kotlinx.android.synthetic.main.fragment_eventos.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [eventosFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class eventosFrag : Fragment() {


    private lateinit var baseDatos: FirebaseDatabase
    lateinit var arrEventos: MutableList<Evento>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("OnCreate")

        baseDatos = FirebaseDatabase.getInstance()
        arrEventos = mutableListOf()

        //grabarEnBD(1,  "Evento1")
        //grabarEnBD(2, "Evento2")
        //grabarEnBD(3, "Evento3")
        //grabarEnBD(4, "Evento4")

        leerDatos()
        configurarRV()
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos, container, false)
    }


    private  fun grabarEnBD(id: Int, nombreEvento: String){


        val event = Evento(id, nombreEvento)


        val referencia = baseDatos.getReference("/Evento/$id")
        referencia.setValue(event)
    }


    fun leerDatos(){

        val baseDatos = FirebaseDatabase.getInstance()
        val referencia = baseDatos.getReference("/Evento/")

        referencia.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrEventos.clear()
                for (registro in snapshot.children){
                    val idEvento = (registro.child("idEvento").value).toString().toInt()
                    val nombreEvento = (registro.child("nombreEvento").value).toString()

                    //println(idEvento)
                    //println(nombreEvento)

                    val evento = Evento(idEvento, nombreEvento)
                    //println(evento.toString())
                    arrEventos.add(evento)


                }
                println(arrEventos)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                println("ERROR EN BASE DE DATOS")
            }
        })
    }

    private fun configurarRV(){
        println("setup RB")
        val layoutManager = LinearLayoutManager(this.context)
        val  adaptador = AdaptadorTarjetaEventos(arrEventos)
        recyclerViewTarjetas.layoutManager = layoutManager
        recyclerViewTarjetas.adapter=adaptador
    }





}