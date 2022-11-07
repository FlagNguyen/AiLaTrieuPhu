package com.example.ailatrieuphu.repository;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ailatrieuphu.repository.model.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class QuestionRepository extends SQLiteOpenHelper {
    static String DEVICE_DB_PATH = "";
    static String DEVICE_DB_NAME = "AiLaTrieuPhuDB.db";
    private SQLiteDatabase sqlDb, sqlDb1;
    private Activity activity;

    public QuestionRepository(Activity activity) {
        super(activity, DEVICE_DB_NAME, null, 1);
        this.activity = activity;
    }

    public void create() {
        setDatabasePath();
        File file = new File(DEVICE_DB_PATH + DEVICE_DB_NAME);
        if (!file.exists()) {
            this.getReadableDatabase();
            this.close();
            try {
                InputStream input = activity.getAssets().open(DEVICE_DB_NAME);
                String fileName = DEVICE_DB_PATH + DEVICE_DB_NAME;
                OutputStream output = new FileOutputStream(fileName);
                byte[] mBuffer = new byte[1024];
                int length;
                while ((length = input.read(mBuffer)) > 0)
                    output.write(mBuffer, 0, length);
                output.flush();
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get db path
     */
    public void setDatabasePath() {
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DEVICE_DB_PATH = activity.getApplicationInfo().dataDir + "/databases/";
        else
            DEVICE_DB_PATH = activity.getApplicationContext().getDatabasePath(DEVICE_DB_NAME).getAbsolutePath();
    }

    // C:\Users\asus\AiLaTrieuPhu.db
    public void open() {
        sqlDb = SQLiteDatabase.openDatabase(DEVICE_DB_PATH + DEVICE_DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public synchronized void close() {
        super.close();
        if (sqlDb != null) {
            sqlDb.close();
        }
    }

    public ArrayList<Question> getData() {
        sqlDb1 = this.getReadableDatabase();
        ArrayList<Question> questions = new ArrayList<>();
        Cursor easyQuestionCursor = sqlDb1.rawQuery("SELECT * FROM question WHERE difficulty == 1 LIMIT 5", null);
        Cursor mediumQuestionCursor = sqlDb1.rawQuery("SELECT * FROM question WHERE difficulty == 2 LIMIT 5", null);
        Cursor hardQuestionCursor = sqlDb1.rawQuery("SELECT * FROM question WHERE difficulty == 3 LIMIT 5", null);

        questions.addAll(getDataFromQueryCursor(easyQuestionCursor));
        questions.addAll(getDataFromQueryCursor(mediumQuestionCursor));
        questions.addAll(getDataFromQueryCursor(hardQuestionCursor));
        return questions;
    }

    @SuppressLint("Range")
    public ArrayList<Question> getDataFromQueryCursor(Cursor cursor) {
        ArrayList<Question> questions = new ArrayList<>();
        while (cursor.moveToNext()) {
            questions.add(new Question(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("question")),
                    cursor.getString(cursor.getColumnIndex("answer_a")),
                    cursor.getString(cursor.getColumnIndex("answer_b")),
                    cursor.getString(cursor.getColumnIndex("answer_c")),
                    cursor.getString(cursor.getColumnIndex("answer_d")),
                    cursor.getString(cursor.getColumnIndex("correct_answer")),
                    cursor.getInt(cursor.getColumnIndex("difficulty"))
            ));
        }
        return questions;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
