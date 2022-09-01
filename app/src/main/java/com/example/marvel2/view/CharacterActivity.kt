package com.example.marvel2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marvel2.R
import com.example.marvel2.data.CharacterDTO
import com.example.marvel2.data.MarvelApi
import com.example.marvel2.data.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterActivity : AppCompatActivity() {

    private var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        if(intent!=null){
            id = intent.getIntExtra("id",0)
            Log.e("id",id.toString())

            val apiInterface = MarvelApi.create().getCharacterById(id)

            apiInterface.enqueue( object : Callback<CharacterDTO> {
                override fun onResponse(
                    call: Call<CharacterDTO>,
                    response: Response<CharacterDTO>
                ) {
                    if (response?.body() != null)
                        buildView((response.body()!!.data.results))

                }

                override fun onFailure(call: Call<CharacterDTO>, t: Throwable) {
                    showError(t?.message)
                }
            })
        }
    }

    private fun buildView(characterList: List<Result>) {
        findViewById<TextView>(R.id.tvCharacterNameDetail).text = characterList[0].name
        findViewById<TextView>(R.id.tvCharacterDescription).text = characterList[0].description
        var image:ImageView = findViewById(R.id.ivCharacterDetail)

        val imageUrl = "${characterList[0].thumbnail.path}/portrait_xlarge.${characterList[0].thumbnail.extension}"
        val imageUrlSecured = imageUrl.replace("http","https")
        Glide.with(image.context)
            .load(imageUrlSecured)
            .apply(RequestOptions().centerCrop())
            .into(image)
    }

    private fun showError(message: String?) {
        Toast.makeText(this@CharacterActivity,message.toString(), Toast.LENGTH_LONG).show()
        Log.v("error",message.toString())
    }
}

