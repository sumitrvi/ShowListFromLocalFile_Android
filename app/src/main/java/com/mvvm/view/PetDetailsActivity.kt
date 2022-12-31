package com.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.livedata.app.R
import com.livedata.app.databinding.ActivityPetDetailsBinding
import com.mvvm.modal.petModal
import com.squareup.picasso.Picasso

class PetDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPetDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetDetailsBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val petData: petModal = intent.getSerializableExtra("petData") as petModal;


        //Bind data the UI components
        if (petData != null) {
            binding.petTitle.text = petData.title
            binding.petContentUrl.text = petData.content_url
            Picasso
                .get()
                .load(petData.image_url).fit()
                .error(R.drawable.placeholder_img)
                .placeholder(R.drawable.placeholder_img)
                .into(binding.petIMG);

        }


    }
}