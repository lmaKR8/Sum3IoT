package com.example.compraventa

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.compraventa.databinding.ActivityRegistroEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.intellij.lang.annotations.Pattern

class Registro_email : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnRegistrar.setOnClickListener{
            validarInfo()
        }
    }

    private var email = ""
    private var password = ""
    private var r_password = ""
    private fun validarInfo() {
        email = binding.EtEmail.text.toString().trim()
        password = binding.EtPassword.text.toString().trim()
        r_password = binding.EtRPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmail.error = "Email Inválido"
            binding.EtEmail.requestFocus()
        }
        else if (email.isEmpty()){
            binding.EtEmail.error = "Ingresa Email"
            binding.EtEmail.requestFocus()
        }
        else if (password.isEmpty()){
            binding.EtPassword.error = "Ingrese Password"
            binding.EtPassword.requestFocus()
        }
        else if (r_password.isEmpty()){
            binding.EtRPassword.error = "Repita el Password"
            binding.EtRPassword.requestFocus()
        }
        else if (password != r_password){
            binding.EtRPassword.error = "No coincide el Password"
            binding.EtRPassword.requestFocus()
        }
        else{
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        progressDialog.setMessage("Creando cuenta de usuario")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                llenarInfoBD()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Error en el registro: ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun llenarInfoBD() {
        progressDialog.setMessage("Guardando información")

        val tiempo = Constantes.obtenerTiempoDispositivo()
        val emailUsuario = firebaseAuth.currentUser!!.email
        val uidUsuario = firebaseAuth.uid

        val hashMap = HashMap<String, Any>()
        hashMap["nombres"] = ""
        hashMap["codigoTelefono"] = ""
        hashMap["telefono"] = ""
        hashMap["urlImagenPerfil"] = ""
        hashMap["proveedor"] = "Email"
        hashMap["escribiendo"] = ""
        hashMap["tiempo"] = tiempo
        hashMap["online"] = true
        hashMap["email"] = "${emailUsuario}"
        hashMap["uid"] = "${uidUsuario}"
        hashMap["fecha_nacimiento"] = ""

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this, "No se registró debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}