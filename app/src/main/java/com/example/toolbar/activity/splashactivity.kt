package com.example.toolbar.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.toolbar.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class splashactivity : AppCompatActivity() {

    private val splashTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashactivity)

        supportActionBar?.hide()


        // Gerando um atraso com Coroutine
        CoroutineScope(Dispatchers.Main).launch {
            delay(splashTime)

            //ao finalizar o splash ir para página
            val intent = Intent(this@splashactivity,
                ListagemAluno::class.java)
            startActivity(Intent)
            finish()
        }


       /* //Splash - primeira forma
        Handler().postDelayed({
                              val intent = Intent(this, ListagemAluno::class.java)
            startActivity(intent)
            finish()
        }, 2000)*/
    }
}