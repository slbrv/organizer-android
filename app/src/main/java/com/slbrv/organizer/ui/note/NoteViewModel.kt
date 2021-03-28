package com.slbrv.organizer.ui.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.slbrv.organizer.Config
import com.slbrv.organizer.data.room.database.OrganizerDatabase
import com.slbrv.organizer.data.room.note.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val allData = MutableLiveData<List<NoteEntity>>()

    fun insert(noteEntity: NoteEntity): LiveData<Long> {
        val idData = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            idData.postValue(noteDao.insert(noteEntity))
            allData.postValue(noteDao.getAll())
        }
        return idData
    }

    fun update(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            noteDao.update(noteEntity)
            allData.postValue(noteDao.getAll())
        }
    }

    fun delete(noteEntity: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            noteDao.delete(noteEntity)
            allData.postValue(noteDao.getAll())
        }
    }

    fun get(noteId: Long): LiveData<NoteEntity> {
        val noteData = MutableLiveData<NoteEntity>()
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            noteData.postValue(noteDao.get(noteId))
        }
        return noteData
    }

    fun getAll(): LiveData<List<NoteEntity>> {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            allData.postValue(noteDao.getAll())
        }
        return allData
    }
}