package kotlinForm

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.form_submit.view.*
import com.example.mohammed.kotlintest.R

open class Form(private val activity: Activity, val container: LinearLayout, private val fields : ArrayList<out InputField>) {
    // Define the layout for submit button
    private val formSubmitButtonLayout = R.layout.form_submit

    // Data variable for saving form when submitting
    var data = mutableMapOf<String, Any>()
    var onValidationFunction = ::toastValid
    init {
        val views = formViews()
        addViews(views)
    }
    private fun formViews(): ArrayList<View> {
        val views = ArrayList<View>()
        val inflater = LayoutInflater.from(activity.applicationContext)
        for (field in fields)
            views.add(field.getView(inflater))

        val submitButtonLayout = inflater.inflate(formSubmitButtonLayout, null, false)
        submitButtonLayout.form_submit_button.setOnClickListener {
            onSubmit()
        }
        views.add(submitButtonLayout)
        return views
    }

    private fun addViews(views: ArrayList<View>) {
        for (v in views)
            container.addView(v)
    }

    private fun onSubmit(){
        updateData()
        if(isValid())
            onValidationFunction()
    }

    private fun toastValid(){
        Toast.makeText(activity,"Valid",Toast.LENGTH_SHORT).show()
    }

    private fun updateData()  {
        data.clear()
        for (field in fields)
            data[field.name] = field.getInput(container)
    }

    private fun isValid(): Boolean {
        var valid = true
        for (field in fields)
            if (! field.isValid(container, data))
                valid = false
        return valid
    }


}


