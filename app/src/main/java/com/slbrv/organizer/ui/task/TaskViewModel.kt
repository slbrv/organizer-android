package com.slbrv.organizer.ui.task

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Room
import com.slbrv.organizer.Config
import com.slbrv.organizer.data.room.database.OrganizerDatabase
import com.slbrv.organizer.data.room.task.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    
    private val allData = MutableLiveData<List<TaskEntity>>()

    fun insert(task: TaskEntity): LiveData<Long> {
        val idData = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().taskDao()
            idData.postValue(taskDao.insert(task))
            allData.postValue(taskDao.getAll())
        }
        return idData
    }

    fun update(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().taskDao()
            taskDao.update(task)
            allData.postValue(taskDao.getAll())
        }
    }

    fun delete(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().taskDao()
            taskDao.delete(task)
            allData.postValue(taskDao.getAll())
        }
    }

    fun get(taskId: Long): LiveData<TaskEntity> {
        val taskData = MutableLiveData<TaskEntity>()
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().taskDao()
            taskData.postValue(taskDao.get(taskId))
        }
        return taskData
    }

    fun getAll(): LiveData<List<TaskEntity>> {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().taskDao()
            allData.postValue(taskDao.getAll())
        }
        return allData
    }
}