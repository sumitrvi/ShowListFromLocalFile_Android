package com.mvvm.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.livedata.app.databinding.ActivityPetListactivityBinding
import com.mvvm.adapter.PetAdapter
import com.mvvm.modal.petModal
import java.io.IOException
import java.util.*


class PetListactivity : AppCompatActivity() {
    lateinit var list: List<petModal>;
    lateinit var binding: ActivityPetListactivityBinding;
    lateinit var adapter: PetAdapter
    var calendar: Calendar = Calendar.getInstance()
    var day: Int = calendar.get(Calendar.DAY_OF_WEEK)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetListactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemRecycleView.setHasFixedSize(true)
        binding.itemRecycleView.layoutManager = LinearLayoutManager(this);
        getCountryCode(this);


        //Show dialog on the weekends
        if (day != Calendar.SATURDAY && day != Calendar.SUNDAY) {
            showErrorDialog();
        } else {
            //Current date instance
            val current = Calendar.getInstance();
            current.time = Date();
            current.time

            //Start date instance
            val dateobj = Date()
            val startcalendar = Calendar.getInstance()
            startcalendar.time = dateobj
            startcalendar[Calendar.HOUR_OF_DAY] = 9
            startcalendar[Calendar.MINUTE] = 0
            startcalendar[Calendar.SECOND] = 0
            System.out.println("Start time " + startcalendar.time) //


            //End date instance
            val endCalendar = Calendar.getInstance()
            endCalendar.time = dateobj
            endCalendar[Calendar.HOUR_OF_DAY] = 18
            endCalendar[Calendar.MINUTE] = 0
            endCalendar[Calendar.SECOND] = 0
            System.out.println("End time " + endCalendar.time) //


            if (current.time.after(startcalendar.time) && current.time.before(endCalendar.time)) {
                list = getCountryCode(this);
                print(list);
                adapter = PetAdapter(applicationContext, list)
                binding.itemRecycleView.adapter = adapter;
            } else {
                showErrorDialog();
            }

        }
    }

    //Show dialog on the weekend & non-official houras
    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this);
        builder.setTitle("Alert")
        builder.setMessage("Now! We're in support mode we'll update you once comes up")
        builder.setCancelable(false)
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                // When the user click yes button then app will close
                finish()
            } as DialogInterface.OnClickListener)

        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                // If user click no then dialog box is canceled.
                dialog.cancel()
            } as DialogInterface.OnClickListener)
        builder.show();


    }

    //get the data from the local file
    fun getCountryCode(context: Context): List<petModal> {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("pets_list.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            // AppLogger.d(ioException)
        }
        val listCountryType = object : TypeToken<List<petModal>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }
}
