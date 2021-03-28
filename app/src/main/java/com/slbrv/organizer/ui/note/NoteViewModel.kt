package com.slbrv.organizer.ui.note

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.slbrv.organizer.Config
import com.slbrv.organizer.data.room.database.OrganizerDatabase
import com.slbrv.organizer.data.room.entity.note.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val allData = MutableLiveData<List<Note>>()

    fun insert(note: Note): LiveData<Long> {
        val idData = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            idData.postValue(noteDao.insert(note))
            allData.postValue(noteDao.getAll())
        }
        return idData
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            noteDao.update(note)
            allData.postValue(noteDao.getAll())
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            noteDao.delete(note)
            allData.postValue(noteDao.getAll())
        }
    }

    fun get(noteId: Long): LiveData<Note> {
        val noteData = MutableLiveData<Note>()
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

    fun getAll(): LiveData<List<Note>> {
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