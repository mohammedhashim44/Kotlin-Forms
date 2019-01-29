package kotlinForm

abstract class Validator(var error_message: String){
    var data : Map<String,Any> = mutableMapOf()
    abstract fun isValid(field: String) : Boolean
}

class DataRequired( error_message: String = "This field is required"): Validator(error_message){
    override fun isValid(field: String) : Boolean{
        if(data[field] == null || data[field].toString().isEmpty())
            return false
        return true
    }
}

class EqualTo(error_message: String = "This field must equal " , var target_field: String): Validator(error_message + target_field){
    override fun isValid(field: String): Boolean {
        if(data[field] != data[target_field])
            return false
        return true
    }

}

class Email(error_message: String = "Please enter valid email"): Validator(error_message){

    override fun isValid(field: String) : Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val email = (data[field] as String).trim()
        val isEmail = email.matches(Regex(emailPattern)) && email.isNotEmpty()
        if(! isEmail)
            return false
        return true
    }

}

fun getMessage(min : Int , max: Int) : String{
    var mess = ""
    if (min != -1){
        mess += "Min length is $min "
    }
    if(max != -1){
        mess += "Max length is $max"
    }
    return mess
}

class Length(var max :Int = -1 , var min : Int = -1, error_message: String = getMessage(min,max)): Validator(error_message){
    override fun isValid(field: String) : Boolean {
        val len = (data[field] as String).length
        if (max != -1 && len > max )
            return false
        if (min != -1 && len < min)
            return false
        return true
    }
}


