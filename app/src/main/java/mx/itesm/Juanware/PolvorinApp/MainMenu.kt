package mx.itesm.Juanware.PolvorinApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        configMenu()
    }


    private fun configureInitFrag(fragment_str : String) {
        when(fragment_str){
            "About"   -> putFragment(About())
            "Profile" -> putFragment(Profile())
            "Events"  -> putFragment(FragmentEventos())
            "Notif"   -> putFragment(FragNotificaciones())
            "Equipos" -> putFragment(FragEquipo(this))
            else      -> putFragment(Profile())
        }
    }

    private fun configMenu() {
        NavMenu.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.About -> {
                    configureInitFrag("About")
                }

                R.id.Profile -> {
                    println("Perfil")
                    configureInitFrag("Profile")
                }

                R.id.Events -> {
                    println("eventos")
                    configureInitFrag("Events")
                }
                R.id.Notifications -> {
                    println("Notificaciones")
                    configureInitFrag("Notif")
                }
                R.id.Teams -> {
                    println("Equipos")
                    configureInitFrag("Equipos")
                }
            }

            true

        }
    }

    private fun putFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment,frag)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

    }

    fun hacerLogOut(v: View){
        val regresarLogin = Intent(this, Login::class.java)
        startActivity(regresarLogin)
        AuthUI.getInstance().signOut(this)
        finish()
    }


}