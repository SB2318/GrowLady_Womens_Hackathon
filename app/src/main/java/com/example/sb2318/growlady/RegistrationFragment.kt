package com.example.sb2318.growlady

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class RegistrationFragment: Fragment() {

    private lateinit var auth:FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore
   private lateinit  var fullName: EditText
   private lateinit var email:EditText
   private lateinit var password:EditText
   private lateinit var phone:EditText
   private lateinit var isTeacher:CheckBox
   private lateinit var registerBtn: Button
   private lateinit var goToLogin:Button
   private var valid = true
    private lateinit var isAdmin:String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)


        fullName = view.findViewById(R.id.registerName)
        email = view.findViewById(R.id.registerEmail)
        password = view.findViewById(R.id.registerPassword)
        phone = view.findViewById(R.id.registerPhone)
        registerBtn = view.findViewById(R.id.registerBtn)
        goToLogin = view.findViewById(R.id.gotoLogin)
        isTeacher= view.findViewById(R.id.isTeacher)
        auth= FirebaseAuth.getInstance()
        fireStore= FirebaseFirestore.getInstance()

        isAdmin= if(isTeacher.isChecked)
                   "1"
                 else
                     "0"

         registerBtn.setOnClickListener {
             checkField(email)
             checkField(password)
             checkField(phone)
             checkField(fullName)

             if(valid){
                 // Start user registration process
                 auth.createUserWithEmailAndPassword(email.text.toString().trim(),password.text.toString().trim())
                     .addOnSuccessListener {
                         val currentUser= auth.currentUser
                         val docRef= currentUser?.let { it1 -> fireStore.collection("Users").document(it1.uid) }

                         val userInfo= HashMap<String,Any>()
                         userInfo.put("FullName",fullName.text.toString())
                         userInfo.put("Email",email.text.toString())
                         userInfo.put("Phone Number",phone.text.toString())

                         // if the user is admin
                         userInfo.put("isAdmin",isAdmin)

                         docRef?.set(userInfo)

                         Toast.makeText(requireContext(),"Account created",Toast.LENGTH_LONG).show()
                         // find the navcontroller and navigate to login activity
                         findNavController().navigate(R.id.action_registrationFragment_to_logInFragment)
                         // findNavController().popBackStack()
                     }
                     .addOnFailureListener {
                         Toast.makeText(requireContext(),"Failed to create account",Toast.LENGTH_LONG).show()
                     }
             }
         }

        goToLogin.setOnClickListener {
            // navigate to login fragment
            findNavController().navigate(R.id.action_registrationFragment_to_logInFragment)
        }

    }

    fun checkField(textField: EditText): Boolean {
        if (textField.text.toString().isEmpty()) {
            textField.error = "Error"
            valid = false
        } else {
            valid = true
        }
        return valid
    }
}