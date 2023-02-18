package com.example.vkserviceapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_screen)
        /** getting all widgets */
        val nameView = findViewById<TextView>(R.id.serviceNameView)
        val descriptionView = findViewById<TextView>(R.id.serviceDescriptionView)
        val linkView = findViewById<TextView>(R.id.serviceLinkView)
        val iconView = findViewById<ImageView>(R.id.serviceIconView)
        /** setting up action bar */
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //back button
        supportActionBar?.title = bundle?.getString("name") //title
        nameView.text = bundle?.getString("name") //set service name
        descriptionView.text = bundle?.getString("description") // set service description
        linkView.text = bundle?.getString("service_url") // set service url
        Picasso.get().load(bundle?.getString("icon_url")).into(iconView)  // set service icon
    }

    /** function to return back when action bar button clicked */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
