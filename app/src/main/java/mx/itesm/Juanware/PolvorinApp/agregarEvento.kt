package mx.itesm.Juanware.PolvorinApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_agregar_evento.*

class agregarEvento : AppCompatActivity() {
    val DATABASE = FirebaseDatabase.getInstance().reference.child("Eventos")
    val mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_agregar_evento)
    }


    fun creaEvento(v: View){
        val usuario = mAuth.currentUser
        val key = DATABASE.push().key
        var nombreEvento = etNombre.text.toString()
        var descripcionEvento = etDescription.text.toString()
        var tipoEvento = etTipo.text.toString()
        var idCreadorEvento = usuario.uid
        var latEvento = 0.0//posicion.latitude.toDouble()
        var longEvento = 0.0//posicion.longitude.toDouble()
        var maxParticipantes = etMaxParticipantes.text.toString()

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

                DATABASE.child(key).setValue(Evento(nombreEvento,
                        descripcionEvento, tipoEvento, idCreadorEvento,
                        latEvento, longEvento, maxParticipantes.toInt(), temp
                ))
            }
        }
        Toast.makeText(this, "Evento creado correctamente.", Toast.LENGTH_SHORT).show()
        val intRegresar = Intent(this, MainMenu::class.java)
        startActivity(intRegresar)
        finish()

    }

    fun cancelar(v:View){
        val intRegresar = Intent(this, MainMenu::class.java)
        startActivity(intRegresar)
        finish()
    }

}