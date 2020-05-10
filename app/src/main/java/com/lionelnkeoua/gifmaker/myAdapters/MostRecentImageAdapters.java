package com.lionelnkeoua.gifmaker.myAdapters;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lionelnkeoua.gifmaker.myHomeStorage.CursorRecyclerViewAdapter;
import com.lionelnkeoua.gifmaker.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class MostRecentImageAdapters extends CursorRecyclerViewAdapter<MostRecentImageAdapters.ViewHolder> {

    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MostRecentImageAdapters(Context context, Cursor cursor) {
        super(context, cursor, "");
        mContext = context;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor, final int position) {

        viewHolder.setData(cursor);
        int int_ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        getThumbnail(int_ID, viewHolder.imgSd);


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_storeage_image, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(ViewHolder item, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSd;
        private TextView txtSd;


        public ViewHolder(View v) {
            super(v);


            imgSd = (ImageView) v.findViewById(R.id.imgSd);
            txtSd = (TextView) v.findViewById(R.id.txtSd);

        }

        public void setData(Cursor cursor) {
            int uriIndex = getUriIndex(getCursor());
            String itemUri = getCursor().getString(uriIndex) + "/" + getCursor().getString(getCursor().getColumnIndexOrThrow(MediaStore.Video.Media._ID));
            txtSd.setText(itemUri);

        }
    }

    private int getUriIndex(Cursor c) {
        int uriIndex;
        String[] columnNames = {
                MediaStore.Video.Media.INTERNAL_CONTENT_URI.toString(),
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString()
        };

        int i = 0;
        for (String columnName : Arrays.asList(columnNames)) {
            i++;
            Log.e(":Loadin", i + "");
            uriIndex = c.getColumnIndex(columnName);
            if (uriIndex >= 0) {
                return uriIndex;
            }
            // On some phones and/or Android versions, the column name includes the double quotes.
            uriIndex = c.getColumnIndex("\"" + columnName + "\"");
            if (uriIndex >= 0) {
                return uriIndex;
            }
        }
        return -1;
    }

    private Bitmap getThumbnail(int id, ImageView imageView) {
        final String thumb_DATA = MediaStore.Video.Thumbnails.DATA;
        final String thumb_IMAGE_ID = MediaStore.Video.Thumbnails.VIDEO_ID;
        final Uri thumbUri = MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI;
        String[] thumbColumns = {thumb_DATA, thumb_IMAGE_ID};
        CursorLoader thumbCursorLoader = new CursorLoader(
                mContext,
                thumbUri,
                thumbColumns,
                thumb_IMAGE_ID + "=" + id,
                null,
                null);
        Cursor thumbCursor = thumbCursorLoader.loadInBackground();
        Bitmap thumbBitmap = null;
        if (thumbCursor.moveToFirst()) {
            int thCulumnIndex = thumbCursor.getColumnIndex(thumb_DATA);
            String thumbPath = thumbCursor.getString(thCulumnIndex);
            Picasso.with(mContext).load(new File(thumbPath)).into(imageView);
        }
        return thumbBitmap;
    }

    private Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 350;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

}