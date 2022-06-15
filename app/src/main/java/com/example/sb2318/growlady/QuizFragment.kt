package com.example.sb2318.growlady

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson

class QuizFragment : Fragment(),View.OnClickListener {

    private lateinit var filename: String
    private val args:QuizFragmentArgs by navArgs()
    private lateinit var physicsPlay: Button
    private lateinit var chemPlay: Button
    private lateinit var mathPlay: Button
    private lateinit var historyPlay: Button
    private lateinit var geoPlay: Button
    private lateinit var bioPlay: Button
    private lateinit var highscoreText:TextView
    private var highScore =0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_quiz,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        physicsPlay= view.findViewById(R.id.play_physics)
        chemPlay= view.findViewById(R.id.play_chemistry)
        mathPlay= view.findViewById(R.id.play_math)
        historyPlay= view.findViewById(R.id.play_history)
        geoPlay= view.findViewById(R.id.play_geo)
        bioPlay= view.findViewById(R.id.play_bio)
        highscoreText= view.findViewById(R.id.text_high_score)
        loadHighScore()

        physicsPlay.setOnClickListener(this)
        chemPlay.setOnClickListener(this)
        mathPlay.setOnClickListener(this)
        historyPlay.setOnClickListener(this)
        geoPlay.setOnClickListener(this)
        bioPlay.setOnClickListener(this)

        val score= args.highScore

        highScore= if(score!!>highScore)
                      score
                   else
                       highScore
        highscoreText.setText("Highscore : $highScore")
       loadPreferences(highScore)


    }


    private fun loadPreferences(highScore: Int) {
        val preferences= requireContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor= preferences.edit()

        editor.putInt(KEY_HIGH_SCORE, highScore)

        editor.apply()
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.play_physics -> {
                filename= "physics.json"
            }
            R.id.play_chemistry -> {
                filename= "chemistry.json"
            }
            R.id.play_math->{
                  filename="math.json"
            }

            R.id.play_history->{
                filename="history.json"
            }

            R.id.play_geo->{
                filename="geography.json"
            }

            R.id.play_bio->{
                filename="bio.json"
            }
            else->{
                filename=""
            }

        }
        val actions= QuizFragmentDirections.actionQuizFragmentToQuestionsFragment(filename)
        findNavController().navigate(actions)
    }

    private fun loadHighScore(){
        val preferences= requireContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        highScore= preferences.getInt(KEY_HIGH_SCORE,0)
        highscoreText.setText("Highscore : $highScore")
    }

    companion object{
        const val SHARED_PREFS="SharedPrefs"
        const val KEY_HIGH_SCORE= "keyHighscore"
    }
}