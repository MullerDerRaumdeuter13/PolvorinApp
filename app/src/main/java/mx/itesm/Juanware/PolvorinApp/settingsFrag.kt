package mx.itesm.Juanware.PolvorinApp


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_eventos.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [settingsFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class settingsFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("OnCreateView")

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pref = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        super.onViewCreated(view, savedInstanceState)

        if (pref != null) {
            loadPreferences(view, pref)
        }
        buttonAceptar.setOnClickListener{
            if (pref != null) {
                aceptarCambios(view, pref)
            }
        }

        myEvents_sw.setOnClickListener{
            if (pref != null)
                cambiarMyEvents(myEvents_sw.isChecked(), pref)
        }

        participo_sw.setOnClickListener{
            if (pref != null)
                cambiarParticipo(participo_sw.isChecked(), pref)
        }

        }

}

    fun aceptarCambios(view: View, pref: SharedPreferences){
        val insertedText  = view.editTextNumber.text.toString()

        if (insertedText.toInt() < 1500) {
            val editor = pref.edit()
            editor.apply{putInt("DIST_KEY", insertedText.toInt())}.apply()
        }
    }

    fun cambiarParticipo(isChecked: Boolean, pref: SharedPreferences) {
        val editor = pref.edit()
        if (isChecked){
            editor.apply{putInt("PARTICIPO_KEY", 1)}.apply()
        } else{
            editor.apply{putInt("PARTICIPO_KEY", 0)}.apply()
        }
    }

    fun cambiarMyEvents(isChecked: Boolean, pref: SharedPreferences){
        val editor = pref.edit()
        if (isChecked){
            editor.apply{putInt("MY_EVENTS_KEY", 1)}.apply()
        } else{
            editor.apply{putInt("MY_EVENTS_KEY", 0)}.apply()
        }
    }

    fun loadPreferences(view: View, pref: SharedPreferences){
        val savedInt = pref.getInt("DIST_KEY",10)
        val myEvento = pref.getInt("MY_EVENTS_KEY", 0)
        val participo = pref.getInt("PARTICIPO_KEY", 0)
        var myEventoBool = false
        var participoBool = false
        if (myEvento == 1){
            myEventoBool = true
        }
        if (participo == 1){
            participoBool = true
        }
        view.editTextNumber.setText(savedInt.toString())
        view.myEvents_sw.setChecked(myEventoBool)
        view.participo_sw.setChecked(participoBool)


    }


  /*  companion object {
        *//**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment settingsFrag.
         *//*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            settingsFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
