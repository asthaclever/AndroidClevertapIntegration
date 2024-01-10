package com.example.clevertapdemo

import android.app.NotificationManager
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.PushPermissionResponseListener
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.example.clevertapdemo.databinding.ActivityMainBinding
import java.util.Date

class MainActivity: AppCompatActivity(), CTInboxListener,
    DisplayUnitListener {

    private lateinit var binding: ActivityMainBinding
    private var cleverTapDefaultInstance: CleverTapAPI? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)

        CleverTapAPI.setDebugLevel(3)
        //MobileAds.initialize(this) {}
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(this)
      //  cleverTapDefaultInstance?.registerPushPermissionNotificationResponseListener(this)


//        //init app inbox
//        cleverTapDefaultInstance?.apply {
//            ctNotificationInboxListener = this@MainActivity
//            //Initialize the inbox and wait for callbacks on overridden methods
//            initializeInbox()
//        }
//
//
        //Native display
        cleverTapDefaultInstance?.apply {
            setDisplayUnitListener(this@MainActivity)
        }
        //For events
        binding.BtAddEvent.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("Product viewed")
        }

        //For events with properties
        binding.BtAddEventWithProperties.setOnClickListener {
            // event with properties
            val prodViewedAction = HashMap<String, Any>()
            prodViewedAction["Product Name"] = "Casio Chronograph Watch"
            prodViewedAction["Category"] = "Mens Accessories"
            prodViewedAction["Price"] = 59.99
            prodViewedAction["Date"] = Date()

            cleverTapDefaultInstance?.pushEvent("Product viewed", prodViewedAction)
        }

        //For user events
        binding.BtOnUserLogin.setOnClickListener {
            val profileUpdate = HashMap<String, Any>()
            profileUpdate["Name"] = binding.etName.text.toString() // String
//            profileUpdate["Identity"] = ""+ cleverTapDefaultInstance!!.cleverTapID;  // String or number
            profileUpdate["Identity"] = binding.etIdentity.text.toString()  // String or number
            profileUpdate["Email"] = binding.etEmail.text.toString() // Email address of the user
            profileUpdate["Phone"] =
                "+91"+binding.etPhone.text.toString() // Phone (with the country code, starting with +)
            profileUpdate["Gender"] = "M" // Can be either M or F
            profileUpdate["DOB"] =
                Date() // Date of Birth. Set the Date object to the appropriate value


            // optional fields. controls whether the user will be sent email, push etc.
            // optional fields. controls whether the user will be sent email, push etc.
            profileUpdate["MSG-email"] = false // Disable email notifications
            profileUpdate["MSG-push"] = true // Enable push notifications
            profileUpdate["MSG-sms"] = false // Disable SMS notifications
            profileUpdate["MSG-whatsapp"] = true // Enable WhatsApp notifications


            //custom profile properties
            val stuff = ArrayList<String>()
            stuff.add("bag")
            stuff.add("shoes")
            profileUpdate["MyStuff"] = stuff //ArrayList of Strings
            val otherStuff = arrayOf("Jeans", "Perfume")
            profileUpdate["MyStuff"] = otherStuff //String Array

            //updating profile information on login
            CleverTapAPI.getDefaultInstance(applicationContext)?.onUserLogin(profileUpdate)

            Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show()
        }

        CleverTapAPI.createNotificationChannel(
            applicationContext,"001","Demo","This will be a demo notification channel",
            NotificationManager.IMPORTANCE_MAX,true)

    }

//    override fun onPushPermissionResponse(accepted: Boolean) {
//        Log.i(ContentValues.TAG, "onPushPermissionResponse :  InApp---> response() called accepted=$accepted")
//        if (accepted) {
//            CleverTapAPI.createNotificationChannel(
//                applicationContext, "001", "Demo",
//                "This will be a demo notification channel", NotificationManager.IMPORTANCE_HIGH, true);
//        }
//    }

    override fun inboxDidInitialize() {
        TODO("Not yet implemented")
    }

    override fun inboxMessagesDidUpdate() {
        TODO("Not yet implemented")
    }

    override fun onDisplayUnitsLoaded(units: java.util.ArrayList<CleverTapDisplayUnit>?) {
        // you will get display units here
        // implement your logic to create your display views using these Display Units here
        for (i in 0 until units!!.size)
        {
            val unit = units[i]
            Log.d(TAG, "onDisplayUnitsLoaded: ${units[0].jsonObject.toString()}")
            //???
            //prepareDisplayView(unit)
        }
    }
}