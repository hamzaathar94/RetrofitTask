package com.example.retrofittask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofittask.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private var binding:ActivityMain3Binding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding?.root)


    }
}