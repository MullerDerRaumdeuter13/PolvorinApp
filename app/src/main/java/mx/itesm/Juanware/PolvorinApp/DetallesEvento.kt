package mx.itesm.Juanware.PolvorinApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detalles_evento.*
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.lang.StringBuilder

class DetallesEvento : AppCompatActivity() {
    val DATABASE = FirebaseDatabase.getInstance().reference.child("Eventos")
    val mAuth = FirebaseAuth.getInstance()
    lateinit var detallesEvento: Evento
    val usuario = mAuth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_evento)


        val indexEvento = intent.getIntExtra(
            "EVENTO", 1
        )
        detallesEvento = arrEventos.get(indexEvento)
        var eventoBody = StringBuilder()
        eventoBody.append("Descripcion: ${detallesEvento.descripcionEvento}\n" +
                "participantes: ${detallesEvento.participantes.size} / ${detallesEvento.maxParticipantes}\n" +
                "Creador del Evento: ${detallesEvento.nombreParticipantes.first()}")

        etNombreEvento.text =detallesEvento.nombreEvento
        etBody.text = eventoBody

    }

    fun entrarAEvennto(v: View){
        if(detallesEvento.participantes.size > 10){
            Toast.makeText(this, "El evento ya está lleno.", Toast.LENGTH_SHORT).show()
        }else if(detallesEvento.idCreadorEvento == usuario.uid){
            Toast.makeText(this, "No puedes unirte a tu propio evento", Toast.LENGTH_SHORT).show()
        }else{
            //Actualiza la lista para subir y sobreescribir la vieja
            detallesEvento.participantes.add(usuario.uid)
            detallesEvento.nombreParticipantes.add(usuario.displayName)

            DATABASE.child(detallesEvento.idEvento).setValue(detallesEvento)
            Toast.makeText(this, "¡¡Te has inscrito a el evento!!", Toast.LENGTH_LONG).show()
            finish()

        }

    }

    fun salirEvento(v: View){
        if(detallesEvento.idCreadorEvento == usuario.uid){
            Toast.makeText(this, "No te puedes salir de tu propio evento.", Toast.LENGTH_SHORT).show()

        }else if(detallesEvento.participantes.contains(usuario.uid) == false){
            Toast.makeText(this, "Todavia no estás inscrito.", Toast.LENGTH_SHORT).show()

        }else{
            detallesEvento.participantes.remove(usuario.uid)
            detallesEvento.nombreParticipantes.remove(usuario.displayName)

            DATABASE.child(detallesEvento.idEvento).setValue(detallesEvento)
            Toast.makeText(this, "Has salido del evento", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}