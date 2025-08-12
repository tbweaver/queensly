package com.example.letstryagain

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buttonClick(view: View?){
        println("Hey guys, it's me again.")
    }
}

class MyViewModel : ViewModel() {

    fun fetchHtmlContent(url: String) {
        viewModelScope.launch {
            try {
                val htmlContent = getHtmlFromUrl(url)
                // Update UI with htmlContent (e.g., set to a LiveData or update a TextView)
                println("HTML Content: $htmlContent") // For demonstration
            } catch (e: Exception) {
                // Handle network errors or other exceptions
                e.printStackTrace()
            }
        }
    }
}

suspend fun getHtmlFromUrl(urlString: String): String {
    return withContext(Dispatchers.IO) {
        val url = URL(urlString)
        val urlConnection = url.openConnection() as java.net.HttpURLConnection
        try {
            urlConnection.inputStream.bufferedReader().use { reader ->
                reader.readText()
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}