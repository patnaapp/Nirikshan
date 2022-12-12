package bih.nic.in.Nirikshan.databasehelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import bih.nic.in.Nirikshan.entity.CommiteeDetails;
import bih.nic.in.Nirikshan.entity.ControlModel;
import bih.nic.in.Nirikshan.entity.GetCommitteList;
import bih.nic.in.Nirikshan.entity.InspectionFormModel;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    //private static String DB_PATH = "/data/data/bih.nic.in.ashwin/databases/";
    //private static String DB_NAME = "eCountingAC.sqlite";
    private static String DB_NAME = "Nirikshan.db";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    SQLiteDatabase db;

    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 2);
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist


        } else {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist() {

        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {
        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertProvisional(ArrayList<ControlModel> result) {

        long c = -1;
        try {
            // CREATE TABLE "BankList" ( `BankId` TEXT, `BankName` TEXT, `BankType` TEXT )

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("Delete from ControlModel");
            for (ControlModel remarks : result) {

                ContentValues values = new ContentValues();
                values.put("ControlModel_ID", remarks.getID());
                values.put("ControlModel_Value", remarks.getValue());
                values.put("ControlModel_Description", remarks.getDescription());
                c = db.insert("ControlModel", null, values);


            }
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        // return plantationList;
    }

    @SuppressLint("Range")
    public ArrayList<ControlModel> getDropDownList(String id) {
        ArrayList<ControlModel> bdetail = new ArrayList<ControlModel>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();


            //Cursor cur = db.rawQuery("SELECT ControlModel_ID,ControlModel_Value,ControlModel_Description FROM  ControlModel where ControlModel_ID='" + id.trim() + "' AND BlockCode='" + BlockCode + "'", null);
            String[] params = new String[]{id};
            Cursor cur = db.rawQuery("SELECT * from ControlModel WHERE ControlModel_ID=?", params);

            int x = cur.getCount();
            while (cur.moveToNext()) {
                ControlModel ps = new ControlModel();
                ps.setID(cur.getString(cur.getColumnIndex("ControlModel_ID")));
                ps.setValue((cur.getString(cur.getColumnIndex("ControlModel_Value"))));
                ps.setDescription((cur.getString(cur.getColumnIndex("ControlModel_Description"))));
                bdetail.add(ps);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bdetail;
    }

    public void insertTeamList(ArrayList<GetCommitteList> result,String User_id) {

        long c = -1;
        try {
            // CREATE TABLE "BankList" ( `BankId` TEXT, `BankName` TEXT, `BankType` TEXT )

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("Delete from CommitteList");
            for (GetCommitteList remarks : result) {

                ContentValues values = new ContentValues();
                values.put("ID", remarks.getID());
                values.put("Committee_ID", remarks.getCommittee_ID());
                values.put("Committee_Name", remarks.getCommitteeName());
                values.put("User_Id",User_id);
                c = db.insert("CommitteList", null, values);


            }
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        // return plantationList;
    }

    @SuppressLint("Range")//GetCommitteList
    public ArrayList<GetCommitteList> getTeamList(String user_id) {
        ArrayList<GetCommitteList> bdetail = new ArrayList<GetCommitteList>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();


            //Cursor cur = db.rawQuery("SELECT ControlModel_ID,ControlModel_Value,ControlModel_Description FROM  ControlModel where ControlModel_ID='" + id.trim() + "' AND BlockCode='" + BlockCode + "'", null);
            String[] params = new String[]{user_id};
            Cursor cur = db.rawQuery("SELECT * from CommitteList WHERE User_Id=?", params);

            int x = cur.getCount();
            while (cur.moveToNext()) {
                GetCommitteList ps = new GetCommitteList();
                ps.setID(cur.getString(cur.getColumnIndex("ID")));
                ps.setCommittee_ID((cur.getString(cur.getColumnIndex("Committee_ID"))));
                ps.setCommitteeName((cur.getString(cur.getColumnIndex("Committee_Name"))));
                ps.setUserID((cur.getString(cur.getColumnIndex("User_Id"))));
                bdetail.add(ps);
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bdetail;
    }

//    public void insertTeamDetails(CommiteeDetails result) {
//
//        long c = -1;
//        try {
//            // CREATE TABLE "BankList" ( `BankId` TEXT, `BankName` TEXT, `BankType` TEXT )
//
//            SQLiteDatabase db = this.getWritableDatabase();
//            //db.execSQL("Delete from TeamDetails");
//
//                ContentValues values = new ContentValues();
//                values.put("Committee_ID", result.getCommittee_ID());
//                values.put("CommitteeName", result.getCommitteeName());
//                values.put("User_Name", result.getUser_Name());
//                values.put("UserID", result.getUserID());
//                values.put("Open_Date", result.getOpen_Date());
//                values.put("Time", result.getTime());
//                values.put("Dist_Code", result.getDist_Code());
//                values.put("Dist_Name", result.getDist_Name());
//                values.put("Block_Code", result.getBlock_Code());
//                values.put("Block_Name", result.getBlock_Name());
//                values.put("Panch_Code", result.getPanch_Code());
//                values.put("Panch_Name", result.getPanch_Name());
//                values.put("No_Of_Member", result.getNo_Of_Member());
//                values.put("From_Date", result.getFrom_Date());
//                values.put("To_Date", result.getTo_Date());
//                values.put("Duration", result.getDuration());
//                values.put("Location", result.getLocation());
//                values.put("IsFinalize", result.getIsFinalize());
//                c = db.insert("TeamDetails", null, values);
//
//            db.close();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO: handle exception
//        }
//        // return plantationList;
//    }

    public long insertTeamDetails(CommiteeDetails result) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("Committee_ID", result.getCommittee_ID());
            values.put("CommitteeName", result.getCommitteeName());
            values.put("User_Name", result.getUser_Name());
            values.put("UserID", result.getUserID());
            values.put("Open_Date", result.getOpen_Date());
            values.put("Time", result.getTime());
            values.put("Dist_Code", result.getDist_Code());
            values.put("Dist_Name", result.getDist_Name());
            values.put("Block_Code", result.getBlock_Code());
            values.put("Block_Name", result.getBlock_Name());
            values.put("Panch_Code", result.getPanch_Code());
            values.put("Panch_Name", result.getPanch_Name());
            values.put("No_Of_Member", result.getNo_Of_Member());
            values.put("From_Date", result.getFrom_Date());
            values.put("To_Date", result.getTo_Date());
            values.put("Duration", result.getDuration());
            values.put("Location", result.getLocation());
            values.put("IsFinalize", result.getIsFinalize());

            String[] whereArgs = new String[]{result.getCommittee_ID()};

            c = db.update("TeamDetails", values, "Committee_ID=? ", whereArgs);

            if (!(c > 0)) {

                c = db.insert("TeamDetails", null, values);
                //c = db.insertWithOnConflict("UserLogin", null, values,SQLiteDatabase.CONFLICT_REPLACE);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;

    }


    @SuppressLint("Range")
    public CommiteeDetails getTeamDetails(String team_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] params = new String[]{team_id};
        //Cursor cur = db.rawQuery("SELECT * from TeamDetails WHERE Committee_ID=?", params);
        //Cursor cur = db.rawQuery("SELECT * from TeamDetails", null);
        Cursor cur= getReadableDatabase().rawQuery("SELECT * FROM TeamDetails WHERE Committee_ID=?",params);

        //Cursor cur = db.rawQuery("SELECT *  FROM TeamDetails where Committee_ID = '" + team_id + "'", null);
        CommiteeDetails progress = new CommiteeDetails();


        try {
            if (cur.moveToNext()) {
                progress.setCommittee_ID(cur.isNull(cur.getColumnIndex("Committee_ID")) == false ? cur.getString(cur.getColumnIndex("Committee_ID")) : "");
                progress.setCommitteeName(cur.isNull(cur.getColumnIndex("CommitteeName")) == false ? cur.getString(cur.getColumnIndex("CommitteeName")) : "");
                progress.setUser_Name(cur.isNull(cur.getColumnIndex("User_Name")) == false ? cur.getString(cur.getColumnIndex("User_Name")) : "");
                progress.setUserID(cur.isNull(cur.getColumnIndex("UserID")) == false ? cur.getString(cur.getColumnIndex("UserID")) : "");
                progress.setOpen_Date(cur.isNull(cur.getColumnIndex("Open_Date")) == false ? cur.getString(cur.getColumnIndex("Open_Date")) : "");
                progress.setTime(cur.isNull(cur.getColumnIndex("Time")) == false ? cur.getString(cur.getColumnIndex("Time")) : "");
                progress.setDist_Code(cur.isNull(cur.getColumnIndex("Dist_Code")) == false ? cur.getString(cur.getColumnIndex("Dist_Code")) : "");
                progress.setDist_Name(cur.isNull(cur.getColumnIndex("Dist_Name")) == false ? cur.getString(cur.getColumnIndex("Dist_Name")) : "");
                progress.setBlock_Code(cur.isNull(cur.getColumnIndex("Block_Code")) == false ? cur.getString(cur.getColumnIndex("Block_Code")) : "");
                progress.setBlock_Name(cur.isNull(cur.getColumnIndex("Block_Name")) == false ? cur.getString(cur.getColumnIndex("Block_Name")) : "");
                progress.setPanch_Code(cur.isNull(cur.getColumnIndex("Panch_Code")) == false ? cur.getString(cur.getColumnIndex("Panch_Code")) : "");
                progress.setPanch_Name(cur.isNull(cur.getColumnIndex("Panch_Name")) == false ? cur.getString(cur.getColumnIndex("Panch_Name")) : "");
                progress.setNo_Of_Member(cur.isNull(cur.getColumnIndex("No_Of_Member")) == false ? cur.getString(cur.getColumnIndex("No_Of_Member")) : "");
                progress.setFrom_Date(cur.isNull(cur.getColumnIndex("From_Date")) == false ? cur.getString(cur.getColumnIndex("From_Date")) : "");
                progress.setTo_Date(cur.isNull(cur.getColumnIndex("To_Date")) == false ? cur.getString(cur.getColumnIndex("To_Date")) : "");
                progress.setDuration(cur.isNull(cur.getColumnIndex("Duration")) == false ? cur.getString(cur.getColumnIndex("Duration")) : "");
                progress.setLocation(cur.isNull(cur.getColumnIndex("Location")) == false ? cur.getString(cur.getColumnIndex("Location")) : "");
                progress.setIsFinalize(cur.isNull(cur.getColumnIndex("IsFinalize")) == false ? cur.getString(cur.getColumnIndex("IsFinalize")) : "");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cur.close();
            db.close();
        }
        return progress;
    }

//    @SuppressLint("Range")
//    public InspectionFormModel getInspectionForm(String unit_id) {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] params = new String[]{unit_id};
//
//        Cursor cur = db.rawQuery("SELECT *  FROM InspectionForm where Unit_ID = '" + unit_id + "'", null);
//        InspectionFormModel progress = new InspectionFormModel();
//
//
//        try {
//            if (cur.moveToNext()) {
//                progress.setUnit_ID(cur.isNull(cur.getColumnIndex("Unit_ID")) == false ? cur.getString(cur.getColumnIndex("Unit_ID")) : "");
//                progress.setUnit_Name(cur.isNull(cur.getColumnIndex("Unit_Name")) == false ? cur.getString(cur.getColumnIndex("Unit_Name")) : "");
//                progress.setSubUnit_ID(cur.isNull(cur.getColumnIndex("SubUnit_ID")) == false ? cur.getString(cur.getColumnIndex("SubUnit_ID")) : "");
//                progress.setSubUnit_Name(cur.isNull(cur.getColumnIndex("SubUnit_Name")) == false ? cur.getString(cur.getColumnIndex("SubUnit_Name")) : "");
//                progress.setOprion_ID(cur.isNull(cur.getColumnIndex("Oprion_ID")) == false ? cur.getString(cur.getColumnIndex("Oprion_ID")) : "");
//                progress.setOption_Name(cur.isNull(cur.getColumnIndex("Option_Name")) == false ? cur.getString(cur.getColumnIndex("Option_Name")) : "");
//                progress.setControl_ID(cur.isNull(cur.getColumnIndex("Control_ID")) == false ? cur.getString(cur.getColumnIndex("Control_ID")) : "");
//
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            cur.close();
//            db.close();
//        }
//        return progress;
//    }
    public void insertInspectionForm(ArrayList<InspectionFormModel> result,String user_id) {

        long c = -1;
        try {
            // CREATE TABLE "BankList" ( `BankId` TEXT, `BankName` TEXT, `BankType` TEXT )

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("Delete from TeamDetails");
            for (InspectionFormModel remarks : result) {

                ContentValues values = new ContentValues();
                values.put("Unit_ID", remarks.getUnit_ID());
                values.put("Unit_Name", remarks.getUnit_Name());
                values.put("SubUnit_ID", remarks.getSubUnit_ID());
                values.put("SubUnit_Name", remarks.getSubUnit_Name());
                values.put("Oprion_ID", remarks.getOprion_ID());
                values.put("Option_Name", remarks.getOption_Name());
                values.put("Control_ID", remarks.getControl_ID());
                values.put("user_id", user_id);
                c = db.insert("InspectionForm", null, values);


            }
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        // return plantationList;
    }

    @SuppressLint("Range")
    public ArrayList<InspectionFormModel> getInspectionForm(String unit_id) {
        ArrayList<InspectionFormModel> basicdetail = new ArrayList<InspectionFormModel>();

        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String[] args = {unit_id};
            //Cursor cur = db.rawQuery("SELECT *  FROM InspectionForm where Unit_ID = '" + unit_id + "'", null);
            //Cursor cur = db.rawQuery("SELECT *  FROM InspectionForm Where Unit_ID=? ", args);
            Cursor cur= getReadableDatabase().rawQuery("SELECT * FROM InspectionForm WHERE Unit_ID=?",args);
            int x = cur.getCount();

            while (cur.moveToNext()) {
                InspectionFormModel basicInfo = new InspectionFormModel();
                basicInfo.setUnit_ID(cur.isNull(cur.getColumnIndex("Unit_ID")) == false ? cur.getString(cur.getColumnIndex("Unit_ID")) : "");
                basicInfo.setUnit_Name(cur.isNull(cur.getColumnIndex("Unit_Name")) == false ? cur.getString(cur.getColumnIndex("Unit_Name")) : "");
                basicInfo.setSubUnit_ID(cur.isNull(cur.getColumnIndex("SubUnit_ID")) == false ? cur.getString(cur.getColumnIndex("SubUnit_ID")) : "");
                basicInfo.setSubUnit_Name(cur.isNull(cur.getColumnIndex("SubUnit_Name")) == false ? cur.getString(cur.getColumnIndex("SubUnit_Name")) : "");
                basicInfo.setOprion_ID(cur.isNull(cur.getColumnIndex("Oprion_ID")) == false ? cur.getString(cur.getColumnIndex("Oprion_ID")) : "");
                basicInfo.setOption_Name(cur.isNull(cur.getColumnIndex("Option_Name")) == false ? cur.getString(cur.getColumnIndex("Option_Name")) : "");
                basicInfo.setControl_ID(cur.isNull(cur.getColumnIndex("Control_ID")) == false ? cur.getString(cur.getColumnIndex("Control_ID")) : "");


                //basicdetail.add(basicInfo);

                basicdetail.add(basicInfo);
            }
            cur.close();
            this.getReadableDatabase().close();
            sqLiteDatabase.close();


        } catch (Exception e) {
            e.printStackTrace();
            basicdetail = null;
            // TODO: handle exception

        }
        return basicdetail;
    }
}
