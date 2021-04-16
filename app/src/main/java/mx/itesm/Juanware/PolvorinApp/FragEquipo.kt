package mx.itesm.Juanware.PolvorinApp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_frag_equipo.*

class FragEquipo(act: AppCompatActivity) : Fragment() {

    var act = act


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_equipo, container, false)

    }




}