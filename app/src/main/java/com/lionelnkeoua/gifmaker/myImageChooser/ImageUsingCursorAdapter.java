package com.lionelnkeoua.gifmaker.myImageChooser;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lionelnkeoua.gifmaker.myHomeStorage.CursorRecyclerViewAdapter;
import com.lionelnkeoua.gifmaker.R;

import java.io.File;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class ImageUsingCursorAdapter extends CursorRecyclerViewAdapter<ImageUsingCursorAdapter.ViewHolder> {

    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public ImageUsingCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, "");
        mContext = context;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor, final int position) {
        viewHolder.setData(cursor);
        int int_ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));

    obervable(int_ID,viewHolder.imgSd);
      viewHolder.imgSd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.getContentResolver().delete(Uri.parse(viewHolder.txtSd.getText().toString()), null, null);
                Toast.makeText(mContext, "POSiton " + position + "Curesot" + cursor.getCount() + viewHolder.txtSd.getText().toString(), Toast.LENGTH_SHORT).
                        show();
               changeCursor(cursor);
               notifyItemRemoved(position);
               notifyItemRangeChanged(position, cursor.getCount());
            }

        });


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
//            imgSd.setImageURI(Uri.parse(new File(cursor.getString(cursor.getColumnIndex(MediaStore.Image.Media.DATA))).toString()));
            int uriIndex = getUriIndex(getCursor());
            String itemUri = getCursor().getString(uriIndex) + "/" + getCursor().getString(getCursor().getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            txtSd.setText(itemUri);
            Log.e("Cursor item uri ", itemUri + "");


        }
    }

    private int getUriIndex(Cursor c) {
        int uriIndex;
        String[] columnNames = {
                MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()
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

    public void obervable(int id, final ImageView img) {

        Observable.just(getThumbPath(id))
               .subscribe(new Observer<String>() {
                   @Override
                   public void onSubscribe(Disposable d) {


                   }

                   @Override
                   public void onNext(String value) {
                       Glide.with(mContext).load(new File(value)).asBitmap().override(150,150).placeholder(R.drawable.rect_load).error(R.drawable.rect_load).into(img);
                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onComplete() {

                   }
               });

    }


    public String getThumbPath(int id) {
        final String thumb_DATA = MediaStore.Images.Thumbnails.DATA;
        final String thumb_IMAGE_ID = MediaStore.Images.Thumbnails.IMAGE_ID;
        final Uri thumbUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

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
            return thumbPath;

        } else {
        }
        return "";
    }



}