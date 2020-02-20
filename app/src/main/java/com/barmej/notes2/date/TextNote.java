package com.barmej.notes2.date;

import android.os.Parcel;

public class TextNote extends Note {

    public TextNote() {
        super("TextNote");
    }

    public TextNote(String textNote, int color) {
        super("TextNote", textNote, color);
    }

    protected TextNote(Parcel in) {
        super(in);
    }

    public static final Creator<TextNote> CREATOR = new Creator<TextNote>() {
        @Override
        public TextNote createFromParcel(Parcel in) {
            return new TextNote(in);
        }

        @Override
        public TextNote[] newArray(int size) {
            return new TextNote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

}
