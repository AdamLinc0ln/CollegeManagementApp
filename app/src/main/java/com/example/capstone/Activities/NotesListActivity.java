package com.example.capstone.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.capstone.Adapters.NoteAdapter;
import com.example.capstone.Entities.Note;
import com.example.capstone.R;
import com.example.capstone.ViewModels.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class NotesListActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;

    public static final String EXTRA_COURSE_ID =
            "com.example.termapp.Activities.COURSE_ID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.termapp.Activities.COURSE_TITLE";

    private int courseId;
    private String courseTitle;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        FloatingActionButton addNoteButton = findViewById(R.id.button_add_note);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNoteIntent = new Intent(NotesListActivity.this, AddEditNoteActivity.class);
                startActivityForResult(addNoteIntent, ADD_NOTE_REQUEST);
            }
        });

        Intent noteListIntent = getIntent();
        courseId = noteListIntent.getIntExtra(EXTRA_COURSE_ID, -1);
        courseTitle = noteListIntent.getStringExtra(EXTRA_COURSE_TITLE);

        setTitle(courseTitle + " Notes");

        RecyclerView recyclerView = findViewById(R.id.note_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getCourseNotes(courseId).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note deletedNote = adapter.getNoteAt(viewHolder.getAdapterPosition());
                noteViewModel.delete(deletedNote);
                Toast.makeText(NotesListActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent noteIntent = new Intent(NotesListActivity.this, NoteActivity.class);
                noteIntent.putExtra(NoteActivity.EXTRA_NOTE_ID, note.getId());
                noteIntent.putExtra(NoteActivity.EXTRA_NOTE_COURSE_ID, note.getCourseId());
                noteIntent.putExtra(NoteActivity.EXTRA_NOTE_COURSE_TITLE, courseTitle);
                noteIntent.putExtra(NoteActivity.EXTRA_NOTE_TITLE, note.getName());
                noteIntent.putExtra(NoteActivity.EXTRA_NOTE_CONTENT, note.getMemo());
                startActivity(noteIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
            String noteName = data.getStringExtra(AddEditNoteActivity.EXTRA_COURSE_NOTE_TITLE);
            String noteContent = data.getStringExtra(AddEditNoteActivity.EXTRA_COURSE_NOTE_MEMO);

            if (courseId == -1) throw new AssertionError("CourseId cannot be -1");
            Note note = new Note(courseId, noteName, noteContent);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Note not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}