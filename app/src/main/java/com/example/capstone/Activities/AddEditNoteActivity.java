package com.example.capstone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.R;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE_ID =
            "com.example.termapp.Activities.COURSE_NOTE_ID";
    public static final String EXTRA_COURSE_NOTE_TITLE =
            "com.example.termapp.Activities.COURSE_NOTE_TITLE";
    public static final String EXTRA_COURSE_NOTE_MEMO =
            "com.example.termapp.Activities.COURSE_NOTE_MEMO";
    public static final String EXTRA_COURSE_ID =
            "com.example.termapp.Activities.COURSE_ID";

    private EditText Title;
    private EditText Memo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        Title = findViewById(R.id.note_name);
        Memo = findViewById(R.id.note_memo);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent parentIntent = getIntent();
        if (parentIntent.hasExtra(EXTRA_NOTE_ID)) {
            setTitle("Edit Note");
            Title.setText(parentIntent.getStringExtra(EXTRA_COURSE_NOTE_TITLE));
            Memo.setText(parentIntent.getStringExtra(EXTRA_COURSE_NOTE_MEMO));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_edit_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String noteTitle = Title.getText().toString();
        String noteContent = Memo.getText().toString();
        if(noteTitle.trim().isEmpty() || noteContent.trim().isEmpty()){
            Toast.makeText(this, "Note Not Saved.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_NOTE_TITLE, noteTitle);
        data.putExtra(EXTRA_COURSE_NOTE_MEMO, noteContent);
        int noteId = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
        if(noteId != -1){
            data.putExtra(EXTRA_NOTE_ID, noteId);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}