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
            val taskDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val taskDao = taskDb.taskDao()
            idData.postValue(taskDao.insert(task))
            allData.postValue(taskDao.getAll())
            taskDb.close()
        }
        return idData
    }

    fun update(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val taskDao = taskDb.taskDao()
            taskDao.update(task)
            allData.postValue(taskDao.getAll())
            taskDb.close()
        }
    }

    fun updatePositions(tasks: List<TaskEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val taskDao = taskDb.taskDao()
            for (item in tasks)
                taskDao.updatePosition(item.id ?: 0, item.position)
            allData.postValue(taskDao.getAll())
            taskDb.close()
        }
    }

    fun delete(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val taskDao = taskDb.taskDao()
            allData.postValue(taskDao.getAll())
            taskDb.close()
        }
    }

    fun get(taskId: Long): LiveData<TaskEntity> {
        val taskData = MutableLiveData<TaskEntity>()
        viewModelScope.launch(Dispatchers.IO) {
            val taskDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val taskDao = taskDb.taskDao()
            taskData.postValue(taskDao.get(taskId))
            taskDb.close()
        }
        return taskData
    }

    fun getAll(): LiveData<List<TaskEntity>> {
        viewModelScope.launch(Dispatchers.IO) {
            val taskDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val taskDao = taskDb.taskDao()
            allData.postValue(taskDao.getAll())
            taskDb.close()
        }
        return allData
    }
}