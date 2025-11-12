package app;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Photo;
import model.PhotoSize;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import javax.xml.transform.Transformer;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log = Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

    public void downloadPhotoExamples() {

        photoDownloader.getPhotoExamples().subscribe(photoSerializer::savePhoto);
//        try {
//            List<Photo> downloadedExamples = photoDownloader.getPhotoExamples();
//            for (Photo photo : downloadedExamples) {
//                photoSerializer.savePhoto(photo);
//            }
//        } catch (IOException e) {
//            log.log(Level.SEVERE, "Downloading photo examples error", e);
//        }
    }

    public void downloadPhotosForQuery(String query) throws IOException {
//        photoDownloader.searchForPhotos(query).subscribe(photoSerializer::savePhoto,
//                error -> log.log(Level.SEVERE, "Error downloading photos for query: " + query, error));
        downloadProcessAndSavePhotos(photoDownloader.searchForPhotos(query));
    }

    public void downloadPhotosForMultipleQueries(List<String> queries) {
//        photoDownloader.searchForPhotos(queries).subscribe(photoSerializer::savePhoto,
//                error -> log.log(Level.SEVERE, "Error downloading photos for query: " + queries, error));
        downloadProcessAndSavePhotos(photoDownloader.searchForPhotos(queries));
    }


    private void downloadProcessAndSavePhotos(Observable<Photo> photoObservable) {
        photoObservable.compose(this::processPhotos)
                .subscribe(photoSerializer::savePhoto,
                        e -> log.log(Level.WARNING, "Could not download a photo", e)
                );
    }

    private Observable<Photo> processPhotos(Observable<Photo> photoObservable) {
        return photoObservable
                .filter(photoProcessor::isPhotoValid)
                .groupBy(PhotoSize::resolve)
                .flatMap(
                        group -> group.getKey() == PhotoSize.LARGE ?
                                group
                                        .observeOn(Schedulers.computation())
                                        .map(photoProcessor::convertToMiniature) :
                                group
                                        .buffer(5, TimeUnit.SECONDS)
                                        .flatMapIterable(o -> o));
    }


}
