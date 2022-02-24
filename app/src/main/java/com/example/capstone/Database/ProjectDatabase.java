package com.example.capstone.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.capstone.Activities.CourseActivity;
import com.example.capstone.Dao.AssessmentDao;
import com.example.capstone.Dao.CourseDao;
import com.example.capstone.Dao.NoteDao;
import com.example.capstone.Dao.TermDao;
import com.example.capstone.Entities.Assessment;
import com.example.capstone.Entities.Course;
import com.example.capstone.Entities.Note;
import com.example.capstone.Entities.Term;


@Database(entities = {Term.class, Course.class, Assessment.class, Note.class}, version = 7)

public abstract class ProjectDatabase extends RoomDatabase {

    private static ProjectDatabase instance;

    public abstract TermDao termDao();

    public abstract CourseDao courseDao();

    public abstract AssessmentDao assessmentDao();

    public abstract NoteDao noteDao();



    public static synchronized ProjectDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProjectDatabase.class, "project_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDb(instance).execute();
        }
    };


    private static class PopulateDb extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;
        private CourseDao courseDao;
        private AssessmentDao assessmentDao;
        private NoteDao noteDao;

        private PopulateDb(ProjectDatabase db) {
            termDao = db.termDao();
            courseDao = db.courseDao();
            assessmentDao = db.assessmentDao();
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            termDao.insert(new Term("Term 1", "01/01/2020", "06/06/2020", "10:30"));
            termDao.insert(new Term("Term 2", "01/01/2021", "06/06/2022", "17:30"));
            termDao.insert(new Term("Term 3", "01/01/2022", "06/06/2023", "5:15"));

            courseDao.insert(new Course(1, "Course 1", "01/01/2021", "06/05/2021",
                    "Joe", "1111111111", "fake111@gmail.com", CourseActivity.STATUS_IN_PROGRESS, true, false));
            courseDao.insert(new Course(2, "Course 2", "02/02/2022", "06/05/2022",
                    "Greg", "2222222222", "fake222@gmail.com", CourseActivity.STATUS_COMPLETED, false, true));
            courseDao.insert(new Course(3, "Course 3", "03/03/2023", "06/05/2023",
                    "Potter", "3333333333", "fake333@real.com", CourseActivity.STATUS_PLAN_TO_TAKE, false, false));
            assessmentDao.insert(new Assessment(1, "test1", 0, "03/03/2020", "03/05/2020", true, false));
            assessmentDao.insert(new Assessment(2, "test1", 0, "03/03/2020", "03/05/2020", true, true));
            assessmentDao.insert(new Assessment(3, "test1", 1, "03/03/2020", "03/05/2020", false, false));

            noteDao.insert(new Note(1, "Note for course 1", "Note 1"));
            noteDao.insert(new Note(2, "Note for course 2", "Note 2"));
            noteDao.insert(new Note(3, "Note for course 3", "Note 3"));
            noteDao.insert(new Note(3, "Notes for course 3", "Note 4"));
            return null;
        }
    }

}