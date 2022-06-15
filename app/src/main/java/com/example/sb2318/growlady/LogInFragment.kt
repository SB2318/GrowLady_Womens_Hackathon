package com.example.sb2318.growlady

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LogInFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var gotoRegister: Button
    private var valid = true
    private lateinit var auth:FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       // super.onViewCreated(view, savedInstanceState)
        email = view.findViewById(R.id.loginEmail)
        password = view.findViewById(R.id.loginPassword)
        loginBtn = view.findViewById(R.id.loginBtn)
        gotoRegister = view.findViewById(R.id.gotoRegister)
        auth= FirebaseAuth.getInstance()
        fireStore= FirebaseFirestore.getInstance()


        gotoRegister.setOnClickListener {
            // navigate to register fragment
            findNavController().navigate(R.id.action_logInFragment_to_registrationFragment)
        }

        loginBtn.setOnClickListener {

            checkField(email)
            checkField(password)

            if(valid){

                auth.signInWithEmailAndPassword(email.text.toString().trim(),password.text.toString().trim())
                    .addOnSuccessListener {

                        // Send the admin data to
                        // Check user access level
                         val data= it.user?.let { it1 -> checkUserAccess(it1.uid) }
                        val action= data?.let { it1 ->
                            LogInFragmentDirections.actionLogInFragmentToHomeFragment(
                                it1
                            )
                        }

                        if (action != null) {
                            findNavController().navigate(action)
                        }
                        // Show the toast
                        Toast.makeText(requireContext(),"Logged in",Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(),"Log in failed${it.localizedMessage}",Toast.LENGTH_LONG).show()
                    }
            }
        }


    }

    private fun checkUserAccess(uid: String):String {

        val docRef= fireStore.collection("Users").document(uid)
        var data="0"
        // extract data from the document
        docRef.get().addOnSuccessListener {
            // Create a HashMap and extract the data using getData() method
             data= it.data?.get("isAdmin").toString()
        }
       return data
    }

    override fun onStart() {
        super.onStart()

        // Here we checking whether our user is logged in or not.
        if(FirebaseAuth.getInstance().currentUser!=null){
            // navigate them to Home Fragment
            val data= FirebaseAuth.getInstance().currentUser?.let { checkUserAccess(it.uid) }
            val action= LogInFragmentDirections.actionLogInFragmentToHomeFragment(data.toString())

            findNavController().navigate(action)
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