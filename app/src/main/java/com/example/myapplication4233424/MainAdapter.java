package com.example.myapplication4233424;

import android.app.Activity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import listener.OnPostListener;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.GalleryViewHolder> {
    private ArrayList<PostInfo> mDataset;
    private Activity activity;
    private OnPostListener onPostListener;


     static class GalleryViewHolder extends RecyclerView.ViewHolder {
         CardView cardView;
         GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public MainAdapter(Activity activity, ArrayList<PostInfo> myDataset) {
        this.mDataset = myDataset;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
         return position;
    }

    public void setOnPostListener(OnPostListener onPostListener) {
         this.onPostListener = onPostListener;
    }

    @NonNull
    @Override
    public MainAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, galleryViewHolder.getAdapterPosition());
            }
        });
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);
        titleTextView.setText(mDataset.get(position).gettitle());

        TextView createdAtTextView = cardView.findViewById(R.id.createAtTextView);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getcreatedAt()));


        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
        ArrayList<String> contentsList = mDataset.get(position).getContents();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(contentsLayout.getTag() == null || !contentsLayout.getTag().equals(contentsList)){
            contentsLayout.setTag(contentsList);
            contentsLayout.removeAllViews();
            final int MORE_INDEX = 2;
            for (int i = 0; i < contentsList.size(); i++ ){

                if(i == MORE_INDEX) {
                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(layoutParams);
                    textView.setText("더보기....");
                    contentsLayout.addView(textView);
                    break;
                }

                String contents = contentsList.get(i);
                if(Patterns.WEB_URL.matcher(contents).matches()){
                    ImageView imageView = new ImageView(activity);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    contentsLayout.addView(imageView);
                    Glide.with(activity).load(contents).override(1000).thumbnail(0.1f).into(imageView);
                }else {
                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(layoutParams);
                    textView.setText(contents);
                    contentsLayout.addView(textView);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void showPopup(View view, int position) {
        PopupMenu popup = new PopupMenu(activity, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String id = mDataset.get(position).getid();
                switch (menuItem.getItemId()) {
                    case R.id.modify:
                        onPostListener.onModify(id);
                        return true;
                    case R.id.delete:
                        onPostListener.onDelete(id);
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }
        }