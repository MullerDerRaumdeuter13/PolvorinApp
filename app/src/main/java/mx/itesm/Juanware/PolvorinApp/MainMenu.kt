package mx.itesm.Juanware.PolvorinApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        configMenu()
    }

    private fun configureInitFrag() {
        val fragProfile = Profile()
        putFragment(fragProfile)
    }

    private fun configMenu() {
        NavMenu.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.About -> {
                    println("Acerca de")
                    val fragAbout = About()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.NavMenu, fragAbout)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.Profile -> {
                    println("Perfil")
                    val fragProfile = Profile()
                    putFragment(fragProfile)
                }

                R.id.Events -> {
                    println("eventos")
                    val fragEvents = Profile()
                    putFragment(fragEvents)
                }
                R.id.Notifications -> {
                    println("Notificaciones")
                    val fragNotif = FragNotificaciones()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.NavMenu, fragNotif)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.Teams -> {
                    println("Equipos")
                    val fragEquip = FragEquipo()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.NavMenu, fragEquip)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }

            true

        }
    }

    private fun putFragment(Fragment: Profile) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.NavMenu, Fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}