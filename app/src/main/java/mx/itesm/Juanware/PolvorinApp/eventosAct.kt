package mx.itesm.Juanware.PolvorinApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_eventos.*
import kotlinx.android.synthetic.main.fragment_eventos.*

class eventosAct : AppCompatActivity() {

    private lateinit var baseDatos: FirebaseDatabase
    lateinit var arrEventos: MutableList<Evento>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)


        baseDatos = FirebaseDatabase.getInstance()
        arrEventos = mutableListOf()

        //grabarEnBD(1,  "Evento1")
        //grabarEnBD(2, "Evento2")
        //grabarEnBD(3, "Evento3")
        //grabarEnBD(4, "Evento4")

        leerDatos()
        configurarRV()

    }




    private  fun grabarEnBD(id: Int, nombreEvento: String){


        val event = Evento(id, nombreEvento)


        val referencia = baseDatos.getReference("/Evento/$id")
        referencia.setValue(event)
    }


    fun leerDatos(){
        println("leyendo datos")
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
        val layoutManager = LinearLayoutManager(this)
        val  adaptador = AdaptadorTarjetaEventos(arrEventos)
        rvTarjetas.layoutManager = layoutManager
        rvTarjetas.adapter=adaptador
    }
}