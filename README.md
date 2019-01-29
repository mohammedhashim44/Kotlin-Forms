# Kotlin-Forms
Small package to make dealing with forms and verification for easy and simple for android developers 

## How to setup
- Clone the repo 
- Copy `package/kotlinForm` to `yourApp/app/src/main/java/`
- Copy the all the other .xml layout in `package/` to `yourApp/app/src/main/res/layout/` folder .
- Open `Fields.kt` and `Form.kt` and edit the import statements to match your project .


## How it works 
Let's see an example. 
Let's say you want to make a form with these fields: email, password,  confirm  password, gender, country, and a checkbox for enabling notifications.

First, you will define the form in your activity class 


```kotlin
class MainActivity : AppCompatActivity(){
...
lateinit var form : Form
...
```

And then in onCreate method you will define the fields , and initiate the form with these fields 


```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
...
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
...
```

Each filed have a name , label , and validators. 
- The name is the name you will use when you get the data . 
- The label is the string the will be displayed in front of the field . 
- The validators validate the input when the submit button is clicked.  

--- 

So , for the email field we have
```kotlin
name="email" , label="Email"
```
and validators are `DataReuired()` and `Email()` 

```kotlin
DataRequired()
```
If the field is empty the form won't validate and an error message will be displayed .

```kotlin
Email()
```
If the field's input don't match email pattern the form won't validate and an error message will be displayed .

---

For the password we have `PasswordField` , it's the same as the `TextField` except the input will be hidden .
In the `PasswordField` validators we have `Lenght()` . 
```kotlin
Length(min,max)
```
If the field's input characters count is greater than max or less than min ,the form won't validate and an error message will be displayed .

Note : you can specify either min and max or just one of them .

---

For the password confirm we have PasswordField , with one validator, EqualTo() 
```kotlin
EqualTo(target_field)
```
Target field is the name of the other field , which value must equal the current field to validate the form .
So, if the password field and password_confirm field don't match , a validation error will be displayed .

--- 

For the gender field we have RadioFeild() 
The RadioField must have choices , which is array of strings to be displayed as radio buttons . 
So we have 
```kotlin
choices = arrayOf("Male","Female")
```
--- 

For the country we have SpinnerField , which is the same as the RadioField except it will display the choices as drop down menu

---

For the notification , we have BooleanField .
Which just have a name and a label .

---

Then we initiate the form with the current activity, the fields and a LinearLayout from the MainActivity layout 
Note : It must be LinearLayout

```kotlin
form = Form(this, main_activity_form_container, fields)
```

Then we define the function will be called when the form is verified 

```kotlin
        form.onValidationFunction = ::login
```

Note : The function must be UNIT and have no args

Next , we define the function . 
```kotlin
  private fun login(){
        toast(form.data.toString())
    }
```

The function just toast the data when the form verified , and here you will edit the function to do what ever you want

Note : to get to the data you do this 
```kotlin
var email = form.data["email"] 
```

## Result 
