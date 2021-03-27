package com.slbrv.organizer.ui.note

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.room.Room
import com.slbrv.organizer.Config
import com.slbrv.organizer.data.room.database.OrganizerDatabase
import com.slbrv.organizer.data.room.entity.note.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    fun insertNote(note: Note): LiveData<Long> {
        val data = MutableLiveData<Long>()
        viewModelScope.launch(Dispatchers.IO){
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            data.postValue(noteDao.insert(note))
        }
        return data
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.Default) {
            val noteDao = Room.databaseBuilder(
                getApplication(),
                OrganizerDatabase::class.java,
                Config.ORGANIZER_DATABASE_NAME
            ).build().noteDao()
            noteDao.update(note)
        }
    }
}