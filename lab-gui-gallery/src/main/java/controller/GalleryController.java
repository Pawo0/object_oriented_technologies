package controller;


import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Gallery;
import model.Photo;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import util.PhotoDownloader;
import javafx.event.ActionEvent;


public class GalleryController {

    @FXML
    private TextField imageNameField;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<Photo> imagesListView;

    @FXML
    private TextField searchTextField;

    private Gallery galleryModel;

    @FXML
    public void initialize() {
        imagesListView.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(Photo item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(null);
                    setGraphic(null);
                } else{
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });

        imagesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
//                bindSelectedPhoto(newValue);
                imageNameField.textProperty().unbindBidirectional(oldValue.nameProperty());
            }
            bindSelectedPhoto(newValue);
        });
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
//        bindSelectedPhoto(gallery.getPhotos().get(0));
        imagesListView.setItems(gallery.getPhotos());
        imagesListView.getSelectionModel().select(0);

        imageView.imageProperty().bind(
            imagesListView.getSelectionModel().selectedItemProperty().flatMap(Photo::photoDataProperty)
        );
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        imageNameField.textProperty().bindBidirectional(selectedPhoto.nameProperty());
        imageView.imageProperty().bind(selectedPhoto.photoDataProperty());
    }
    
    public void onSearchButtonClicked(ActionEvent actionEvent) {
        var photoDownloader = new PhotoDownloader();
        galleryModel.clear();
        Disposable subscription = photoDownloader.searchForPhotos(searchTextField.getText())
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(photo -> galleryModel.addPhoto(photo));
    }

//    public void onSearchButtonClicked(ActionEvent actionEvent) {
//        searchButtonClicked(actionEvent);
//    }
}

