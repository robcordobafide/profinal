package com.rtc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.rtc.databinding.ActivityRegistrarseBinding

class RegistrarseActivity : AppCompatActivity() {

    //variable tipo binding para acceder directamente a todos los controles, puente entre la progra y el frontend
    private lateinit var llamar: ActivityRegistrarseBinding

    //Para el desarrollo éxitoso del proceso de autentificación, se crea el objeto rAuth de tipo Firebase.
    private lateinit var rAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //La variable llamar es la que se encarga de permitirnos acceder a los controladores de la parte gráfica.
        llamar = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(llamar.root)

        //Llamamos al botón que tiene la flecha para devolverse a la actividad de login.
        llamar.btAtras.setOnClickListener { navegarAtras() }

        //Llamamos al botón 'registrarse' que permite que la persona, una vez completó los campos, se registre en el app con sus datos
        llamar.btRegistrarse.setOnClickListener { registrarse() }

        FirebaseApp.initializeApp(this)
        rAuth = Firebase.auth

    }
    //Si el  campo del correo está vacío o el campo de la contraseña lo está
    //retorna 'true' pero el '!' lo pasa a falso, por ende;
    //si el campo del correo o contraseña no están completos, significa que los camposCompletos=FALSE.
    private fun camposCompletos(): Boolean{
        return !(llamar.etCorreo.text.isEmpty() || llamar.etContrasenia.text.isEmpty())
    }

    private fun registrarse() {
        if (camposCompletos()){ //chequeamos si los campos están completos con información.
            val mail = llamar.etCorreo.text.toString()
            val contrasenia = llamar.etContrasenia.text.toString()
            rAuth.createUserWithEmailAndPassword(mail,contrasenia) //llamamos a la función que permite la creación de usuario con mail y contraseña.
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){ //ósea el usuario se registró con éxito
                        val usuario = rAuth.currentUser
                        irPantallaInicio(usuario)
                    }else{ //no funcionó el registro
                            Toast.makeText(baseContext,
                            getText(R.string.msj3_falla_registro),
                            Toast.LENGTH_SHORT).show()
                            irPantallaInicio(null)
                    }
                }
        }else{
                Toast.makeText(baseContext,
                getText(R.string.msj4_faltan_campos),
                Toast.LENGTH_SHORT).show()
        }

    }
    //función que nos permite que el usuario pueda ir a la pantalla de inicio del app, una vez completó
    //correctamente el registro con correo
    private fun irPantallaInicio(usuario: FirebaseUser?) {
        if(usuario != null){ //el usuario está autenticado
            val intento= Intent(this,PrincipalActivity::class.java)
            startActivity(intento)
        }
    }

    //función que nos permite que la flecha devuelva al usuario a la actividad de inicio de sesión con correo y contraseña
    private fun navegarAtras() {
        val intento= Intent(this,InicioSesionActivity::class.java)
        startActivity(intento)
    }
}