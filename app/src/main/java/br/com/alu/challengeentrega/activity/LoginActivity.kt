package br.com.alu.challengeentrega.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alu.challengeentrega.R
import br.com.alu.challengeentrega.utils.Validations
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

class LoginActivity : AppCompatActivity(R.layout.login_layout) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linkRegistrarTela = findViewById<TextView>(R.id.entre_text)
        val linkRecuperarSenha = findViewById<TextView>(R.id.esqueci_senha_text)

        val recuperarSenhaActivity = Intent(this@LoginActivity, RecuperarSenhaActivity::class.java)
        val btnEntrar = findViewById<Button>(R.id.button_entrar)
        val email = findViewById<EditText>(R.id.email_input)
        val senha = findViewById<EditText>(R.id.senha_input)
        val analiseMercadoActivity = Intent(this@LoginActivity, AnaliseMercadoActivity::class.java)

        val emailIntent = intent.getStringExtra("email") != null

        if(emailIntent){
            email.setText(intent.getStringExtra("email"))
        }

        val gson = Gson()
        val client = OkHttpClient()

        val registerURL = "https://backend-challenge-mobile.vercel.app/auth/login"

        btnEntrar.setOnClickListener {
            if (!Validations.validadeEmail(email.text.toString())) {
                runOnUiThread {

                    val toast = Toast.makeText(
                        this@LoginActivity,
                        "O email é inválido", Toast.LENGTH_LONG
                    )
                    toast.show()
                }
                return@setOnClickListener
            } else if (senha.text.toString().isEmpty()) {

                runOnUiThread {
                    val toast = Toast.makeText(
                        this@LoginActivity,
                        "A senha não pode ser nula", Toast.LENGTH_LONG
                    )
                    toast.show()
                }
                return@setOnClickListener
            }

            val body = """
                {
                    "email": "${email.text}",
                    "senha": "${senha.text}"
                }
            """.trimIndent()

            val request = Request.Builder()
                .url(registerURL)
                .post(body.toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ERROR-LOGIN", e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 200) {
                        analiseMercadoActivity.putExtra("email", email.text.toString())
                        startActivity(analiseMercadoActivity)
                    } else {
                        runOnUiThread {

                            val toast = Toast.makeText(
                                this@LoginActivity,
                                "Senha ou email são inválidos", Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    }
                }
            }
            client.newCall(request).enqueue(response)
        }

        linkRecuperarSenha.setOnClickListener {
            startActivity(recuperarSenhaActivity)
        }

        linkRegistrarTela.setOnClickListener {
            val registrarActivity = Intent(this, RegistrarActivity::class.java)
            startActivity(registrarActivity)
        }
    }
}