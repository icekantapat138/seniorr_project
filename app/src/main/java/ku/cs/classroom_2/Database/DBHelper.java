package ku.cs.classroom_2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_name = "classroom_db";
    private static final int DATABASE_VERSION = 7;
    public static final String table_name1 = "user";
    public static final String email = "email";
    public static final String fullname = "fullname";
    public static final String password = "password";
    public static final String role = "role";

    public static final String table_name2 = "course";
    public static final String courseId = "course_id";
    public static final String courseName = "course_name";
    public static final String email_pf = "email_pf";
    public static final String latitude = "latitude";
    public static final String longtitude = "longtitude";
    public static final String distance = "distance";
    public static final String status = "status";

    public static final String table_name3 = "course_regis";
    public static final String courseIdnisitregis = "course_id_nisit_regis";
    public static final String courseid_re = "courseid_re";
    public static final String email_ns = "email_ns";

    public static final String table_name4 = "check_nisit";
    public static final String checkId = "check_id";
    public static final String idNisitRegis = "id_nisit_regis";
    public static final String email1 = "email";
    public static final String courseId1 = "course_id";
    public static final String statusCheck = "statusCheck";

    public static final String table_name5 = "check_study_leave";
    public static final String check_id = "check_id";
    public static final String id_nisit_regis = "id_nisit_regis";
    public static final String email2 = "email";
    public static final String courseId2 = "course_id";
    public static final String detail = "detail";
    public static final String link = "link";

    public static final String table_name6 = "score";
    public static final String courseidd = "course_id";
    public static final String detail1 = "detail";
    public static final String link2 = "link";


    public DBHelper(Context context) {
        super(context, DB_name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table_name1 + "(email VARCHAR(30) PRIMARY KEY, fullname VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL, role VARCHAR(30) NOT NULL)");
        db.execSQL("CREATE TABLE " + table_name2 + "(course_id VARCHAR(30) PRIMARY KEY, course_name VARCHAR(30) NOT NULL, email_pf VARCHAR(30) NOT NULL, latitude VARCHAR(30), longtitude VARCHAR(30), distance VARCHAR(30) , status VARCHAR(30), FOREIGN KEY(email_pf) REFERENCES user(email))");
        db.execSQL("CREATE TABLE " + table_name3 + "(course_id_nisit_regis VARCHAR(30) PRIMARY KEY, courseid_re VARCHAR(30) NOT NULL, email_ns VARCHAR(30) NOT NULL, FOREIGN KEY(courseid_re) REFERENCES course(course_id), FOREIGN KEY(email_ns) REFERENCES email(user))");
        db.execSQL("CREATE TABLE " + table_name4 + "(check_id VARCHAR(30) PRIMARY KEY, id_nisit_regis VARCHAR(30) NOT NULL, email VARCHAR(30) NOT NULL, course_id VARCHAR(30) NOT NULL, statusCheck VARCHAR(30) NOT NULL, FOREIGN KEY(id_nisit_regis) REFERENCES course_id_nisit_regis(course_regis), FOREIGN KEY(email) REFERENCES email_ns(course_regis), FOREIGN KEY(course_id) REFERENCES courseid_re(course_regis))");
        db.execSQL("CREATE TABLE " + table_name5 + "(check_id VARCHAR(30) PRIMARY KEY, id_nisit_regis VARCHAR(30) NOT NULL, email VARCHAR(30) NOT NULL, course_id VARCHAR(30) NOT NULL, detail VARCHAR(300) NOT NULL, link VARCHAR(300) NOT NULL, FOREIGN KEY(id_nisit_regis) REFERENCES course_id_nisit_regis(course_regis), FOREIGN KEY(email) REFERENCES email_ns(course_regis), FOREIGN KEY(course_id) REFERENCES courseid_re(course_regis))");
        db.execSQL("CREATE TABLE " + table_name6 + "(course_id VARCHAR(30) PRIMARY KEY, detail VARCHAR(300) NOT NULL, link VARCHAR(300) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name1);
        db.execSQL("DROP TABLE IF EXISTS " + table_name2);
        db.execSQL("DROP TABLE IF EXISTS " + table_name3);
        db.execSQL("DROP TABLE IF EXISTS " + table_name4);
        db.execSQL("DROP TABLE IF EXISTS " + table_name5);
        db.execSQL("DROP TABLE IF EXISTS " + table_name6);
        onCreate(db);
    }

    public boolean adduser(String Uemail, String Ufullname, String Upassword, String Urole) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(email, Uemail);
        contentValues.put(fullname, Ufullname);
        contentValues.put(password, Upassword);
        contentValues.put(role, Urole);
        long result = db.insert(table_name1, null, contentValues);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name1 + " WHERE email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkEmailPassword(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name1 + " WHERE email = ? and password = ?", new String[]{email, pass});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor checkRole(String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + table_name1 + " WHERE email = ?", new String[]{role});
        return cursor;
    }

    public boolean addCourse(String Cid, String Cname, String Cemail, Double Clat, Double Clon, Double Cdis, String Cstatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(courseId, Cid);
        contentValues.put(courseName, Cname);
        contentValues.put(email_pf, Cemail);
        contentValues.put(latitude, Clat);
        contentValues.put(longtitude, Clon);
        contentValues.put(distance, Cdis);
        contentValues.put(status, Cstatus);
        long result = db.insert(table_name2, null, contentValues);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor coursenameIs(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + table_name2 + " WHERE course_id = ?", new String[]{cid});
        return cursor;
    }

    public Cursor courseStatus(String ccourseid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + table_name2 + " WHERE course_id = ?", new String[]{ccourseid});
        return cursor;
    }

    public boolean checkCourseID(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name2 + " WHERE course_id=?", new String[]{cid});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor CourseList(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name2 + " WHERE email_pf = ?", new String[]{email});
        return cursor;
    }

    public Cursor CourseList2(String email, String courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name2 + " WHERE email_pf = ? AND course_id = ?", new String[]{email, courseID});
        return cursor;
    }

    public Cursor CourseList3() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name2, null);
        return cursor;
    }

    public Cursor CourseList4(String courseid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name2 + " WHERE course_id=?", new String[]{courseid});
        return cursor;
    }

    public Boolean updateStatusLatLong(String mid, String mlat, String mlongti, String mdistance, String mstatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(latitude, mlat);
        contentValues.put(longtitude, mlongti);
        contentValues.put(distance, mdistance);
        contentValues.put(status, mstatus);
        long result = db.update(table_name2, contentValues,"course_id=?", new String[]{mid});
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getLatLon(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + table_name2 + " WHERE course_id = ?", new String[]{cid});
        return cursor;
    }

    public boolean addRegisNisitCourse(String rid, String rcourse, String remail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(courseIdnisitregis, rid);
        contentValues.put(courseid_re, rcourse);
        contentValues.put(email_ns, remail);
        long result = db.insert(table_name3, null, contentValues);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCode(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name3 + " WHERE course_id_nisit_regis=?", new String[]{code});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor courseReList(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name3 + " WHERE email_ns=?", new String[]{email});
        return cursor;
    }

    public Cursor courseRe(String email, String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name3 + " WHERE email_ns=? AND courseid_re=?", new String[]{email, cid});
        return cursor;
    }

    public boolean addCheckID(String cicheckid, String ciidnregis, String ciemail, String cicid, String cistatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(checkId, cicheckid);
        contentValues.put(idNisitRegis, ciidnregis);
        contentValues.put(email1, ciemail);
        contentValues.put(courseId1, cicid);
        contentValues.put(statusCheck, cistatus);
        long result = db.insert(table_name4, null, contentValues);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor courseCheck(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name4 + " WHERE course_id=?", new String[]{cid});
        return cursor;
    }

    public Cursor courseCheckStatus(String cid, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name4 + " WHERE course_id=? AND statusCheck=?", new String[]{cid, status});
        return cursor;
    }

    public Cursor courseCheckEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name4 + " WHERE email=?", new String[]{email});
        return cursor;
    }

    public boolean addStudyLeave(String slid, String slnre, String slemail, String slcid, String dtail, String lnk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(check_id, slid);
        contentValues.put(id_nisit_regis, slnre);
        contentValues.put(email2, slemail);
        contentValues.put(courseId2, slcid);
        contentValues.put(detail1, dtail);
        contentValues.put(link, lnk);
        long result = db.insert(table_name5, null, contentValues);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor delete(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("DELETE FROM " + table_name5 + " WHERE check_id=?", new String[]{cid});
        return cursor;
    }

    public Cursor studyLeaveID(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name5 + " WHERE course_id=?", new String[]{cid});
        return cursor;
    }

    public Cursor studyLeaveIDD(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name5 + " WHERE check_id=?", new String[]{cid});
        return cursor;
    }

    public boolean addScore(String scid, String dtail, String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(courseidd, scid);
        contentValues.put(detail, dtail);
        contentValues.put(link2, link);
        long result = db.insert(table_name6, null, contentValues);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor scoreList(String cid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name6 + " WHERE course_id=?", new String[]{cid});
        return cursor;
    }
}