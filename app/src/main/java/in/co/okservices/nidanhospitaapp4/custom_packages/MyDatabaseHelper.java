package in.co.okservices.nidanhospitaapp4.custom_packages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.DataInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.co.okservices.nidanhospitaapp4.R;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DB_NAME = "PatientLibrary.db";

    private static final String PATIENT_TABLE = "patients";
    private static final String DAY_RECORD_TABLE = "day_record";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SR_NO = "sr_no";
    private static final String COLUMN_CHECKED = "checked";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_AMOUNT = "amount";

    private static final String COLUMN_NORMAL = "normal";
    private static final String COLUMN_EMERGENCY = "emergency";
    private static final String COLUMN_PAPER_VALID = "paper_valid";
    private static final String COLUMN_EMERGENCY_PAPER_VALID = "emergency_paper_valid";
    private static final String COLUMN_DISCOUNT = "discount";
    private static final String COLUMN_CANCEL = "cancel";
    private static final String COLUMN_ADD_AMOUNT = "add_amount";
    private static final String COLUMN_TOTAL_AMOUNT_COLLECTED = "total_amount_collected";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String patientTableQuery = "CREATE TABLE " + PATIENT_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SR_NO + " INTEGER, " +
                COLUMN_CHECKED + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_COLOR + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_AMOUNT + " INT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER);";

        String dayRecordTableQuery = "CREATE TABLE " + DAY_RECORD_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_NORMAL + " INTEGER, " +
                COLUMN_EMERGENCY + " INTEGER, " +
                COLUMN_PAPER_VALID + " INTEGER, " +
                COLUMN_EMERGENCY_PAPER_VALID + " INTEGER, " +
                COLUMN_DISCOUNT + " INTEGER, " +
                COLUMN_CANCEL + " INTEGER, " +
                COLUMN_ADD_AMOUNT + " INTEGER, " +
                COLUMN_TOTAL_AMOUNT_COLLECTED + " INTEGER);";

        try{
            sqLiteDatabase.execSQL(patientTableQuery);
            sqLiteDatabase.execSQL(dayRecordTableQuery);
        } catch(Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE + ";");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DAY_RECORD_TABLE + ";");
        } catch(Exception ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        onCreate(sqLiteDatabase);
    }

    public String addRawData(){
        SQLiteDatabase db=this.getWritableDatabase();
        float res = 0;
        ContentValues pcv=new ContentValues();
        try{
            for (int i = 1; i <= 150; i++){
                pcv.put(COLUMN_SR_NO,i);
                pcv.put(COLUMN_CHECKED,"no");
                pcv.put(COLUMN_TYPE, "none");
                pcv.put(COLUMN_COLOR, Integer.parseInt(String.valueOf(R.color.white)));
                pcv.put(COLUMN_DATE, this.getDate());
                pcv.put(COLUMN_TIME, "--:--:-- --");
                pcv.put(COLUMN_AMOUNT, 0);
                pcv.put(COLUMN_NAME, "--");
                pcv.put(COLUMN_AGE, 0);
                res = db.insert(PATIENT_TABLE  ,null,pcv);
            }
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(res==-1)
            return "Failed";
        else
            return  "Successfully inserted";
    }

    public String addDayData(){
        SQLiteDatabase db=this.getWritableDatabase();
        float res = 0;
        ContentValues dcv=new ContentValues();
        try{
            dcv.put(COLUMN_DATE, this.getDate());
            dcv.put(COLUMN_NORMAL, 0);
            dcv.put(COLUMN_EMERGENCY, 0);
            dcv.put(COLUMN_PAPER_VALID, 0);
            dcv.put(COLUMN_EMERGENCY_PAPER_VALID, 0);
            dcv.put(COLUMN_DISCOUNT, 0);
            dcv.put(COLUMN_CANCEL, 0);
            dcv.put(COLUMN_ADD_AMOUNT, 0);
            dcv.put(COLUMN_TOTAL_AMOUNT_COLLECTED, 0);
            res = db.insert(DAY_RECORD_TABLE  ,null,dcv);
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(res==-1)
            return "Failed";
        else
            return  "Successfully inserted";
    }

    public boolean checkIfRecordExist(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + PATIENT_TABLE + " WHERE " + COLUMN_DATE + " = '" + date + "';";
        try {
            Cursor c = db.rawQuery(query, null);
            if(c.getCount()>0) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor readPatientData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+PATIENT_TABLE+" WHERE "+COLUMN_DATE+" = '" + getDate() + "'";
        Cursor cursor = db.rawQuery(qry,null);
        return  cursor;
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy:MM:dd");
        return dateFormat.format(currentTime);
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
        return dateFormat.format(currentTime);
    }

    public void updatePatientDetail(int _id, int sr_no, String type, int color, String name, String age, int amount){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        try{
            cv.put(COLUMN_SR_NO,sr_no);
            cv.put(COLUMN_CHECKED,"yes");
            cv.put(COLUMN_TYPE, type);
            cv.put(COLUMN_COLOR, color);
            cv.put(COLUMN_DATE, this.getDate());
            cv.put(COLUMN_TIME, this.getTime());
            cv.put(COLUMN_AMOUNT, amount);
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_AGE, age);
            db.update(PATIENT_TABLE, cv, COLUMN_ID + "=?", new String[]{String.valueOf(_id)});
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDayRecord(String type, int amount){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

    }
}
