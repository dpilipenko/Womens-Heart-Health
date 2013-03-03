package com.example.womenshearthealth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.database.Cursor;
import android.widget.Toast;

public class SQLDatabaseHelper {

	
	private Activity activity;
	private METSDBAdapter db;

	public SQLDatabaseHelper (Activity activity)
	{
		this.activity = activity;
        this.db = new METSDBAdapter(activity);
        initMETSDB();
	}
	
	private void initMETSDB() {

		try {

            String destPath = "/data/data/" + this.activity.getPackageName() +
                "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//Copy db from assets folder to the databases folder
                CopyDB(activity.getBaseContext().getAssets().open("METS"),
                    new FileOutputStream(destPath + "/METS"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
	}
	
	public void displayAllMETs()
	{
		db.open();
		
        Cursor c = db.getAllMETS();
        if (c.moveToFirst())
        {
            do {
                DisplayMET(c);
            } while (c.moveToNext());
        }
        db.close();
    }
    
    private void CopyDB(InputStream inputStream, 
    OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    public void DisplayMET(Cursor c)
    {
        Toast.makeText(activity,
                "id: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "METS Amount:  " + c.getInt(2) + "\n" +
                "Date:  " + c.getString(3),
                Toast.LENGTH_LONG).show();
    }
	
    public void addMET(String name, int METS, String date) {
    db.open();
    long id = db.insertMETSActivity(name, METS, date);
    db.close();
    }
	
	
}
