package mx.itesm.Juanware.PolvorinApp

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_menu.*
import com.squareup.picasso.Picasso

var latitud = 0.0
var longitude = 0.0

class MainMenu : AppCompatActivity(), GPSListener {
    private val CODIGO_PERMISO_GPS: Int = 100
    private var gps: GPS? = null
    lateinit var actual:String

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        configureInitFrag()
        configMenu()

        mAuth = FirebaseAuth.getInstance()
        val usuario = mAuth.currentUser
        tvNombre.text = usuario.displayName
        Picasso.get().load(usuario.photoUrl).into(ivUsuario)

    }

    override fun onStart() {
        super.onStart()
        configurarGPS()

    }


    private fun configureInitFrag() {
        val home = eventosFrag()
        putFragment(home)
        actual = "Eventos"

    }

    private fun configMenu() {
        NavMenu.itemIconTintList = null
        NavMenu.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.About -> {
                    if(actual != "About"){
                    val cambio = aboutFrag()
                    putFragment(cambio)
                    actual = "About"
                    }
                }


                R.id.Events -> {
                    if(actual != "Eventos"){
                    val cambio = eventosFrag()
                    putFragment(cambio)
                    actual = "Eventos"
                    }

                }

                R.id.Settings -> {
                    if(actual != "Settings"){
                        val cambio = settingsFrag()
                        putFragment(cambio)
                        actual = "Settings"
                    }
                }
            }

            true

        }
    }

    private fun putFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fraglayout,frag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }


    fun hacerLogOut(v: View){
        val usuario = mAuth.currentUser
        AuthUI.getInstance().signOut(this)
        Handler().postDelayed(
                {
                    regresarLogin()
                }, 500
        )

    }

    fun regresarLogin(){
        val regresarLogin= Intent(this, Login::class.java)
        startActivity(regresarLogin)
        finish()
        println("Salio")
    }



    private fun configurarGPS() {
        gps = GPS()
        gps?.gpsListener = this
        gps?.inicializar(this)
        if (verificarPermisos()) {
            iniciarActualizacionesPosicion()
        } else {
            pedirPermisos()
        }
    }

    private fun iniciarActualizacionesPosicion() {
        gps?.iniciarActualizaciones()
    }

    @Override private fun verificarPermisos(): Boolean {
        val estadoPermiso = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        return estadoPermiso == PackageManager.PERMISSION_GRANTED
    }

    private fun pedirPermisos() {
        val requiereJustificacion = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
        if (requiereJustificacion) {
            mostrarDialogo()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODIGO_PERMISO_GPS)
        }
    }

    private fun mostrarDialogo() {
        val dialogo = AlertDialog.Builder(this)
        dialogo.setMessage("Necesitas GPS para esta app.")
            .setPositiveButton("Aceptar") { dialog, which ->
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODIGO_PERMISO_GPS)
            }
            .setNeutralButton("Cancelar", null)
        dialogo.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODIGO_PERMISO_GPS) {
            if (grantResults.isEmpty()) {
            } else if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                gps?.iniciarActualizaciones()
            } else { // Permiso negado
                val dialogo = AlertDialog.Builder(this)
                dialogo.setMessage("Esta app requiere GPS, ¿Quieres configurar el permiso?")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity (intent)
                    })
                    .setNeutralButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                        println("No hay forma de usar gps, cerrar la actividad")
                        //Deshabilitar funcionalidad
                    })
                    .setCancelable(false)
                dialogo.show()
            }
        }
    }
    override fun actualizarPosicion(posicion: Location) {
        latitud = posicion.latitude
        longitude = posicion.longitude

    }

}