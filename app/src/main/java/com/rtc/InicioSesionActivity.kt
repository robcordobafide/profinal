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
import com.rtc.databinding.ActivityInicioSesionBinding

class InicioSesionActivity : AppCompatActivity() {

    //variable tipo binding para acceder directamente a todos los controles, puente entre la progra y el frontend
    private lateinit var llamar: ActivityInicioSesionBinding

    //variable de tipo auth que nos permite hacer tod el proceso de autenticación
    private lateinit var lAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        llamar = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(llamar.root)

        //Apenas alguien toque el botón de "Registrarse" este vaya a la función entre { }
        llamar.btRegistrar.setOnClickListener { navegarRegistrar() }
        llamar.btAcceder.setOnClickListener { IniciarSesion() }


        //Inicializamos "lAuth" para acceder a Firebase.
        FirebaseApp.initializeApp(this)
        lAuth = Firebase.auth
    }
    //Si el  campo del correo está vacío o el campo de la contraseña lo está
    //retorna 'true' pero el '!' lo pasa a falso, por ende;
    //si el campo del correo o contraseña no están completos, significa que los camposCompletos=FALSE.
    private fun camposCompletos(): Boolean{
        return !(llamar.etMail.text.isEmpty() || llamar.etContra.text.isEmpty())
    }

    private fun IniciarSesion() {
        if (camposCompletos()){ //chequeamos si los campos están completos con información.
            val mail = llamar.etMail.text.toString()
            val contrasenia = llamar.etContra.text.toString()
            lAuth.signInWithEmailAndPassword(mail,contrasenia) //llamamos a la función que permite  el login de usuario con mail y contraseña.
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){ //ósea el usuario accedió con éxito
                        val usuario = lAuth.currentUser
                        irPantallaInicio(usuario)
                    }else{ //no funcionó el inicio de sesión
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

    private fun irPantallaInicio(usuario: FirebaseUser?){
        if(usuario != null){ //el usuario está autenticado
            val intento= Intent(this,PrincipalActivity::class.java)
            startActivity(intento)
        }
    }

    private fun navegarRegistrar() {
        val intento=Intent(this,RegistrarseActivity::class.java)
        startActivity(intento)
    }
}