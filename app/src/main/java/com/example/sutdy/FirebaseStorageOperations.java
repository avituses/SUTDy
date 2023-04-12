package com.example.sutdy;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.IOException;

public class FirebaseStorageOperations {
    StorageReference mainStorageReference;
    /** plan your Storage Database, then instantiate References to it */
    FirebaseStorageOperations(){
        mainStorageReference = FirebaseStorage.getInstance().getReference();
    }
    void displayPhoto(ImageView imageView, Context context, String fileName){
        StorageReference storageReference = mainStorageReference.child(fileName);
        FireBaseUtils.downloadToImageView(context, storageReference, imageView);

    }

    // TODO 14.4b takes a URI and uploads it to Firestore
    public String uploadUriToStorage(Context context, Uri photoUri) throws IOException {
        String filename = FireBaseUtils.getFileName(context, photoUri);
        StorageReference imageStorageReference = mainStorageReference.child(filename);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(  context.getContentResolver(), photoUri);
        FireBaseUtils.uploadImageToStorage(context, imageStorageReference, bitmap);

        return filename;
    }


}
