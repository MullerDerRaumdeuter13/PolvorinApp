package mx.itesm.Juanware.PolvorinApp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_agregar_equipo.*



class agregarEquipo : AppCompatActivity() {
    val DATABASE = FirebaseDatabase.getInstance().reference.child("Equipos")
    lateinit var STORAGE: StorageReference
    lateinit var imageViewPreview: ImageView
    lateinit var btnAccesoGaleria: Button
    private val IMG_CODE = 100
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_equipo)

        imageViewPreview = findViewById(R.id.imageViewPreview)
        btnAccesoGaleria = findViewById(R.id.btnBuscarImagen)

        btnAccesoGaleria.setOnClickListener {

            val accesoGaleria = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(accesoGaleria, IMG_CODE)
        }
        STORAGE = FirebaseStorage.getInstance().reference.child("Imagenes_de_equipos")
    }

    //selecciona la imagen de la galeria

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMG_CODE){
            if(data != null){
                imageUri = data.data
            }
            imageViewPreview.setImageURI(imageUri)
        }
    }

    //Click listener para cancelar la creacion de equipos
    fun cancelarCreacion(v: View){
        val regresarMenu = Intent(this, MainMenu::class.java)
        startActivity(regresarMenu)
        finish()
    }

    fun creaEquipo(v: View){



        val key = DATABASE.push().key
        var nombreEquipo = editTextNombre.text.toString()
        var integrantes = editTextIntegrantes.text.toString()
        var numeroTelefono = editTextTelefono.text.toString()
        var imageUrl = ""

        //Verificacion de que los campos fueron llenados correctamente
        if(nombreEquipo.isNullOrEmpty()){

            Toast.makeText(this, "Debes agregar un nombre.", Toast.LENGTH_SHORT).show()

        }else if(integrantes.toInt() <= 0 || integrantes.isNullOrEmpty()){

            Toast.makeText(this, "Número de integrantes no válido.", Toast.LENGTH_SHORT).show()

        }else if(numeroTelefono.isNullOrEmpty() || numeroTelefono.length > 10 || numeroTelefono.length < 10){

            Toast.makeText(this, "Número telefónico no válido.", Toast.LENGTH_SHORT).show()
        }else{
            //Todas las condiciones fueron verificadas y se puede proceder a subir los datos
            //TODO: arreglar el progress dialog
            /*val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Creando equipo...")
            progressDialog.setMessage("Porfavor espera...")
            progressDialog.show()*/
            //verifica que exista una llave para mandar la informacion.
            if(key != null){
                //envia la imagen seleccionada si existe alguna
                if(imageUri != null){
                    val imagenEquipo = STORAGE!!.child(System.currentTimeMillis().toString()+".jpg")
                    var uploadTask: StorageTask<*>
                    uploadTask = imagenEquipo.putFile(imageUri!!)
                    uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                        if(task.isSuccessful){
                            task.exception?.let{
                                throw it

                            }
                        }
                        return@Continuation imagenEquipo.downloadUrl
                    }).addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            val downloadUrl = task.result
                            val url = downloadUrl.toString()
                            imageUrl = url
                            DATABASE.child(key).setValue(Equipo(nombreEquipo, integrantes.toInt(), numeroTelefono, imageUrl))
                            //progressDialog.dismiss()
                        }
                    }

                }else{ //no envian ninguna imagen
                    imageUrl = ""
                    DATABASE.child(key).setValue(Equipo(nombreEquipo, integrantes.toInt(), numeroTelefono, imageUrl))
                    //progressDialog.dismiss()
                }
            }

            val regresarMenu = Intent(this, MainMenu::class.java)
            startActivity(regresarMenu)
            finish()
        }
    }
}