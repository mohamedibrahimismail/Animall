package com.example.animall.Product;

import com.example.animall.Data.Remote.Models.Product.Product;

public interface Communicator {
    public void HandleClicked(int index, Product product);
    public void like(String id);

}
