package com.example.retrofittask

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittask.databinding.ActivityMainBinding
import com.example.roomdatabasetask.PostAdapter
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.measureTimeMillis

const val url="https://jsonplaceholder.typicode.com/"
class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    private var apiService:ApiService?=null
    private var progressDialog:ProgressDialog?=null
    private val TAG:String="Hamza"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        apiService=RetrofitHelper.getInstance().create(ApiService::class.java)

        val recycle=binding?.recyclerview
      //  getUser()

       // getUserByID()

        //getMyData()

        val retrofitInstance=RetrofitInstance.buildService(ApiInterface::class.java)
        val call=retrofitInstance.getPosts()

        binding?.btngetall?.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    //showLoading("Getting,  Please wait")
                    call.enqueue(object :Callback<MutableList<PostModel>>{
                        override fun onResponse(
                            call: Call<MutableList<PostModel>>,
                            response: Response<MutableList<PostModel>>
                        ) {
                            if(response.isSuccessful){
                                recycle?.apply {
                                    layoutManager=LinearLayoutManager(this@MainActivity)
                                    adapter=PostAdapter().apply {
                                        setData(response.body()!!)
                                    }

                                }
                                Log.d("TAG",response.body().toString())
                            }
                        }

                        override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                            Log.d("TAG","Failure:"+t.message)
                        }

                    })
                   // progressDialog?.show()
                }



        }

        binding?.btnget?.setOnClickListener {
            getUserByID()
        }

        binding?.btnupdate?.setOnClickListener {
            updateUser()
        }

        binding?.btndelete?.setOnClickListener {
            deleteUser()
        }
        binding?.btngetrequ?.setOnClickListener {
            createUser()
        }

        binding?.btnmove?.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }


    }
    private fun createUser(){
        lifecycleScope.launch {
            val body=JsonObject().apply {
                val name=binding?.editTextTextPersonName4?.text.toString()
                val job=binding?.editTextTextPersonName5?.text.toString()

                addProperty("name",name)
                addProperty("job",job)
            }
            showLoading("Creating, Please wait")
            val result=apiService?.createUser(body)
            if (result!!.isSuccessful){
                Log.d("ooo","createUser success : ${result.body()}")
            }else{
                Log.d("ooo","createUser faild : ${result.message()}")
            }
            progressDialog?.dismiss()
        }
    }

    private fun deleteUser() {
        lifecycleScope.launch {
            val id=binding?.edtxtid?.text.toString()
            showLoading("Deleting, Please wait...")
            val result=apiService?.deleteUser(id)
            if (result!!.isSuccessful){
                Log.d("ooo","deleteUser:${result.body()}")
            }else{
                Log.d("ooo","deleteUser:${result.message()}")
            }
            progressDialog?.dismiss()
        }
    }

    private fun updateUser(){
        lifecycleScope.launch {
            val body=JsonObject().apply {
//                addProperty("name","vichit coding delivery")
//                addProperty("job","android developer")

                val name=binding?.editTextTextPersonName4?.text.toString()
                val job=binding?.editTextTextPersonName5?.text.toString()

                addProperty("name",name)
                addProperty("job",job)
            }
            val id=binding?.edtxtid?.text.toString()
            showLoading("Updating, Please wait...")
            val result= apiService?.updateUser(id,body)
            if (result!!.isSuccessful){
                Log.d("ooo","updateUser:${result.body()}")
            }else{
                Log.d("ooo","updateUser:${result.message()}")
            }
            progressDialog?.dismiss()
        }
    }
    private fun getUser(){
        CoroutineScope(Dispatchers.Main).launch {
            showLoading("Getting, Please wait...")
            val result=apiService?.getUser()
            if (result != null) {
                Log.d("ooo",result.body().toString())
            }
//            if (result!!.isSuccessful){
//                binding?.recyclerview?.apply {
//                    layoutManager=LinearLayoutManager(this@MainActivity)
//                    adapter=PostAdapter().apply {
//                        result.body()?.let { setData(it) }
//                    }
//                }
//            }
            else{
                Toast.makeText(this@MainActivity,result?.message(),Toast.LENGTH_SHORT).show()
            }
            progressDialog?.dismiss()
        }
    }

    private fun getUserByID() {
      //  Log.d(TAG,"${Thread.currentThread().name}")
        lifecycleScope.launch {
            val id= binding?.edtxtid?.text.toString()
            showLoading("Getting, please wait...")
            val result=apiService?.getUserByID(id)
            if (result!!.isSuccessful){
                //Get request success
                Log.d("ooo","getUserByID:${result.body()}")
               // binding?.txtemail?.text?.equals(result.body()?.data?.first_name.toString())
                val email=result.body()?.data?.first_name.toString()
                val fname=result.body()?.data?.first_name.toString()
                val lname=result.body()?.data?.last_name.toString()
                val url=result.body()?.data?.avatar

                Log.d("ooo","First-Name :"+fname)
                Log.d("ooo","Last-Name :"+lname)
                Log.d("ooo","Email :"+email)
                binding?.textView?.text=email
                binding?.textView3?.text=fname
                binding?.textView4?.text=lname

                if (url != null) {
                    Log.d("ooo","Image :"+url)
                    Picasso.with(this@MainActivity)
                        .load(url)
                        .centerCrop()
                        .resize(200,200)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding?.imageView)
                }else{Toast.makeText(this@MainActivity,"No url found",Toast.LENGTH_SHORT).show()}
            }else{
                Log.d("ooo","getUserByID field:${result.message()}")
            }
            progressDialog?.dismiss()
        }

    }

//    private fun getMyData() {
//        val retrofitBuilder=Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(url)
//            .build()
//            .create(ApiInterface::class.java)
//
//        val retrofitData=retrofitBuilder.getData()
//
//        retrofitData.enqueue(object :Callback<MyData>{
//            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
//                val responseBody=response.body()!!
//
//                val myStringBuilder=StringBuilder()
//
//                for (myData in responseBody){
//                    myStringBuilder.append(myData.toString())
//                    myStringBuilder.append("\n")
//                }
//
//               // binding?.txtId?.text=myStringBuilder
//            }
//
//            override fun onFailure(call: Call<MyData>, t: Throwable) {
//                Log.d("TAG","onFailure"+t.message)
//            }
//
//        })
//    }

    private fun showLoading(msg:String){
        progressDialog= ProgressDialog.show(this,null,msg,true)

    }
}