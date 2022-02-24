package com.example.capstone.Database;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.example.capstone.Dao.AssessmentDao;
import com.example.capstone.Dao.CourseDao;
import com.example.capstone.Dao.NoteDao;
import com.example.capstone.Dao.TermDao;
import com.example.capstone.Entities.Assessment;
import com.example.capstone.Entities.Course;
import com.example.capstone.Entities.Note;
import com.example.capstone.Entities.Term;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;
    private LiveData<List<Term>> searchTerms;

    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    private AssessmentDao assessmentDao;
    private LiveData<List<Assessment>> courseAssessments;

    private NoteDao noteDao;
    private LiveData<List<Note>> courseNotes;

    public Repository(Application application) {
        ProjectDatabase database = ProjectDatabase.getInstance(application);
        termDao = database.termDao();
        allTerms = termDao.getAllTerms();
        searchTerms = termDao.getTerms();
        noteDao = database.noteDao();
        courseDao = database.courseDao();
        assessmentDao = database.assessmentDao();

    }

    public void insert(Term term) {
        new InsertTermAsyncTask(termDao).execute(term);
    }

    public void insert(Course course) {
        new InsertCourseAsyncTask(courseDao).execute(course);
    }

    public void insert(Assessment assessment) {
        new InsertAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }


    public void update(Term term) {
        new UpdateTermAsyncTask(termDao).execute(term);
    }

    public void update(Course course) {
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void update(Assessment assessment) {
        new UpdateAssessmentAsyncTask(assessmentDao).execute(assessment);
    }
    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Term term) {
        new DeleteTermAsyncTask(termDao).execute(term);
    }

    public void delete(Course course) {
        new DeleteCourseAsyncTask(courseDao).execute(course);
    }

    public void delete(Assessment assessment) {
        new DeleteAssessmentAsyncTask(assessmentDao).execute(assessment);
    }
    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllTerms() {
        new DeleteAllTermsAsyncTask(termDao).execute();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }
    public LiveData<List<Term>> getTerms() {
        return searchTerms;
    }

    public LiveData<List<Assessment>> getCourseAssessments(int courseID) {
        return assessmentDao.getCourseAssessments(courseID);
    }

    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDao termDao;

        private InsertTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDao.insert(terms[0]);
            return null;
        }
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }

    private static class InsertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.insert(assessments[0]);
            return null;
        }
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDao termDao;

        private UpdateTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDao.update(terms[0]);
            return null;
        }
    }

    private  static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.update(assessments[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteTermAsyncTask extends AsyncTask<Term, Void, Void> {
        private TermDao termDao;

        private DeleteTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDao.delete(terms[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.delete(courses[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {
        private AssessmentDao assessmentDao;

        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao) {
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.delete(assessments[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    public LiveData<List<Course>> getTermCoursesLive(int termID) {
        return courseDao.getLiveTermCourses(termID);
    }
    public LiveData<List<Note>> getCourseNotes(int courseId){
        return noteDao.getCourseNotes(courseId);
    }

    public List<Course> getTermCourses(int termID) throws ExecutionException, InterruptedException {
        return new GetTermCoursesAsyncTask(courseDao).execute(termID).get();
    }

    private static class GetTermCoursesAsyncTask extends AsyncTask<Integer, Void, List<Course>> {
        private CourseDao courseDao;

        private GetTermCoursesAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected List<Course> doInBackground(Integer... integers) {
            return courseDao.getTermCourses(integers[0]);
        }
    }

    private static class DeleteAllTermsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;

        private DeleteAllTermsAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
