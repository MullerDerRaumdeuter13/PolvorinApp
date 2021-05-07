package mx.itesm.Juanware.PolvorinApp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    lateinit var builder: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_evento)

        val indexEvento = intent.getIntExtra(
            "EVENTO", 1
        )
        detallesEvento = arrEventos.get(indexEvento)





        //Revisa si eres el creador del evento para darte oportunidad de borrar el evento

        if(detallesEvento.idCreadorEvento != usuario.uid){
            btnBorrar.visibility = View.INVISIBLE
        }
        if(detallesEvento.idCreadorEvento == usuario.uid){
            btndesinsc.visibility = View.INVISIBLE
            btninsc.visibility = View.INVISIBLE
        }


        var eventoBody = StringBuilder()
        eventoBody.append("Descripcion: ${detallesEvento.descripcionEvento}\n" +
                "participantes: ${detallesEvento.participantes.size} / ${detallesEvento.maxParticipantes}\n" +
                "Creador del Evento: ${detallesEvento.nombreParticipantes.first()}")

        etNombreEvento.text =detallesEvento.nombreEvento
        etBody.text = eventoBody

    }

    fun entrarAEvennto(v: View){
        if(detallesEvento.participantes.size >= detallesEvento.maxParticipantes){
            Toast.makeText(this, "El evento ya está lleno.", Toast.LENGTH_SHORT).show()
        }else if(detallesEvento.idCreadorEvento == usuario.uid){
            Toast.makeText(this, "No puedes unirte a tu propio evento", Toast.LENGTH_SHORT).show()
        }else if(detallesEvento.participantes.contains(usuario.uid)){
            Toast.makeText(this, "Ya estas inscrito", Toast.LENGTH_SHORT).show()
        }else{
            //Actualiza la lista para subir y sobreescribir la vieja
            detallesEvento.participantes.add(usuario.uid)
            detallesEvento.nombreParticipantes.add(usuario.displayName)

            DATABASE.child(detallesEvento.idEvento).setValue(detallesEvento)
            Toast.makeText(this, "¡¡Te has inscrito a el evento!!", Toast.LENGTH_LONG).show()
            finish()

        }

    }

    fun borrarEvento(v:View) {
        val builder = AlertDialog.Builder(this)
        //Crea una alerta para verificar que estas seguro que lo vas a borrar
        builder.setTitle("¿Estas seguro?")
        builder.setMessage("Esto borrará el evento y no hay vuelta atras.")
        //borras los datos
        builder.setPositiveButton("Si", { dialogInterface: DialogInterface, i: Int ->
            DATABASE.child(detallesEvento.idEvento).removeValue()
            Toast.makeText(this, "Se ha eliminado el evento", Toast.LENGTH_LONG).show()
            finish()
        })
        //Cancelas la acción
        builder.setNegativeButton("No", {dialogInterface: DialogInterface, i: Int -> })
        //despliega la alerta
        builder.show()

    }

    fun salirEvento(v: View) {
        if (detallesEvento.idCreadorEvento == usuario.uid) {
            Toast.makeText(this, "No te puedes salir de tu propio evento.", Toast.LENGTH_SHORT)
                .show()

        } else if (detallesEvento.participantes.contains(usuario.uid) == false) {
            Toast.makeText(this, "Todavia no estás inscrito.", Toast.LENGTH_SHORT).show()

        } else {
            detallesEvento.participantes.remove(usuario.uid)
            detallesEvento.nombreParticipantes.remove(usuario.displayName)

            DATABASE.child(detallesEvento.idEvento).setValue(detallesEvento)
            Toast.makeText(this, "Has salido del evento", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}