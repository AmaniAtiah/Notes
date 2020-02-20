package com.barmej.notes2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.barmej.notes2.adapter.NoteAdapter;
import com.barmej.notes2.date.CheckNote;
import com.barmej.notes2.date.Note;
import com.barmej.notes2.date.PhotoNote;
import com.barmej.notes2.date.TextNote;
import com.barmej.notes2.listener.ItemClickListenerCheckNote;
import com.barmej.notes2.listener.ItemClickListenerNote;
import com.barmej.notes2.listener.ItemLClickListenerPhotoNote;
import com.barmej.notes2.listener.ItemLongClickListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int ADD_NOTE = 142;
    private static final int EDIT_NOTE = 140;
    private RecyclerView mRecyclerView;
    private ArrayList<Note> mItems;
    private NoteAdapter mAdapter;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view_photos);

        mItems = new ArrayList<>();
        loadData();

        mAdapter = new NoteAdapter(mItems, this, new ItemClickListenerNote() {
            @Override
            public void onClickItemNote(int position) {
                editNote(position, NoteDetailsActivity.class);
            }
        }, new ItemLClickListenerPhotoNote() {
            @Override
            public void onClickItemPhotoNote(int position) {
                editNote(position, PhotoNoteDetailsActivity.class);
            }
        }, new ItemClickListenerCheckNote() {
            @Override
            public void onClickItemCheckNote(int position) {
                editNote(position, CheckNoteDetailsActivity.class);
            }
        }, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                deleteItem(position);
            }
        });


        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddNewNoteActivity();
            }
        });

    }

    private void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared prefernces", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriSerializerDeserializer())
                .create();
        String json = gson.toJson(mItems);
        System.out.println(json);
        editor.putString("task_list", json);
        editor.apply();
    }



    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefernces", MODE_PRIVATE);
        RuntimeTypeAdapterFactory<Note> noteTypesAdapter = RuntimeTypeAdapterFactory.of(Note.class, "type")
                .registerSubtype(PhotoNote.class)
                .registerSubtype(CheckNote.class)
                .registerSubtype(TextNote.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Uri.class, new UriSerializerDeserializer())
                .registerTypeAdapterFactory(noteTypesAdapter)
                .create();
        String json = sharedPreferences.getString("task_list", null);
        System.out.println("Ret" + json);
        Type listOfNotes = new TypeToken<List<Note>>() {
        }.getType();
        mItems = gson.fromJson(json, listOfNotes);

        if (mItems == null) {
            mItems = new ArrayList<>();
        }

    }

    private void startAddNewNoteActivity() {
        Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE) {

            if (resultCode == RESULT_OK && data != null) {

                    Note note = data.getParcelableExtra(Constants.NOTE);
                    addItemNote(note);

            }

        } else if (requestCode == EDIT_NOTE) {

            if (resultCode == RESULT_OK && data != null) {

                int position = data.getIntExtra(Constants.POSITION, 0);
                Note note = data.getParcelableExtra(Constants.NOTE);
                modifyItem(position, note);
            }
        }

        saveData();
    }


    private void addItemNote(Note note) {
        mItems.add(note);
        mAdapter.notifyItemInserted(mItems.size() - 1);
    }

    public void modifyItem( int position,  Note note) {
        mItems.set(position, note);
        mAdapter.notifyDataSetChanged();
    }

    private void editNote(int position, Class className) {
        Note selectedItemNote = mItems.get(position);
        Intent intent = new Intent(MainActivity.this, className);
        intent.putExtra(Constants.NOTE, selectedItemNote);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, EDIT_NOTE);
    }

    private void deleteItem(final int position) {
        saveData();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }

                })
                .create();
        alertDialog.show();
    }
}




