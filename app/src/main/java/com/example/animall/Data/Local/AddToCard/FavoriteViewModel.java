package com.example.animall.Data.Local.AddToCard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.animall.Data.Remote.Models.Product.Product;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteRepository repository;
    private LiveData<List<Product>> allNotes;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Product note){

        repository.insert(note);
    }

    public void update(Product note){
        repository.update(note);
    }

    public void delete(Product note){
        repository.delete(note);
    }

    public void deleteAllNote(){
        repository.deleteall();
    }

    public LiveData<List<Product>> getAllNotes(){
        return allNotes;
    }

    public LiveData<Product> getFavoriteMovie(Long id){ return repository.getNote(id);}

}
