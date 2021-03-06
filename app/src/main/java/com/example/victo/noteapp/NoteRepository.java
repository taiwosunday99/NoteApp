package com.example.victo.noteapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {


    private NoteDao mNoteDao;
    private LiveData<List<NoteEntry>> mAllNotes;

    NoteRepository(Application application) {
        NoteAppDatabase db = NoteAppDatabase.getInstance(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    LiveData<List<NoteEntry>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(NoteEntry note) {
        new insertAsyncTask(mNoteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<NoteEntry, Void, Void> {

        private NoteDao mAsyncTaskDao;

        insertAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteEntry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
