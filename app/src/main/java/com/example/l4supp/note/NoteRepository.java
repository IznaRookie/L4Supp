package com.example.l4supp.note;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private com.example.l4supp.note.NoteDao noteDao;
    private LiveData<List<com.example.l4supp.note.Note>> allNotes;

    public NoteRepository(Application application) {
        com.example.l4supp.note.NoteDatabase database = com.example.l4supp.note.NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(com.example.l4supp.note.Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(com.example.l4supp.note.Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(com.example.l4supp.note.Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<com.example.l4supp.note.Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<com.example.l4supp.note.Note, Void, Void> {
        private com.example.l4supp.note.NoteDao noteDao;

        private InsertNoteAsyncTask(com.example.l4supp.note.NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(com.example.l4supp.note.Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<com.example.l4supp.note.Note, Void, Void> {
        private com.example.l4supp.note.NoteDao noteDao;

        private UpdateNoteAsyncTask(com.example.l4supp.note.NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(com.example.l4supp.note.Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<com.example.l4supp.note.Note, Void, Void> {
        private com.example.l4supp.note.NoteDao noteDao;

        private DeleteNoteAsyncTask(com.example.l4supp.note.NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(com.example.l4supp.note.Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private com.example.l4supp.note.NoteDao noteDao;

        private DeleteAllNotesAsyncTask(com.example.l4supp.note.NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}