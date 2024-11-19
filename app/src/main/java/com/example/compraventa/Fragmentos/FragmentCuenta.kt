package com.example.compraventa.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.compraventa.OpcionesLogin
import com.example.compraventa.databinding.FragmentCuentaBinding
import com.google.firebase.auth.FirebaseAuth

class FragmentCuenta : Fragment() {

    private lateinit var binding: FragmentCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mContex: Context

    override fun onAttach(context: Context) {
        mContex = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCuentaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.BtnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(mContex, OpcionesLogin::class.java))
            activity?.finishAffinity()
        }
    }

}