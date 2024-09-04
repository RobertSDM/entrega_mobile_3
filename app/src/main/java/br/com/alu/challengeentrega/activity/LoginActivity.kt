package br.com.alu.challengeentrega.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import br.com.alu.challengeentrega.R

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val linkRegistrarTela = findViewById<TextView>(R.id.entre_text)
        val linkRecuperarSenha = findViewById<TextView>(R.id.esqueci_senha_text)


        linkRegistrarTela.setOnClickListener{
            val registrarActivity = Intent(this, RegistrarActivity::class.java)
            startActivity(registrarActivity)
        }
    }
}