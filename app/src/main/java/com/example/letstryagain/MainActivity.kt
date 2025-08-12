package com.example.letstryagain

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData

import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val urlLiveData = MutableLiveData<String>()

    fun fetchUrl(url: String): String {
        viewModelScope.launch {
            // Dispatchers.IO (main-safety block)
            withContext(Dispatchers.IO) {
                fetchAsync(url)
            }
        }
    }

    private suspend fun fetchAsync(url: String) {
        urlLiveData.postValue(URL(url).readText())
    }

}

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.urlLiveData.observe(
            viewLifecycleOwner,
            Observer { urlText ->
                mainTextView.setText(urlText)
            }
        )
    }

    mainViewModel.fetchUrl(""https://random-app.appspot.com/")
}