package com.example.bykeaapplication.Model;

// Model class representing data for your RecyclerView items
public class Model {

    // Fields to hold image resource ID and text for each item
    int image_view; // Resource ID of the image

    public String getTxt_1() {
        return txt_1;
    }

    public void setTxt_1(String txt_1) {
        this.txt_1 = txt_1;
    }

    String txt_1;   // Text associated with the item

    // Constructor to initialize the Model with image and text
    public Model(int image_view, String txt_1) {
        this.image_view = image_view;
        this.txt_1 = txt_1;
    }

    // Getter method for retrieving the image resource ID
    public int getImage_view() {
        return image_view;
    }

    // Setter method to update the image resource ID
    public void setImage_view(int image_view) {
        this.image_view=image_view;

    }
}