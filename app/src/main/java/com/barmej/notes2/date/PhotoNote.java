package com.barmej.notes2.date;

import android.net.Uri;
import android.os.Parcel;

public class PhotoNote extends Note {

    private Uri imageNote;

    public PhotoNote() {
        super("PhotoNote");
    }

    public PhotoNote(String textNote, int color, Uri imageNote) {
        super("PhotoNote", textNote, color);
        this.setImageNote(imageNote);
    }

    protected PhotoNote(Parcel in) {
        super(in);
        imageNote = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<PhotoNote> CREATOR = new Creator<PhotoNote>() {
        @Override
        public PhotoNote createFromParcel(Parcel in) {
            return new PhotoNote(in);
        }

        @Override
        public PhotoNote[] newArray(int size) {
            return new PhotoNote[size];
        }
    };

    public Uri getImageNote() {
        return imageNote;
    }


    public void setImageNote(Uri imageNote) {
        this.imageNote = imageNote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(imageNote, flags);
    }
}

