package com.example.animall.Data.Local.AddToCard;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.animall.Data.Remote.Models.Product.Product;

@Database(entities = {Product.class},version = 2,exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;

    public abstract FavoriteDao noteDao();

    public static synchronized FavoriteDatabase getInstance(Context context){

        if(instance==null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();

        }

        return instance;
    }

    private static Callback roomcallback = new Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private FavoriteDao noteDao;

        private PopulateDbAsyncTask(FavoriteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
          /*  noteDao.insert(new Favorite("Title 1","Description 1",1));
            noteDao.insert(new Favorite("Title 2","Description 2",2));
            noteDao.insert(new Favorite("Title 3","Description 3",3));
*/
            return null;

        }
    }

}
