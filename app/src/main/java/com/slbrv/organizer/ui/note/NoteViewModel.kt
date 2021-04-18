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

    fun insert(note: NoteEntity): LiveData<Long> {
        val idData = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO) {
            val noteDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val noteDao = noteDb.noteDao()
            idData.postValue(noteDao.insert(note))
            allData.postValue(noteDao.getAll())
            noteDb.close()
        }
        return idData
    }

    fun update(note: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val noteDao = noteDb.noteDao()
            noteDao.update(note)
            allData.postValue(noteDao.getAll())
            noteDb.close()
        }
    }

    fun delete(note: NoteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val noteDao = noteDb.noteDao()
            noteDao.delete(note)
            allData.postValue(noteDao.getAll())
            noteDb.close()
        }
    }

    fun get(noteId: Long): LiveData<NoteEntity> {
        val noteData = MutableLiveData<NoteEntity>()
        viewModelScope.launch(Dispatchers.IO) {
            val noteDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val noteDao = noteDb.noteDao()
            noteData.postValue(noteDao.get(noteId))
            noteDb.close()
        }
        return noteData
    }

    fun getAll(): LiveData<List<NoteEntity>> {
        viewModelScope.launch(Dispatchers.IO) {
            val noteDb = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build()
            val noteDao = noteDb.noteDao()
            allData.postValue(noteDao.getAll())
            noteDb.close()
        }
        return allData
    }
}