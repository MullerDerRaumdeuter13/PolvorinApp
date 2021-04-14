package mx.itesm.Juanware.PolvorinApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ParaPreguntones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_para_preguntones)

        generarPregunta()
    }

    private fun generarPregunta() {
        println("Para que es esta actividad?")
        generarRespuesta()
    }

    private fun generarRespuesta() {
        println("pa los preguntones")
        generarPregunta()
    }
}