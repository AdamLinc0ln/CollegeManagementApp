package com.example.capstone.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.capstone.Entities.Note;
import com.example.capstone.R;
import com.example.capstone.ViewModels.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE_COURSE_ID =
            "com.example.termapp.Activities.NOTE_COURSE_ID";
    public static final String EXTRA_NOTE_COURSE_TITLE =
            "com.example.termapp.Activities.NOTE_COURSE_TITLE";
    public static final String EXTRA_NOTE_ID =
            "com.example.termapp.Activities.NOTE_ID";
    public static final String EXTRA_NOTE_TITLE =
            "com.example.termapp.Activities.NOTE_TITLE";
    public static final String EXTRA_NOTE_CONTENT =
            "com.example.termapp.Activities.NOTE_CONTENT";
    private static final int EDIT_NOTE_REQUEST = 2;


    private int courseId;
    private int noteId;
    private TextView textViewTitle;
    private TextView textViewMemo;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        textViewTitle = findViewById(R.id.detailed_note_title);
        textViewMemo = findViewById(R.id.detailed_note_memo);

        Intent parentIntent = getIntent();
        courseId = parentIntent.getIntExtra(EXTRA_NOTE_COURSE_ID, -1);
        noteId = parentIntent.getIntExtra(EXTRA_NOTE_ID, -1);
        String noteTitle = parentIntent.getStringExtra(EXTRA_NOTE_TITLE);
        String courseTitle = parentIntent.getStringExtra(EXTRA_NOTE_COURSE_TITLE);
        setTitle(courseTitle + " | " + noteTitle);
        textViewTitle.setText(noteTitle);
        textViewMemo.setText(parentIntent.getStringExtra(EXTRA_NOTE_CONTENT));

        FloatingActionButton editNoteButton = findViewById(R.id.button_edit_note);
        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editNote = new Intent(NoteActivity.this, AddEditNoteActivity.class);
                editNote.putExtra(AddEditNoteActivity.EXTRA_NOTE_ID, noteId);
                editNote.putExtra(AddEditNoteActivity.EXTRA_COURSE_ID, courseId);
                editNote.putExtra(AddEditNoteActivity.EXTRA_COURSE_NOTE_TITLE, noteTitle);
                editNote.putExtra(AddEditNoteActivity.EXTRA_COURSE_NOTE_MEMO, textViewMemo.getText().toString());
                startActivityForResult(editNote, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int noteId = data.getIntExtra(AddEditNoteActivity.EXTRA_NOTE_ID, -1);
            String noteName = data.getStringExtra(AddEditNoteActivity.EXTRA_COURSE_NOTE_TITLE);
            String noteContent = data.getStringExtra(AddEditNoteActivity.EXTRA_COURSE_NOTE_MEMO);

            if (noteId == -1) {
                Toast.makeText(this, "Note Not Saved.", Toast.LENGTH_SHORT).show();
            }
            textViewTitle.setText(noteName);
            textViewMemo.setText(noteContent);

            Note noteEdit = new Note(courseId, noteName, noteContent);

            noteEdit.setId(noteId);
            noteViewModel.update(noteEdit);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note Note Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share_text:
                share();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void share() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TITLE, textViewTitle.getText().toString());
        share.putExtra(Intent.EXTRA_TEXT, textViewMemo.getText().toString());
        share.setType("text/plain");
        Intent shareIntent = Intent.createChooser(share,null);
        startActivity(shareIntent);
    }
}