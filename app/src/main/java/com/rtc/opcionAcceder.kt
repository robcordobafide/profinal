package com.rtc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rtc.databinding.ActivityOpcionAccederBinding

class opcionAcceder : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 150
    }

    private lateinit var binding: ActivityOpcionAccederBinding
    private lateinit var gAuth: FirebaseAuth
    private lateinit var GAuth: FirebaseAuth //FirebaseAuth object.
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Autenticación con Google
        gAuth = FirebaseAuth.getInstance()
        val usuario = gAuth.currentUser

        Handler().postDelayed({
            if (usuario != null){ //si el usuario está logeado, lo mandamos al dashboard.
                val enviaDashboard = Intent(this, PrincipalActivity::class.java)
                startActivity(enviaDashboard)
                finish()
            }else{ //si el usuario no se ha autenticado, lo enviamos a que lo haga, recordandole que escoja su inicio de sesión preferido.
                Toast.makeText(this, "Escoja una opción para Iniciar Sesión", Toast.LENGTH_LONG).show()
            }
        }, 5000)

        //Configuración del Google Sign In (tomada de la documentación oficial)
        //gso: Google Sign Options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) //"error" generado por un bug del Android Studio, el proyecto SÍ funciona, esto significa el ID del json
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)

        GAuth = FirebaseAuth.getInstance()

        binding = ActivityOpcionAccederBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Apenas el usuario toque el botón de inicio con Google, le va a aparecer la pantalla para que lo haga.
        binding.btGoogle.setOnClickListener{
            SignIn()
        }

        //Esto está relacionado a enviar al usuario al otro método de inicio de sesión si no quiere usar su cuenta de Google


        //Apenas alguien toque el botón de "Acceder con Correo" este vaya a la función entre { }
        binding.btInicio.setOnClickListener { navegarInicioSesion() }
    }

    //Esta función hace que el usuario vaya a la pantalla de inicio de sesión con correo.
    private fun navegarInicioSesion() {
        val intent= Intent(this, InicioSesionActivity::class.java)
        startActivity(intent)
    }

    //Metodo de iniciar sesión con Google
    private fun SignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception //obtener alguna excepciión que se presente
            if (task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("opcionAcceder", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("opcionAcceder", "Inicio con Google falló", e)
                }
            }else{
                Log.w("opcionAcceder", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        GAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("opcionAcceder", "signInWithCredential:success")
                    val accede = Intent(this, PrincipalActivity::class.java) // Todo salió bien, vamos a la pantalla principal
                    startActivity(accede)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)

                }
            }
    }

}