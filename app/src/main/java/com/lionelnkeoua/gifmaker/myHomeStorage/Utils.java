package com.lionelnkeoua.gifmaker.myHomeStorage;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.provider.MediaStore;


import com.lionelnkeoua.gifmaker.Model.Image;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by REYANSH on 4/8/2017.
 */

public class Utils {


    private static String DATA = MediaStore.Images.Media.DATA;
    private static String _ID = MediaStore.Images.Media._ID;
    private static String SIZE = MediaStore.Images.Media.SIZE;
    private static String TITLE = MediaStore.Images.Media.TITLE;
    private static String DATE_ADDED = MediaStore.Images.Media.DATE_ADDED;
    private static String DISPLAY_NAME = MediaStore.Images.Media.DISPLAY_NAME;
    private static String BUCKET_DISPLAY_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
    private static String DATE_MODIFIED = MediaStore.Images.Media.DATE_MODIFIED;


    private static Uri INTERNAL_CONTENT_URI = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
    private static Uri EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;


    private static final String[] INTERNAL_COLUMNS = new String[]{
            _ID,
            TITLE,
            SIZE,
            DATA,
            DATE_ADDED,
            DISPLAY_NAME,
            BUCKET_DISPLAY_NAME,
            DATE_MODIFIED,
            "\"" + INTERNAL_CONTENT_URI + "\""
    };
    private static final String[] EXTERNAL_COLUMNS = new String[]{
            _ID,
            TITLE,
            SIZE,
            DATA,
            DATE_ADDED,
            DISPLAY_NAME,
            BUCKET_DISPLAY_NAME,
            DATE_MODIFIED,
            "\"" + EXTERNAL_CONTENT_URI + "\""
    };


    public static ArrayList<Image> getImagesFromDevice(Context context, boolean internal, String searchString, int limit) {


        DataSetObserver mDataSetObserver = null;
        mDataSetObserver = new NotifyingDataSetObserver();

        ArrayList<Image> mImages = new ArrayList<>();
        String order_by;
        if (limit == 0) {
            order_by = DATE_ADDED + " DESC";
        } else {
            order_by = DATE_ADDED + " DESC" + " LIMIT " + limit;

        }
        String[] selectionArgs = null;
        String selection = null;
        if (searchString != null && searchString.length() > 0) {
            selection = "((_data LIKE ?) OR  (ARTIST LIKE ?)  OR (_DATA LIKE ?) OR (ALBUM LIKE ?))";
            selectionArgs = new String[]{"%" + searchString + "%"};
        }

        Uri CONTENT_URI;
        String[] COLUMNS;

        if (internal) {
            CONTENT_URI = INTERNAL_CONTENT_URI;
            COLUMNS = INTERNAL_COLUMNS;
        } else {
            CONTENT_URI = EXTERNAL_CONTENT_URI;
            COLUMNS = EXTERNAL_COLUMNS;
        }
        Cursor cursor = context.getContentResolver().query(
                CONTENT_URI,
                COLUMNS,
                selection,
                selectionArgs,
                order_by);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Image images = new Image();

                int int_ID = cursor.getInt(cursor.getColumnIndex(_ID));
                String itemUri = CONTENT_URI + "/" + cursor.getString(cursor.getColumnIndexOrThrow(_ID));
                File file = new File(cursor.getString(cursor.getColumnIndexOrThrow(DATA)));
                images.setFolderName(file.getParentFile().getName().toString());


                images.setUri(itemUri);
                images.setId(int_ID + "");
                images.setThumbPath(cursor.getString(cursor.getColumnIndexOrThrow(DATA)));


                mImages.add(images);
            } while (cursor.moveToNext());
        }

        cursor.registerDataSetObserver(mDataSetObserver);
        return mImages;
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private static class NotifyingDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
    public ArrayList<Image> getFolder(ArrayList<Image> mImages) {


        ArrayList<Image> mDataFiles = new ArrayList<>();
        mDataFiles.addAll(mImages);


        for (int i = 0; i < mDataFiles.size(); i++) {


            Image dataFiles = mDataFiles.get(i);
            ArrayList<Image> mDataVideos = new ArrayList<Image>();
            mDataVideos.add(dataFiles);

            for (int j = i + 1; j < mDataFiles.size(); j++) {
                if (dataFiles.getFolderName().equalsIgnoreCase(mDataFiles.get(j).getFolderName())) {
                    mDataVideos.add(mDataFiles.get(j));
                    mDataFiles.remove(j);
                    j--;
                }


            }

            dataFiles.setmImages(mDataVideos);
            mDataFiles.set(i, dataFiles);

        }


        return mDataFiles;
    }

}
