package ngodangtan.com.habittracker.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_habit_item.*
import ngodangtan.com.habittracker.R
import ngodangtan.com.habittracker.data.models.Habit
import ngodangtan.com.habittracker.ui.viewmodels.HabitViewModel
import ngodangtan.com.habittracker.utils.Calculations
import java.util.*

class CreateHabitItem : Fragment(R.layout.fragment_create_habit_item),
TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private  var title = ""
    private  var description = ""
    private  var drawableSelected = 0
    private  var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var habieViewModel: HabitViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habieViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        btn_confirm.setOnClickListener {
            addHabitToDB()
        }

        pickDateAndTime()

        drawableSelected()
    }

    private fun addHabitToDB(){
        title = et_habitTitle.text.toString()
        description = et_habitDescription.text.toString()

        timeStamp = "$cleanDate $cleanTime"

        if(!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)){
            val habit = Habit(0,title,description,timeStamp,drawableSelected)

            habieViewModel.addHabit(habit)
            Toast.makeText(context,"Habit created successfully",Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_createHabitItem_to_habitList)
        }else {
            Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawableSelected(){
        iv_fastFoodSelected.setOnClickListener {
            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_fastfood

            iv_smokingSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }
        iv_smokingSelected.setOnClickListener {
            iv_smokingSelected.isSelected = !iv_smokingSelected.isSelected
            drawableSelected = R.drawable.ic_smoking

            iv_teaSelected.isSelected = false
            iv_fastFoodSelected.isSelected = false
        }
        iv_teaSelected.setOnClickListener {
            iv_teaSelected.isSelected = !iv_teaSelected.isSelected
            drawableSelected = R.drawable.ic_tea

            iv_smokingSelected.isSelected = false
            iv_fastFoodSelected.isSelected = false
        }
    }

    private fun pickDateAndTime(){
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(),this,year,month,day).show()

        }
        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context,this,hour,minute,true).show()
        }
    }



    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX,monthX,yearX)
        tv_dateSelected.text = "Date: $cleanDate"
    }

    private fun getTimeCalendar(){
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getDateCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculations.cleanTime(p1,p2)
        tv_timeSelected.text = "Time: $cleanTime"
    }
}