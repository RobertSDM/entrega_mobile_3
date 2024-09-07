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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

class RecuperarSenhaActivity : AppCompatActivity(R.layout.recuperar_senha_layout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = findViewById<EditText>(R.id.email)
        val novaSenha = findViewById<EditText>(R.id.nova_senha)
        val confirmarNovaSenha = findViewById<EditText>(R.id.confirmar_nova_senha)
        val btnTrocarSenha = findViewById<Button>(R.id.trocar_senha_btn)
        val linkLoginActivity = findViewById<TextView>(R.id.textView7)

        val loginActivity = Intent(this@RecuperarSenhaActivity, LoginActivity::class.java)

        val client = OkHttpClient()

        val url = "https://backend-challenge-mobile.vercel.app/auth/pass_forgot"

        btnTrocarSenha.setOnClickListener {
            if (!Validations.validadeEmail(email.text.toString())) {
                val toast = Toast.makeText(
                    this@RecuperarSenhaActivity,
                    "O email é inválido", Toast.LENGTH_LONG
                )
                toast.show()
                return@setOnClickListener
            } else if (!Validations.validateTwoStrings(
                    novaSenha.text.toString(),
                    confirmarNovaSenha.text.toString()
                ) &&
                (novaSenha.text.toString().isEmpty() || confirmarNovaSenha.text.toString()
                    .isEmpty())
            ) {

                val toast = Toast.makeText(
                    this@RecuperarSenhaActivity,
                    "As senhas não são iguais", Toast.LENGTH_LONG
                )
                toast.show()
                return@setOnClickListener
            }

            val body = """
                {
                    "novaSenha" : "${novaSenha.text}",
                    "email": "${email.text}"
                }
            """.trimIndent()

            val request = Request.Builder()
                .url(url)
                .put(body.toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ERROR-REGISTRAR", e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.v("CONTEUDO", response.body?.string()!!)
                    if (response.code == 200) {
                        loginActivity.putExtra("email", email.text.toString())
                        startActivity(loginActivity)
                    }
                }
            }
            client.newCall(request).enqueue(response)
        }

        linkLoginActivity.setOnClickListener {
            startActivity(loginActivity)
        }

    }

}