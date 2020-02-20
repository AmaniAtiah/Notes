package com.barmej.notes2.date;



import android.os.Parcel;
import android.os.Parcelable;

public abstract class Note implements Parcelable {

    public String type = "Note";
    private String textNote;
    private int color;


    public Note(String type) {
        this.type = type;
    }

    public Note(String type, String textNote, int color) {
        this.setType(type);
        this.setTextNote(textNote);
        this.setColor(color);
    }

    protected Note(Parcel in) {
        type = in.readString();
        textNote = in.readString();
        color = in.readInt();
    }

    public String getTextNote() {
        return textNote;
    }


    public int getColor() {
        return color;
    }




    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(textNote);
        dest.writeInt(color);
    }
}
