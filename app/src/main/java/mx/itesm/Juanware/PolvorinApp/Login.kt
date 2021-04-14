package mx.itesm.Juanware.PolvorinApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //probarEscritura()


    }
/*    private fun probarEscritura() {
        // Write a message to the database
        // Write a message to the database
        println("PROBANDO ESCRITURA")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("/autor")
        myRef.setValue("Otra mamada, 2")



        println("TERMINANDO ESCRITURAasdfasdfasdf")
    }*/

}