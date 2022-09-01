package com.example.marvel2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel2.data.CharacterDTO
import com.example.marvel2.data.MarvelApi
import com.example.marvel2.data.Result
import com.example.marvel2.view.CharacterActivity
import com.example.marvel2.view.RecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvCharacters)
        recyclerAdapter = RecyclerAdapter(this, ArrayList()){onItemSelected(it)}
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter = recyclerAdapter


        val apiInterface = MarvelApi.create().getCharacters(offset = 0)

        apiInterface.enqueue( object : Callback<CharacterDTO> {
            override fun onResponse(
                call: Call<CharacterDTO>,
                response: Response<CharacterDTO>
            ) {
                if(response?.body() != null)
                    recyclerAdapter.setCharacterListItems(response.body()!!.data.results as ArrayList<Result>)
            }

            override fun onFailure(call: Call<CharacterDTO>, t: Throwable) {
                showError(t?.message)
            }
        })
    }

    private fun showError(message: String?) {
        Toast.makeText(this,message.toString(),Toast.LENGTH_LONG).show()
        Log.v("error",message.toString())
    }

    fun onItemSelected(character : Result){
        Toast.makeText(this,character.name,Toast.LENGTH_LONG).show()
        val intent = Intent(baseContext,CharacterActivity::class.java).apply {
            putExtra("id", character.id.toInt())
        }
        println("id = ${character.id}")
        startActivity(intent)
    }
}