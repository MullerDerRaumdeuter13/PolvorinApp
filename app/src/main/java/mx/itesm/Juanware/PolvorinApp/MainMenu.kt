package mx.itesm.Juanware.PolvorinApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        configMenu()
    }

    private fun configMenu() {
        NavMenu.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.Profile -> {println("Perfil")}
                R.id.About -> (println("Acerca de"))
                R.id.Events -> {println("eventos")}
                R.id.Notifications -> (println("Notificaciones"))
                R.id.Teams -> (println("Equipos"))
            }

            true

        }
    }
}