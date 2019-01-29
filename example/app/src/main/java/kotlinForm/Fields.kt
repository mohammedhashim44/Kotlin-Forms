package kotlinForm

import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.mohammed.kotlintest.R
import kotlinx.android.synthetic.main.form_checkbox.view.*
import kotlinx.android.synthetic.main.form_edittext.view.*
import kotlinx.android.synthetic.main.form_radio.view.*
import kotlinx.android.synthetic.main.form_spinner.view.*


abstract class InputField(var name: String, var label: String, val validators: Array<Validator> = arrayOf()) {

    abstract fun getView(inflater: LayoutInflater): View
    abstract fun getInput(container: LinearLayout): Any
    open fun isValid(container: LinearLayout, data: MutableMap<String, Any>): Boolean {
        return true
    }
}

class BooleanField(name: String, label: String, validators: Array<Validator> = arrayOf()) :
        InputField(name, label, validators) {

    val layout = R.layout.form_checkbox
    override fun getView(inflater: LayoutInflater): View {
        val v = inflater.inflate(layout, null, false)
        v.form_checkbox_field.tag = this.name + "_input"
        v.form_checkbox_field.text = this.label
        return v
    }

    override fun getInput(container: LinearLayout): Any {
        val checkBox = container.findViewWithTag(this.name + "_input") as CheckBox
        return checkBox.isChecked
    }

}

class SpinnerField(name: String, label: String, var choices: Array<String>, validators: Array<Validator> = arrayOf()) :
        InputField(name, label, validators) {

    val layout = R.layout.form_spinner
    override fun getView(inflater: LayoutInflater): View {

        val parent = inflater.inflate(layout, null, false)
        val spinner = parent.findViewById(R.id.form_spinner_field) as Spinner
        val list = ArrayList<String>()

        for (c in this.choices)
            list.add(c)

        // Create dropDown adapter to display the choices and add it to the spinner
        val dataAdapter = ArrayAdapter<String>(inflater.context,
                android.R.layout.simple_spinner_item, list)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter

        parent.form_spinner_field.tag = this.name + "_input"
        parent.form_spinner_label.text = this.label

        return parent
    }

    override fun getInput(container: LinearLayout): Any {
        val spinner = container.findViewWithTag(this.name + "_input") as Spinner
        return spinner.selectedItem
    }

}


class RadioField(name: String, label: String, var choices: Array<String>, validators: Array<Validator> = arrayOf()) :
        InputField(name, label, validators) {

    val layout = R.layout.form_radio
    override fun getView(inflater: LayoutInflater): View {

        val parent = inflater.inflate(layout, null, false)
        for (choice in this.choices) {
            val child = RadioButton(inflater.context)
            child.text = choice
            parent.form_radio_group.addView(child)
        }
        // Make the first element selected
        parent.form_radio_group.check(parent.form_radio_group.getChildAt(0).id)
        parent.form_radio_group.tag = this.name + "_input"
        parent.form_radio_label.text = this.label

        return parent
    }

    override fun getInput(container: LinearLayout): Any {
        val radioGroup = container.findViewWithTag(this.name + "_input") as RadioGroup
        val selectedButtonId = radioGroup.checkedRadioButtonId
        val selectedButton = container.findViewById(selectedButtonId) as RadioButton
        return selectedButton.text
    }
}

open class TextField(name: String, label: String, validators: Array<Validator> = arrayOf()) :
        InputField(name, label, validators) {

    val layout = R.layout.form_edittext
    var FIELD_INPUT_TYPE: Int = 1


    override fun getView(inflater: LayoutInflater): View {

        val parent = inflater.inflate(layout, null, false)

        // The FIELD_INPUT_TYPE in the EditText controls how the input is displayed
        // and entered , so for the the TextField it will be normal = 1 ,
        // and for the PasswordField the input will be hidden = 129 .

        parent.form_edittext_field.inputType = this.FIELD_INPUT_TYPE

        parent.form_edittext_field.tag = this.name + "_input"
        parent.form_edittext_error.tag = this.name + "_error"
        parent.form_edittext_label.text = this.label

        return parent
    }

    override fun getInput(container: LinearLayout): String {
        val editText = container.findViewWithTag(this.name + "_input") as EditText
        val data = editText.editableText.toString()
        return data
    }

    override fun isValid(container: LinearLayout, data: MutableMap<String, Any>): Boolean {
        var valid = true
        clearError(container)

        for (validator in this.validators) {
            validator.data = data
            if (!validator.isValid(this.name)) {
                setError(container, validator.error_message)
                valid = false
            }
        }
        return valid
    }

    fun setError(container: LinearLayout, error: String) {
        val textViewError = container.findViewWithTag(this.name + "_error") as TextView
        textViewError.text = error
    }

    fun clearError(container: LinearLayout) {
        try {
            val textViewError = container.findViewWithTag(this.name + "_error") as TextView
            textViewError.text = ""
        } catch (e: Exception) {
        }
    }

}

class PasswordField(name: String, label: String, validators: Array<Validator> = arrayOf()) :
        TextField(name, label, validators) {
    init {
        this.FIELD_INPUT_TYPE = 129
    }
}

