package com.example.animall.Data.Local.AddToCard;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.animall.Data.Remote.Models.Product.Product;

import java.util.List;

public class FavoriteRepository {

    private FavoriteDao noteDao;
    private LiveData<List<Product>> allNotes;
    private LiveData<Product> getFavoriteMovie;

    public FavoriteRepository(Application application){
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }



    public void insert(Product note){
        new InsertNoteAsynTask(noteDao).execute(note);
    }

    public void update(Product note){
        new UpdateNoteAsynTask(noteDao).execute(note);
    }

    public void delete(Product note){
        new DeleteNoteAsynTask(noteDao).execute(note);
    }

    public void deleteall(){
        new DeleteAllNoteAsynTask(noteDao).execute();
    }

    public LiveData<List<Product>> getAllNotes(){
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        return allNotes;
    }


    public LiveData<Product> getNote(long id){
        getFavoriteMovie = noteDao.getNote(id);
        return getFavoriteMovie;

    }


    private static class InsertNoteAsynTask extends AsyncTask<Product, Void, Void> {

        private FavoriteDao noteDao;

        private InsertNoteAsynTask(FavoriteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Product... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsynTask extends AsyncTask<Product, Void, Void> {

        private FavoriteDao noteDao;

        private UpdateNoteAsynTask(FavoriteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Product... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsynTask extends AsyncTask<Product, Void, Void> {

        private FavoriteDao noteDao;

        private DeleteNoteAsynTask(FavoriteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Product... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class DeleteAllNoteAsynTask extends AsyncTask<Void, Void, Void> {

        private FavoriteDao noteDao;

        private DeleteAllNoteAsynTask(FavoriteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

/*
    private static class getFavoriteAsynTask extends AsyncTask<Integer,Void,Void>{

        private FavoriteDao noteDao;

        private getFavoriteAsynTask(FavoriteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Integer... notes) {
            noteDao.getNote(notes[0]);
            return null;
        }
    }
*/
    /*
    private static class PopulatedAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        private PopulatedAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Favorite("Title 1","Description 1 ",1));
            noteDao.insert(new Favorite("Title 2","Description 2 ",2));
            noteDao.insert(new Favorite("Title 3","Description 3 ",3));

            return null;
        }
    }
*/
}
