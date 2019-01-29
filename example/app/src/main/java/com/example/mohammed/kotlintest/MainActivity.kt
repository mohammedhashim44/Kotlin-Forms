package com.example.mohammed.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import kotlinForm.*

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    lateinit var form : Form
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define the fields for the form
        val fields = arrayListOf(
                TextField(name = "email", label = "Email",
                        validators = arrayOf(DataRequired() , Email())),
                PasswordField(name = "password", label = "Password",
                        validators = arrayOf(DataRequired() , Length(min=8))),
                PasswordField(name = "password_confirm", label = "Password Confirm",
                        validators = arrayOf(EqualTo(target_field = "password"))) ,
                RadioField(name = "gender",label = "Gender" , choices = arrayOf("Male","Female")) ,
                SpinnerField(name = "country",label = "Country",choices = arrayOf("USA","Canada","UK")) ,
                BooleanField(name = "notifications",label = "Send Notifications")
        )

        // Initiate the form
        form = Form(this, main_activity_form_container, fields)

        // Set the function to be called the the form is verified , it must be UNIT and have no args
        form.onValidationFunction = ::login
    }

    private fun login(){
        // To get to the data just call the filed name from the Map ,
        // example : var email = form.data["email"] 
        toast(form.data.toString())
    }

}
