package com.example.vkserviceapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import api.Api
import com.squareup.picasso.Picasso
import pojo.Service

class MainActivity : AppCompatActivity() {
    private lateinit var layout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fillContent().start()
        layout = findViewById(R.id.container)
        val button = findViewById<Button>(R.id.retryButton) // getting retry button instance
        button.setOnClickListener { fillContent().start() } // to retry connection
    }

    /** function that creates a thread for working with API */
    private fun fillContent(): Thread {
        return Thread(
            kotlinx.coroutines.Runnable {
                val button = findViewById<Button>(R.id.retryButton) // retry button
                try {
                    /** fetching data from API */
                    val api = Api()
                    val request = api.fetchData()
                    /** update widget with API data */
                    Handler(Looper.getMainLooper()).post {
                        button.visibility = View.GONE
                        for (service in request.items) addCard(service) // adding cards to listview
                    }
                } catch (_: java.lang.Exception) {
                    /** catching error if internet connection is lost */
                    Handler(Looper.getMainLooper()).post {
                        button.visibility = View.VISIBLE
                        /** showing alert dialog with instructions */
                        AlertDialog.Builder(this)
                            .setTitle("Отсутствует интернет-соединение")
                            .setMessage("Подключите интернет и перезайдите в приложение.")
                            .setPositiveButton("Ok", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                }
            }
        )
    }

    /** function to add card to listview */
    @SuppressLint("InflateParams")
    private fun addCard(service: Service) {
        val view: View = layoutInflater.inflate(R.layout.card, null) // listview instance
        val nameView = view.findViewById<TextView>(R.id.serviceName) // card name of service
        val imageView = view.findViewById<ImageView>(R.id.serviceImage) // card icon of service
        Picasso.get().load(service.icon_url).into(imageView) // putting icon from url to widget
        nameView.text = service.name // setting up service name
        layout.addView(view) // adding card to listview
        view.setOnClickListener {
            /** creating bundle to pass service arguments to another activity */
            val bundle = Bundle()
            bundle.putString("name", service.name)
            bundle.putString("description", service.description)
            bundle.putString("icon_url", service.icon_url)
            bundle.putString("service_url", service.service_url)
            /** creating another activity */
            val intent = Intent(applicationContext, DetailsScreen::class.java)
            intent.putExtras(bundle) // passing arguments to activity
            startActivity(intent) // starting details screen activity
        }
    }
}
