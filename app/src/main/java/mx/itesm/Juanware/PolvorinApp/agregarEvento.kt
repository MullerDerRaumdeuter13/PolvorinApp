package mx.itesm.Juanware.PolvorinApp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_agregar_evento.*
import java.util.*
import kotlin.collections.ArrayList

class agregarEvento : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    val DATABASE = FirebaseDatabase.getInstance().reference.child("Eventos")
    val mAuth = FirebaseAuth.getInstance()

    // Variables para colocar fecha en el evento
    var dia = 0
    var mes = 0
    var anio = 0
    var hora = 0
    var minuto = 0
    // variables que seran llamadas en TextView
    var diaGuardado = 0
    var mesGuardado = 0
    var anioGuardado = 0
    var horaGuardado = 0
    var minutoGuardado = 0


    val fechaHora = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_agregar_evento)

        elegirFechaHoraOnClick()
    }


    fun creaEvento(v: View){
        val usuario = mAuth.currentUser
        val key = DATABASE.push().key
        var nombreEvento = etNombre.text.toString()
        var descripcionEvento = etDescription.text.toString()
        var tipoEvento = etTipo.text.toString()
        var idCreadorEvento = usuario.uid
        var latEvento = latitud
        var longEvento = longitude
        var maxParticipantes = etMaxParticipantes.text.toString()


        if(nombreEvento.isNullOrEmpty()){
            Toast.makeText(this, "Debes agregar un nombre al Evento.", Toast.LENGTH_SHORT).show()
        }else if(tipoEvento.isNullOrEmpty()){
            Toast.makeText(this, "El tipo de evento no puede estar vacio", Toast.LENGTH_SHORT).show()
        }else if(maxParticipantes.isNullOrEmpty() || maxParticipantes.toInt() <= 1){
            Toast.makeText(this, "Numero de integrantes no válido.", Toast.LENGTH_SHORT).show()
        }else if (fechaHora.isEmpty()){
            Toast.makeText(this, "Debes ingresar una fecha y hora del evento", Toast.LENGTH_SHORT).show()
        }else {
            if (key != null){
                val temp = ArrayList<String>()
                temp.add(idCreadorEvento)
                val nombreParticipantes = ArrayList<String>()
                nombreParticipantes.add(usuario.displayName)


                DATABASE.child(key).setValue(Evento(nombreEvento,
                        descripcionEvento, tipoEvento, idCreadorEvento,
                        latEvento, longEvento, maxParticipantes.toInt(), temp, key, nombreParticipantes, fechaHora
                ))

                Toast.makeText(this, "Evento creado correctamente.", Toast.LENGTH_SHORT).show()
                finish()

            }
        }


    }

    fun cancelar(v:View){

        finish()
    }

    private fun getDiaHoraCalendar(){
        val cal = Calendar.getInstance()
        dia = cal.get(Calendar.DAY_OF_MONTH)
        mes = cal.get(Calendar.MONTH)
        anio = cal.get(Calendar.YEAR)
        dia = cal.get(Calendar.HOUR)
        dia = cal.get(Calendar.MINUTE)
    }

    private fun elegirFechaHoraOnClick(){

        btnFechaHora.setOnClickListener {
            getDiaHoraCalendar()

            DatePickerDialog(this, this,anio, mes, dia).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        diaGuardado = dayOfMonth
        mesGuardado = month
        anioGuardado = year

        getDiaHoraCalendar()
        TimePickerDialog(this, this, hora, minuto, true).show()

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        horaGuardado = hour
        minutoGuardado = minute
        //Agrega un cero a la izquierda si el numero es menor a 10
        if (minutoGuardado < 10){
            tvFecha.text = "Fecha: $diaGuardado/$mesGuardado/$anioGuardado\nHora: $horaGuardado:0$minutoGuardado"
            //Agrega los valores a la variable de tipo FechaHora
            fechaHora.clear()
            fechaHora.add(diaGuardado)
            fechaHora.add(mesGuardado)
            fechaHora.add(anioGuardado)
            fechaHora.add(horaGuardado)
            fechaHora.add(minutoGuardado)

        }else {
            tvFecha.text = "Fecha: $diaGuardado/$mesGuardado/$anioGuardado\nHora: $horaGuardado:$minutoGuardado"
            //Agrega los valores a la variable de tipo FechaHora
            fechaHora.clear()
            fechaHora.add(diaGuardado)
            fechaHora.add(mesGuardado)
            fechaHora.add(anioGuardado)
            fechaHora.add(horaGuardado)
            fechaHora.add(minutoGuardado)
        }


    }

}