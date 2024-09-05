package br.com.alu.challengeentrega.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val linkRegistrarTela = findViewById<TextView>(R.id.entre_text)
        val linkRecuperarSenha = findViewById<TextView>(R.id.esqueci_senha_text)

        val recuperarSenhaActivity = Intent(this, RecuperarSenhaActivity::class.java)
        val btnEntrar = findViewById<Button>(R.id.button_entrar)
        val email = findViewById<EditText>(R.id.email_input)
        val senha = findViewById<EditText>(R.id.senha_input)
        val AnaliseMercadoActivity = Intent(this, LoginActivity::class.java)

        val gson = Gson()
        val client = OkHttpClient()

        val registerURL = "https://backend-challenge-mobile.vercel.app/auth/login"

        btnEntrar.setOnClickListener{
            if(!Validations.validadeEmail(email.text.toString())){
                val toast = Toast.makeText(
                    this@LoginActivity,
                    "O email é inválido", Toast.LENGTH_LONG
                )
                toast.show()
                return@setOnClickListener
            }else if(senha.text.toString().isEmpty()){
                val toast = Toast.makeText(
                    this@LoginActivity,
                    "As senhas não são iguais", Toast.LENGTH_LONG
                )
                toast.show()
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
                    Log.e("ERROR-REGISTRAR", e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.v("CONTEUDO", response.body?.string()!!)
                    if(response.code == 200){
                        val toast = Toast.makeText(
                            this@LoginActivity,
                            "Registrado com sucesso", Toast.LENGTH_LONG
                        )
                        toast.show()

                        startActivity(AnaliseMercadoActivity)
                    }
                }
            }
            client.newCall(request).enqueue(response)
        }

        linkRecuperarSenha.setOnClickListener{
            startActivity(recuperarSenhaActivity)
        }

        linkRegistrarTela.setOnClickListener{
            val registrarActivity = Intent(this, RegistrarActivity::class.java)
            startActivity(registrarActivity)
        }
    }
}