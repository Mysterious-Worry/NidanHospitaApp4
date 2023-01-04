package in.co.okservices.nidanhospitaapp4.custom_packages;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import in.co.okservices.nidanhospitaapp4.DayRecordActivity;
import in.co.okservices.nidanhospitaapp4.R;
import in.co.okservices.nidanhospitaapp4.data_models.*;

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

    private static final String COLUMN_TOTAL_PATIENTS = "total_patients";
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
                COLUMN_AGE + " TEXT);";

        String dayRecordTableQuery = "CREATE TABLE " + DAY_RECORD_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TOTAL_PATIENTS + " INTEGER, " +
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
                pcv.put(COLUMN_COLOR, Integer.parseInt(String.valueOf(R.color.white2)));
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

    public String deletePatientRow(String id, String date, String sr_no){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            float res = -1;
            ContentValues pcv=new ContentValues();

            pcv.put(COLUMN_SR_NO, sr_no);
            pcv.put(COLUMN_CHECKED, "no");
            pcv.put(COLUMN_TYPE, "none");
            pcv.put(COLUMN_COLOR, Integer.parseInt(String.valueOf(R.color.white2)));
            pcv.put(COLUMN_DATE, date);
            pcv.put(COLUMN_TIME, "--:--:-- --");
            pcv.put(COLUMN_AMOUNT, 0);
            pcv.put(COLUMN_NAME, "--");
            pcv.put(COLUMN_AGE, 0);
            res = db.update(PATIENT_TABLE, pcv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

            if(res==-1)
                return "Failed";
            else
                return  "Successfully deleted";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

    public void deleteDayRecord(String type, String amount){

        if(type.equals("paper_valid_emergency")){
            type = "emergency_paper_valid";
        }

        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            
            int total_amount = senderCell(COLUMN_TOTAL_AMOUNT_COLLECTED) - Integer.parseInt(amount);
            int type_count = senderCell(type) - 1;
            int total_patients = senderCell(COLUMN_TOTAL_PATIENTS) - 1;

            cv.put(COLUMN_TOTAL_AMOUNT_COLLECTED, total_amount);
            cv.put(type, type_count);
            cv.put(COLUMN_TOTAL_PATIENTS, total_patients);

            db.update(DAY_RECORD_TABLE, cv, COLUMN_DATE + "=?", new String[]{String.valueOf(getDate())});
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readPatientData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+PATIENT_TABLE+" WHERE "+COLUMN_DATE+" = '" + getDate() + "'";
        Cursor cursor = db.rawQuery(qry,null);
        return  cursor;
    }

    public Cursor readOnePatientData(String srno){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+PATIENT_TABLE+" WHERE "+COLUMN_DATE+" = '" + getDate() + "' AND " + COLUMN_SR_NO + " ='" + srno+"'";
        Cursor cursor = db.rawQuery(qry,null);
        return  cursor;
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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

        int total_amount = senderCell(COLUMN_TOTAL_AMOUNT_COLLECTED) + amount;
        int type_count = senderCell(type) + 1;
        int total_patients = senderCell(COLUMN_TOTAL_PATIENTS) + 1;

        cv.put(COLUMN_TOTAL_AMOUNT_COLLECTED, total_amount);
        cv.put(type, type_count);
        cv.put(COLUMN_TOTAL_PATIENTS, total_patients);

        db.update(DAY_RECORD_TABLE, cv, COLUMN_DATE + "=?", new String[]{String.valueOf(getDate())});
    }

    @SuppressLint("Range")
    public int senderCell(String column_name){
        int rv = -1;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + column_name + " from " + DAY_RECORD_TABLE + " where " + COLUMN_DATE + "=?", new String[]{getDate()});
        if(cursor.moveToNext()){
            rv = (int) cursor.getLong(cursor.getColumnIndex(column_name));
        }
        return rv;
    }

    public Cursor fetchDayData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAY_RECORD_TABLE;
        return db.rawQuery(query, null);
    }

    public Cursor fetchOneDayData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAY_RECORD_TABLE + " WHERE " + COLUMN_DATE + "='" + date + "';";
        return db.rawQuery(query, null);
    }

    public Cursor fetchBetweenDayData(String id1, String id2){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DAY_RECORD_TABLE + " WHERE " + COLUMN_ID + " BETWEEN " + id1 + " AND " + id2 + ";";
        return db.rawQuery(query, null);
    }

    public Cursor fetchMonthDayData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAY_RECORD_TABLE + " WHERE " + COLUMN_DATE + " LIKE'%" + date + "%';";
        return db.rawQuery(query, null);
    }

    // with manual date feed

    public String deletePatientRow(String id, String date, String srno, int t){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            float res = -1;
            ContentValues pcv=new ContentValues();

            pcv.put(COLUMN_SR_NO, id);
            pcv.put(COLUMN_CHECKED, "no");
            pcv.put(COLUMN_TYPE, "none");
            pcv.put(COLUMN_COLOR, Integer.parseInt(String.valueOf(R.color.white2)));
            pcv.put(COLUMN_DATE, date);
            pcv.put(COLUMN_TIME, "--:--:-- --");
            pcv.put(COLUMN_AMOUNT, 0);
            pcv.put(COLUMN_NAME, "--");
            pcv.put(COLUMN_AGE, 0);
            res = db.update(PATIENT_TABLE, pcv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

            if(res==-1)
                return "Failed";
            else
                return  "Successfully deleted";
        } catch (Exception ex){
            return ex.getMessage();
        }
    }

    public void deleteDayRecord(String type, String amount, String date){

        if(type == "paper_valid_emergency"){
            type = "emergency_paper_valid";
        }

        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();

            int total_amount = senderCell(COLUMN_TOTAL_AMOUNT_COLLECTED, date) - Integer.parseInt(amount);
            int type_count = senderCell(type, date) - 1;
            int total_patients = senderCell(COLUMN_TOTAL_PATIENTS, date) - 1;

            cv.put(COLUMN_TOTAL_AMOUNT_COLLECTED, total_amount);
            cv.put(type, type_count);
            cv.put(COLUMN_TOTAL_PATIENTS, total_patients);

            db.update(DAY_RECORD_TABLE, cv, COLUMN_DATE + "=?", new String[]{String.valueOf(date)});
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readPatientData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+PATIENT_TABLE+" WHERE "+COLUMN_DATE+" = '" + date + "'";
        Cursor cursor = db.rawQuery(qry,null);
        return  cursor;
    }

    public Cursor readOnePatientData(String srno, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM "+PATIENT_TABLE+" WHERE "+COLUMN_DATE+" = '" + date + "' AND " + COLUMN_SR_NO + " ='" + srno+"'";
        Cursor cursor = db.rawQuery(qry,null);
        return  cursor;
    }

    public void updatePatientDetail(int _id, int sr_no, String type, int color, String name, String age, int amount, String date){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        try{
            cv.put(COLUMN_SR_NO,sr_no);
            cv.put(COLUMN_CHECKED,"yes");
            cv.put(COLUMN_TYPE, type);
            cv.put(COLUMN_COLOR, color);
            cv.put(COLUMN_DATE, date);
            cv.put(COLUMN_TIME, this.getTime());
            cv.put(COLUMN_AMOUNT, amount);
            cv.put(COLUMN_NAME, name);
            cv.put(COLUMN_AGE, age);
            db.update(PATIENT_TABLE, cv, COLUMN_ID + "=?", new String[]{String.valueOf(_id)});
        } catch (Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDayRecord(String type, int amount, String date){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        int total_amount = senderCell(COLUMN_TOTAL_AMOUNT_COLLECTED, date) + amount;
        int type_count = senderCell(type, date) + 1;
        int total_patients = senderCell(COLUMN_TOTAL_PATIENTS, date) + 1;

        cv.put(COLUMN_TOTAL_AMOUNT_COLLECTED, total_amount);
        cv.put(type, type_count);
        cv.put(COLUMN_TOTAL_PATIENTS, total_patients);

        db.update(DAY_RECORD_TABLE, cv, COLUMN_DATE + "=?", new String[]{String.valueOf(date)});
    }

    @SuppressLint("Range")
    public int senderCell(String column_name, String date){
        int rv = -1;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + column_name + " from " + DAY_RECORD_TABLE + " where " + COLUMN_DATE + "=?", new String[]{date});
        if(cursor.moveToNext()){
            rv = (int) cursor.getLong(cursor.getColumnIndex(column_name));
        }
        return rv;
    }

    public String makePDF(Cursor cursor) throws DocumentException, FileNotFoundException {
        // Create a new document
        Document document = new Document();
        // Create a new file in the "Documents" directory with a unique name
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MyPDF_" + System.currentTimeMillis() + ".pdf");

        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

// Open the document for writing
        document.open();

// Add a title to the document
        document.add(new Paragraph("My PDF"));


// Iterate through the cursor and add each row to the PDF as a table
        if (cursor != null && cursor.moveToFirst()) {
            // Create a new table with the number of columns in the cursor
            PdfPTable table = new PdfPTable(cursor.getColumnCount());

            // Add the table headers
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                table.addCell(cursor.getColumnName(i));
            }

            // Add the table rows
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    table.addCell(cursor.getString(i));
                }
            } while (cursor.moveToNext());

            // Add the table to the document
            document.add(table);
        }

        cursor.close();
        document.close();

        return pdfFile.toString();
    }
}
