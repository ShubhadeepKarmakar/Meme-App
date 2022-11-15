package com.example.meme

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    var currentImageView: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
private fun loadMeme(){
    // Instantiate the RequestQueue.
    val progressbar=findViewById<ProgressBar>(R.id.progressBar)
    progressbar.visibility=View.VISIBLE
    val queue = Volley.newRequestQueue(this)
    val url = "https://meme-api.herokuapp.com/gimme"
    val ImageView=findViewById<ImageView>(R.id.imageView)
// Request a string response from the provided URL.
    val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
        { response ->
//            val url=response.getString("url")
            currentImageView=response.getString("url")
            Glide.with(this).load(currentImageView).listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar.visibility=View.GONE
                    return false
                }
            }).into(ImageView)
        },
        {

        })

// Add the request to the RequestQueue.
    queue.add(JsonObjectRequest)
}

    fun next(view: View) {
        loadMeme()
    }
    fun share(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plane"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, check $currentImageView")
        val chooser=Intent.createChooser(intent,"shubhadeep....")
        startActivity(chooser)
    }
}