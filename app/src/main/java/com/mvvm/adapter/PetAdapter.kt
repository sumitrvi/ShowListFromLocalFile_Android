package com.mvvm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.livedata.app.R
import com.mvvm.modal.petModal
import com.mvvm.view.PetDetailsActivity
import com.squareup.picasso.Picasso

class PetAdapter(var context: Context, val list: List<petModal>) :
    RecyclerView.Adapter<PetAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImg)
        val titleView: TextView = itemView.findViewById(R.id.itemName)
        val rootLayout: LinearLayout = itemView.findViewById(R.id.rootLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = list[position]
//        if (holder != null) {
        holder.titleView.text = list[position].title
//        }
        Picasso.get()
            .load(list[position].image_url)
//            .resize(100, 100)
            .fit()
            .placeholder(R.drawable.placeholder_img)
            .error(R.drawable.placeholder_img)
            .into(holder.imageView);


        //Click on this switch to details screen
        holder.rootLayout.setOnClickListener({
            val intent = Intent(context, PetDetailsActivity::class.java);
            intent.putExtra("petData", list[position]);
            it.context.startActivity(intent);
        })

    }

    override fun getItemCount(): Int {
        return list.size;
    }
}