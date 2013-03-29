package com.example.womenshearthealth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import android.app.Activity;

public class SQLDatabaseHelper {

	
	private Activity activity;
	private METSDBAdapter db;
	
	public SQLDatabaseHelper (Activity activity)
	{
		this.activity = activity;
        this.db = new METSDBAdapter(activity);
        initMETSDB();
	}
	
	public void saveMetActivity(MetActivity activity) {
    	Date date = new Date();
    	db.addMetActivity(activity, date);
    }
	
	public List<MetActivity> getAllMetActivities() {
		List<MetActivity> list = db.getAllMetActivities();
		return list;
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

}
