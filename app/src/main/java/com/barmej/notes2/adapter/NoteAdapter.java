package com.barmej.notes2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notes2.Constants;
import com.barmej.notes2.R;
import com.barmej.notes2.date.CheckNote;
import com.barmej.notes2.date.Note;
import com.barmej.notes2.date.PhotoNote;
import com.barmej.notes2.listener.ItemClickListenerCheckNote;
import com.barmej.notes2.listener.ItemClickListenerNote;
import com.barmej.notes2.listener.ItemLClickListenerPhotoNote;
import com.barmej.notes2.listener.ItemLongClickListener;

import java.util.ArrayList;





public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Note> mItems;
    Context context;
    private ItemClickListenerNote mItemClickListenerNote;
    private ItemLClickListenerPhotoNote mItemLClickListenerPhotoNote;

    private ItemClickListenerCheckNote mItemClickListenerCheckNote;
    private ItemLongClickListener mItemLongClickListener;

    public NoteAdapter(ArrayList<Note> mItems, Context context, ItemClickListenerNote mItemClickListenerNote, ItemLClickListenerPhotoNote mItemLClickListenerPhotoNote, ItemClickListenerCheckNote mItemClickListenerCheckNote, ItemLongClickListener mItemLongClickListener) {
        this.mItems = mItems;
        this.context = context;
        this.mItemClickListenerNote = mItemClickListenerNote;
        this.mItemLClickListenerPhotoNote = mItemLClickListenerPhotoNote;

        this.mItemClickListenerCheckNote = mItemClickListenerCheckNote;
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof PhotoNote) {
            return Constants.IMAGE_DATA;
        }else if(mItems.get(position) instanceof CheckNote) {
            return Constants.CHECK_DATA;

        } else {
          return Constants.NOTE_DATA;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == Constants.NOTE_DATA) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            viewHolder =  new TextNoteViewHolder(view, mItemClickListenerNote, mItemLongClickListener);
        } else if(viewType == Constants.IMAGE_DATA) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
            viewHolder =  new ImageNoteViewHolder(view, mItemLClickListenerPhotoNote, mItemLongClickListener);
        }else if(viewType == Constants.CHECK_DATA){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_check, parent, false);
            viewHolder =  new CheckNoteViewHolder(view, mItemClickListenerCheckNote, mItemLongClickListener);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == Constants.NOTE_DATA){
            Note note =  mItems.get(position);

                TextNoteViewHolder textNoteViewHolder = (TextNoteViewHolder) holder;
                textNoteViewHolder.textNoteTv.setText(note.getTextNote());
                textNoteViewHolder.cardViewNoteColor.setCardBackgroundColor(note.getColor());
                textNoteViewHolder.position = position;

        } else if(getItemViewType(position) == Constants.IMAGE_DATA){
            PhotoNote photoNote = (PhotoNote) mItems.get(position);

                ImageNoteViewHolder imageNoteViewHolder = (ImageNoteViewHolder) holder;
                imageNoteViewHolder.photoNoteTv.setText(photoNote.getTextNote());
                imageNoteViewHolder.imageNoteIv.setImageURI(photoNote.getImageNote());
                imageNoteViewHolder.cardViewPhotoNoteColor.setCardBackgroundColor(photoNote.getColor());
                imageNoteViewHolder.position = position;


        } else if(getItemViewType(position) == Constants.CHECK_DATA){
            CheckNote checkNote = (CheckNote) mItems.get(position);

                CheckNoteViewHolder checkNoteViewHolder = (CheckNoteViewHolder) holder;
                checkNoteViewHolder.textNoteCheckTv.setText(checkNote.getTextNote());
                checkNoteViewHolder.noteCheckCb.setChecked(checkNote.getCheckNote());
                checkNoteViewHolder.cardViewCheckNoteColor.setCardBackgroundColor(checkNote.getColor());
                checkNoteViewHolder.position = position;

        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class TextNoteViewHolder extends RecyclerView.ViewHolder {

        private TextView textNoteTv;
        private CardView cardViewNoteColor;
        private int position;

        public  TextNoteViewHolder(final View itemView, final ItemClickListenerNote mItemClickListenerNote, final ItemLongClickListener mItemLongClickListener) {
            super(itemView);

            textNoteTv = itemView.findViewById(R.id.text_list_view_item_note);
            cardViewNoteColor = itemView.findViewById(R.id.list_card_view_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mItemClickListenerNote.onClickItemNote(position);
                }
            });

          itemView.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View v) {
                  mItemLongClickListener.onLongClickItem(position);
                  return true;
              }
          });
        }

    }

    static class ImageNoteViewHolder extends RecyclerView.ViewHolder {
        private TextView photoNoteTv;
        private ImageView imageNoteIv;
        private CardView cardViewPhotoNoteColor;
        private int position;


        public ImageNoteViewHolder(View itemView, final ItemLClickListenerPhotoNote mItemLClickListenerPhotoNote, final ItemLongClickListener mItemLongClickListener){
            super(itemView);

            photoNoteTv = itemView.findViewById(R.id.text_list_photo_note_view_item_note);
            imageNoteIv = itemView.findViewById(R.id.image_view_list_item_photo);
            cardViewPhotoNoteColor = itemView.findViewById(R.id.list_card_view_photo_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemLClickListenerPhotoNote.onClickItemPhotoNote(position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });

        }
    }

    static class CheckNoteViewHolder extends RecyclerView.ViewHolder {
        private TextView textNoteCheckTv;
        private CheckBox noteCheckCb;
        private CardView cardViewCheckNoteColor;
        private int position;

        public CheckNoteViewHolder(View itemView, final ItemClickListenerCheckNote mItemClickListenerCheckNote, final ItemLongClickListener mItemLongClickListener){
            super(itemView);

            textNoteCheckTv = itemView.findViewById(R.id.text_note_list_view_item);
            noteCheckCb = itemView.findViewById(R.id.check_list_view_item_note);
            cardViewCheckNoteColor = itemView.findViewById(R.id.list_card_view_check_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListenerCheckNote.onClickItemCheckNote(position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
        }
    }
}
