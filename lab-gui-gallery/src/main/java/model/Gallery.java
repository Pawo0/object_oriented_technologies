package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gallery {

//    private final List<Photo> photos = new ArrayList<>();

    private final ObservableList<Photo> photos = FXCollections.observableArrayList();

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

//    public List<Photo> getPhotos() {
//        return photos;
//    }

    public ObservableList<Photo> getPhotos() {
//        return FXCollections.observableList(photos);
        return photos;
    }

    public void clear() {
        photos.clear();
    }
}
