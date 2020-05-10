package com.lionelnkeoua.gifmaker.myImageChooser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lionelnkeoua.gifmaker.myCustomText.TextsMediumBold;
import com.lionelnkeoua.gifmaker.Model.Image;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.Utils.BaseActivity;

import java.io.File;
import java.util.ArrayList;


public class ImagePOJOAdapter extends RecyclerView.Adapter<ImagePOJOAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Image> mImages;
    private SelectImageActivity selectImageActivity;
    private BaseActivity baseActivity;

    public ImagePOJOAdapter(Context mContext, ArrayList<Image> mImages) {
        this.mContext = mContext;
        this.selectImageActivity = (SelectImageActivity) mContext;
        this.baseActivity = (BaseActivity) mContext;
        this.mImages = mImages;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_storeage_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Image images = mImages.get(position);
        Glide.with(mContext).load(new File(images.getThumbPath())).asBitmap().placeholder(R.drawable.rect_load).error(R.drawable.rect_load).into(holder.imgSd);


        if (images.isSelect() == true) {
            holder.lnvHeader.setBackground(mContext.getResources().getDrawable(R.drawable.rect_sleclct));
        } else {
            holder.lnvHeader.setBackground(mContext.getResources().getDrawable(R.drawable.rect_unselect));

        }



        holder.imgSd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (images.isSelect() == false) {
                    images.setSelect(true);
                    selectImageActivity.addImages(images);
                } else {
                    images.setSelect(false);
                    selectImageActivity.removeImages(images);

                }

                    baseActivity.applicationManager.showIntrestialWithWait();

                //selectImageActivity.applicationManager.showIntrestial();
                notifyItemRangeChanged(position, mImages.size(), images);


            }
        });
    }


    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextsMediumBold txtSelect;
        private ImageView imgSd;
        private TextView txtSd;
        private LinearLayout lnvHeader;


        public ViewHolder(View v) {
            super(v);


            txtSelect = (TextsMediumBold) v.findViewById(R.id.txtSelect);
            imgSd = (ImageView) v.findViewById(R.id.imgSd);
            txtSd = (TextView) v.findViewById(R.id.txtSd);
            lnvHeader = (LinearLayout) v.findViewById(R.id.lnvHeader);

        }

    }

}