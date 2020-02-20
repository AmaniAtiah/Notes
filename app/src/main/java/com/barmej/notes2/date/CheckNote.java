package com.barmej.notes2.date;

import android.os.Parcel;

public class CheckNote extends Note {

    private boolean checkNote;

    public CheckNote() {
        super("CheckNote");
    }

    public CheckNote(String textNote, int color, boolean checkNote) {
        super("CheckNote", textNote, color);
        this.checkNote = checkNote;
    }

    protected CheckNote(Parcel in) {
        super(in);
        checkNote = in.readByte() != 0;
    }

    public static final Creator<CheckNote> CREATOR = new Creator<CheckNote>() {
        @Override
        public CheckNote createFromParcel(Parcel in) {
            return new CheckNote(in);
        }

        @Override
        public CheckNote[] newArray(int size) {
            return new CheckNote[size];
        }
    };

    public void setCheckNote(boolean checkNote) {
        this.checkNote = checkNote;
    }

    public boolean getCheckNote() {
        return checkNote;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (checkNote ? 1 : 0));
    }
}
