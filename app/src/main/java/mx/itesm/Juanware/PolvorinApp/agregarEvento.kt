package mx.itesm.Juanware.PolvorinApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class agregarEvento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_evento)
    }

    class agregarEvento : AppCompatActivity(){
        val DATABASE = FirebaseDatabase.getInstance().reference.child("Eventos")

        fun creaEvento(v: View){
            val key = DATABASE.push().key
            var nombreEvento = "eventTest"//editText.text.toString()
            var descripcionEvento = "test descrpition" //editText.text.toString()
            var tipoEvento = "Test"//editText.text.toString()
            var idCreadorEvento = "5123890uqhfgeshgq39q2y8589123gg"//usuario.uid
            var latEvento = 0.0//posicion.latitude.toDouble()
            var longEvento = 0.0//posicion.longitude.toDouble()
            var maxParticipantes = "10"//editText.text.toInt()

            if(nombreEvento.isNullOrEmpty()){
                Toast.makeText(this, "Debes agregar un nombre al Evento.", Toast.LENGTH_SHORT).show()
            }else if(tipoEvento.isNullOrEmpty()){
                Toast.makeText(this, "El tipo de evento no puede estar vacio", Toast.LENGTH_SHORT).show()
            }else if(maxParticipantes.isNullOrEmpty() || maxParticipantes.toInt() < 1){
                Toast.makeText(this, "Numero de integrantes no válido.", Toast.LENGTH_SHORT).show()
            }else{
                if (key != null){
                    val temp = ArrayList<String>()
                    temp.add(idCreadorEvento)
                    val participantes = temp.toArray()

                    DATABASE.child(key).setValue(Evento(nombreEvento,
                            descripcionEvento, tipoEvento, idCreadorEvento,
                            latEvento, longEvento, maxParticipantes.toInt(), participantes as ArrayList<String>
                    ))
                }
            }

        }



    }
}