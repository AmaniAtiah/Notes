package com.barmej.notes2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.barmej.notes2.date.Note;

public class NoteDetailsActivity extends AppCompatActivity {
    private EditText mEditTextNote;
    private View backgroundColor;

    private int color;
    private String textNoteEdit;
    private int position;
    private Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        mEditTextNote = findViewById(R.id.noteEditText);
        backgroundColor = findViewById(R.id.activity_Note_color);

        position = getIntent().getIntExtra(Constants.POSITION, 0);
        note = getIntent().getParcelableExtra(Constants.NOTE);
        color = note.getColor();

        mEditTextNote.setText(note.getTextNote());
        backgroundColor.setBackgroundColor(color);
       // note.setColor(color);

        RadioGroup colors = findViewById(R.id.colorRadioGroup);


        final Button editBt = findViewById(R.id.button_edit);

        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote();
            }
        });

        colors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.yellow_btn:
                        color = ContextCompat.getColor(NoteDetailsActivity.this, R.color.yellow);
                        backgroundColor.setBackgroundResource(R.color.yellow);
                        break;

                    case R.id.red_btn:
                        color = ContextCompat.getColor(NoteDetailsActivity.this, R.color.red);
                        backgroundColor.setBackgroundResource(R.color.red);
                        break;

                    case R.id.blue_btn:
                        color = ContextCompat.getColor(NoteDetailsActivity.this, R.color.blue);
                        backgroundColor.setBackgroundResource(R.color.blue);
                        break;
                }
            }
        });
    }

    private void editNote() {
        textNoteEdit = mEditTextNote.getText().toString();

        if (textNoteEdit.trim().isEmpty()) {
            return;
        } else {
            note.setTextNote(textNoteEdit);
            note.setColor(color);
            Intent intent = new Intent();
            intent.putExtra(Constants.NOTE,note);
            intent.putExtra(Constants.POSITION,position);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
