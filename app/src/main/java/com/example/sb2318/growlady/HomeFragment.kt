package com.example.sb2318.growlady

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class HomeFragment: Fragment(),View.OnClickListener{

    private lateinit var fiveCard:CardView
    private lateinit var sixCard:CardView
    private lateinit var sevenCard:CardView
    private lateinit var eightCard:CardView
    private lateinit var nineCard:CardView
    private lateinit var tenCard:CardView
    private lateinit var elevenCard:CardView
    private lateinit var twelveCard:CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fiveCard= view.findViewById(R.id.five_class_card)
        sixCard= view.findViewById(R.id.six_class_card)
        sevenCard= view.findViewById(R.id.seven_class_card)
        eightCard= view.findViewById(R.id.eight_class_card)
        nineCard= view.findViewById(R.id.nine_class_card)
        tenCard= view.findViewById(R.id.ten_class_card)
        elevenCard= view.findViewById(R.id.eleven_class_card)
        twelveCard= view.findViewById(R.id.twelve_class_card)


        fiveCard.setOnClickListener(this)
        sixCard.setOnClickListener(this)
        sevenCard.setOnClickListener(this)
        eightCard.setOnClickListener(this)
        nineCard.setOnClickListener(this)
        tenCard.setOnClickListener(this)
        elevenCard.setOnClickListener(this)
        twelveCard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.five_class_card-> startMain("Five")
            R.id.six_class_card->  startMain("Six")
            R.id.seven_class_card-> startMain("Seven")
            R.id.eight_class_card-> startMain("Eight")
            R.id.nine_class_card -> startMain("Nine")
            R.id.ten_class_card -> startMain("Ten")
            R.id.eleven_class_card-> startMain("Eleven")
            R.id.twelve_class_card-> startMain("Twelve")

        }
    }

    private fun startMain(data: String) {
        val intent= Intent(requireContext(),MainActivity::class.java)
        intent.putExtra("Data",data)
        startActivity(intent)
    }
}