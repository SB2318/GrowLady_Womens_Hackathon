package com.example.sb2318.growlady

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class QuestionsFragment: Fragment(R.layout.fragment_questions) {

    private lateinit var questionsList:ArrayList<Question>
    private val args:QuestionsFragmentArgs by navArgs()
    private lateinit var fileName:String

    private lateinit var scoreView: TextView
    private lateinit var highScoreText: TextView
    private lateinit var question_countView: TextView

    private lateinit var questionText: TextView

    private lateinit var questionTimer: TextView
    private lateinit var confirmBtn: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var  radioOption1: RadioButton
    private lateinit var radioOption2: RadioButton
    private lateinit var radioOption3: RadioButton
    private lateinit var radioOption4:RadioButton

    private lateinit var  textColorDefaultRb: ColorStateList
    private lateinit var  textColorDefaultCd: ColorStateList
    private var questionCounter =0
    private var questionTotalCount by Delegates.notNull<Int>()
    private lateinit var currQuestion:Question
    private var score =0
    private var answered by Delegates.notNull<Boolean>()
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis:Long=0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_questions,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        if(savedInstanceState==null) {

            questionsList = loadQuestionsFromAssets() as java.util.ArrayList<Question>

            questionTotalCount = 10
            Collections.shuffle(questionsList)
            showNextQuestions(view)
        }
        else{
            questionsList= savedInstanceState.getParcelableArrayList<Question>(
                KEY_QUESTION_LIST)!!

            if(questionsList==null){
                findNavController().popBackStack()
            }
            questionTotalCount = questionsList.size
            questionCounter= savedInstanceState.getInt(KEY_QUESTION_COUNT)
            currQuestion= questionsList[questionCounter-1]
            score= savedInstanceState.getInt(KEY_SCORE)
            timeLeftInMillis= savedInstanceState.getLong(KEY_MILLIS_IN_LEFT)
            answered= savedInstanceState.getBoolean(KEY_ANSWERED)

            if(!answered){
                startCountDown(view)
            }
            else{
                updateCountDownText()
                showSolution(view)
            }
        }

        confirmBtn.setOnClickListener {
            if(!answered){

                if(radioOption1.isChecked || radioOption2.isChecked || radioOption3.isChecked||radioOption4.isChecked){
                    checkAnswer(view)
                }
                else{
                    Toast.makeText(requireContext(),"Please check one!", Toast.LENGTH_LONG).show()
                }
            }else{
                showNextQuestions(view)
            }
        }
    }

    private fun startCountDown(view: View) {

        countDownTimer=  object: CountDownTimer(timeLeftInMillis.toLong(),1000){
            override fun onTick(millisUntilFinished: Long) {

                timeLeftInMillis= millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {

                timeLeftInMillis=0
                updateCountDownText()
                checkAnswer(view)
            }

        }.start()
    }

    private fun checkAnswer(view: View) {
        answered= true
        countDownTimer.cancel()
        val rbSelected= view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        val answer= radioGroup.indexOfChild(rbSelected)+1
        val answerNr= answer.toString()

        if(answerNr.equals(currQuestion.answerNr)){
            score++;
            scoreView.setText("Score: $score")
        }
        showSolution(view)
    }

    private fun showSolution(view:View) {

        radioOption1.setTextColor(Color.RED)
        radioOption2.setTextColor(Color.RED)
        radioOption3.setTextColor(Color.RED)


        when(currQuestion.answerNr){

            "1"-> radioOption1.setTextColor(Color.GREEN)
            "2"-> radioOption2.setTextColor(Color.GREEN)
            "3"-> radioOption3.setTextColor(Color.GREEN)
            "4"-> radioOption4.setTextColor(Color.GREEN)
        }
        questionText.setText("Answer ${currQuestion.answerNr} is correct.")

        if(questionCounter<questionTotalCount){
            confirmBtn.setText("Next")
        }else{
            confirmBtn.setText("Finish")
        }
    }

    private fun updateCountDownText() {

        val minutes= (timeLeftInMillis/1000)/60
        val seconds= (timeLeftInMillis/1000)%60

        val formatedTime= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds)

        questionTimer.setText(formatedTime)

        if(timeLeftInMillis<10000){
            questionTimer.setTextColor(Color.RED)
        }else{
            questionTimer.setTextColor(textColorDefaultCd)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_SCORE,score)
        outState.putInt(KEY_QUESTION_COUNT,questionCounter)
        outState.putLong(KEY_MILLIS_IN_LEFT,timeLeftInMillis)
        outState.putBoolean(KEY_ANSWERED,answered)
        outState.putParcelableArrayList(KEY_QUESTION_LIST,questionsList)
    }

    override fun onDestroy() {
        super.onDestroy()

        if(countDownTimer!=null){
            countDownTimer.cancel()
        }
    }

    private fun initViews(view: View) {

        scoreView= view.findViewById(R.id.text_view_score)
        question_countView= view.findViewById(R.id.text_view_question_count)
        questionText= view.findViewById(R.id.text_view_question)
        questionTimer= view.findViewById(R.id.text_view_timer)
//        highScoreText= view.findViewById(R.id.text_high_score)
        confirmBtn= view.findViewById(R.id.confirm_next_btn)
        radioGroup= view.findViewById(R.id.radioGroup)
        radioOption1= view.findViewById(R.id.option_1)
        radioOption2= view.findViewById(R.id.option_2)
        radioOption3= view.findViewById(R.id.option_3)
        radioOption4= view.findViewById(R.id.option_4)
        fileName= args.fileName
        questionsList= loadQuestionsFromAssets() as ArrayList<Question>


        textColorDefaultRb = radioOption1.textColors
        textColorDefaultCd = question_countView.textColors

    }

    private fun finishQuiz() {
        val actions= QuestionsFragmentDirections.actionQuestionsFragmentToQuizFragment(score)
        findNavController().navigate(actions)
       // findNavController().popBackStack()
    }

    private fun showNextQuestions(view: View) {

        radioOption1.setTextColor(textColorDefaultRb)
        radioOption2.setTextColor(textColorDefaultRb)
        radioOption3.setTextColor(textColorDefaultRb)
        radioOption4.setTextColor(textColorDefaultRb)
        radioGroup.clearCheck()

        if(questionCounter<questionTotalCount){

            currQuestion= questionsList[questionCounter]

            questionText.setText(currQuestion.name)
            radioOption1.setText(currQuestion.option1)
            radioOption2.setText(currQuestion.option2)
            radioOption3.setText(currQuestion.option3)
            radioOption4.setText(currQuestion.option4)

            questionCounter++
            question_countView.setText("Question: "+questionCounter+"/"+questionTotalCount)

            answered= false

            confirmBtn.setText("Confirm")
            timeLeftInMillis= COUNT_DOWN_MILLIS.toLong()
            view?.let { startCountDown(it) }

        }
        else{
            finishQuiz()
        }
    }


    private fun loadQuestionsFromAssets(): List<Question> {

        // create a stream to open the json file
        val inputStream= requireContext().assets.open(fileName)
        // find the file size and store it
        val size:Int= inputStream.available()
        // create a byte array
        val buffer= ByteArray(size)
        // read the data and stored it into the buffer
        inputStream.read(buffer)
        // Now close the input stream
        inputStream.close()

        // convert the buffer into string
        val json= String(buffer,Charsets.UTF_8) // JSON store the data in this format

        val gson= Gson()

        val dataArray=  gson.fromJson(json,Array<Question>::class.java)
        // return null
        return dataArray.toList()
    }

    companion object{
        const val EXTRA_SCORE="extra-score"
        const val COUNT_DOWN_MILLIS= 30000
        const val KEY_SCORE= "keyScore"
        const val KEY_MILLIS_IN_LEFT= "keyMillisInLeft"
        const val KEY_ANSWERED= "keyAnswered"
        const val KEY_QUESTION_LIST= "keyQuestionList"
        const val KEY_QUESTION_COUNT= "keyQuestionCount"

    }
}