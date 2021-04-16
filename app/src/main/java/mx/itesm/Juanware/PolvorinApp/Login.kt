package mx.itesm.Juanware.PolvorinApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import mx.itesm.Juanware.PolvorinApp.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        probarEscritura()
        configurarBoton()

    }
    private fun probarEscritura() {
        // Write a message to the database
        // Write a message to the database
        println("PROBANDO ESCRITURA")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/autor")
        myRef.setValue("Oscar Zuniga Lara A01654827,  PRUEBA 2")



        println("TERMINANDO ESCRITURAasdfasdfasdf")

    }
    private fun configurarBoton(){
        println("Confugrando Boton")
        binding.buttonMainMenu.setOnClickListener{
            val initEvento = Intent(baseContext, eventosAct::class.java)
            startActivity(initEvento)
        }
    }

}