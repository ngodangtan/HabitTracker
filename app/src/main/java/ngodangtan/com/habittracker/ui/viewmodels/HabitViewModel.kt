package ngodangtan.com.habittracker.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ngodangtan.com.habittracker.data.database.HabitDatabase
import ngodangtan.com.habittracker.data.models.Habit
import ngodangtan.com.habittracker.logic.repository.HabitRepository

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository:HabitRepository
    val getAllHabits : LiveData<List<Habit>>

    init {
        val habitDao = HabitDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)
        getAllHabits = repository.getAllHabits
    }
    fun addHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }
    fun updateHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(habit)
        }
    }
    fun deleteHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(habit)
        }
    }
    fun deleteAllHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHabits()
        }
    }

}