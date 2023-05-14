package com.example.retrofittask

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofittask.databinding.ActivityMain2Binding
import kotlinx.coroutines.*
import java.net.URL
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class MainActivity2 : AppCompatActivity() {
    private var binding:ActivityMain2Binding?=null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

//        runBlocking {
//            Log.d("ooo","Main starts")
//            joinAll(
//                async { coroutineOne() },
//                async { coroutineTwo() }
//            )
//            Log.d("ooo","Main ends")
//        }

       // create100kThreads()
       // create100kCoroutines()

        binding?.button?.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("ooo","onCreate : ThreadName : ${Thread.currentThread().name}")
                val url=URL("https://i.redd.it/bfc0pz8qdji61.jpg")

                val bitmap= BitmapFactory.decodeStream(url.openStream())

                withContext(Dispatchers.Main){
                    Log.d("ooo","onCreate withContext : ThreadName : ${Thread.currentThread().name}")
                    binding?.imageView3?.setImageBitmap(bitmap)
                }

            }

        }
        binding?.btnmove?.setOnClickListener {
            startActivity(Intent(this,MainActivity3::class.java))
        }


    }

    fun create100kCoroutines()= runBlocking{
        val duration= measureTimeMillis {
            repeat(100_000){
                launch {
                    delay(1000)
                    Log.d("ooo",".")
                }
            }
        }
        Log.d("time","Time : ${duration/1000} seconds")

    }

    fun create100kThreads(){
        val duration= measureTimeMillis {
            repeat(100_000){
                thread {
                    Thread.sleep(1000)
                    Log.d("ooo",".")
                }
            }
        }
        Log.d("time","Time : ${duration/1000} seconds")

    }

    suspend fun coroutineOne(){
        Log.d("ooo","coroutine one is started")
        delay(3000)
        Log.d("ooo","coroutine one is complete")
    }

    suspend fun coroutineTwo(){
        Log.d("ooo","coroutine two is started")
        delay(1000)
        Log.d("ooo","coroutine two is complete")
    }

}