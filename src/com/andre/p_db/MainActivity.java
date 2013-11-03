package com.andre.p_db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	DBH dbh;
	SQLiteDatabase db;
	ContentValues cv;

	Button btnl, btnr, btna, btnread;
	EditText et;
	TextView tv, tvm;
	int ind1, ind2 = 0;
	String[][] d;
    int pos =1;
    int maxpos=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnl = (Button) findViewById(R.id.btnl);
		btnr = (Button) findViewById(R.id.btnr);
		btna = (Button) findViewById(R.id.btna);
		btnread = (Button) findViewById(R.id.button1);
		btnread.setOnClickListener(this);
		btnl.setOnClickListener(this);
		btnr.setOnClickListener(this);
		btna.setOnClickListener(this);
		et = (EditText) findViewById(R.id.et1);
		tv = (TextView) findViewById(R.id.tv1);
		tvm = (TextView) findViewById(R.id.tvm);
		dbh = new DBH(this);
		db = dbh.getWritableDatabase();
		cv = new ContentValues();
        echo(pos);
	}

	public boolean echo(int pos) {
		Cursor c;
		c = db.query("mytable", null, null, null, null, null, null);
		c.moveToPosition(pos);
		et.setText(c.getString(0) + " - " + c.getString(1));
		tv.setText(String.valueOf(pos+1));
		maxpos = c.getCount();
		tvm.setText(String.valueOf(maxpos));
		c.close();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.btna:
			cv.put("fraza", et.getText().toString());
			db.insert("mytable", null, cv);
			break;
		case R.id.button1:// read all data
			Cursor c = db.query("mytable", null, null, null, null, null, null);
			if (c.moveToFirst()) {
				do {
					et.append(c.getString(0) + " - " + c.getString(1) + "\n");

				} while (c.moveToNext());
			}
			c.close();
			break;
		case R.id.btnr:
			
			if (pos<maxpos-1){
				pos++;
				echo(pos);
			}
			
			break;
		case R.id.btnl:
			
			if (pos>0){
				pos--;
				echo(pos);
			}
			
			break;

		}
		;
	}
}

class DBH extends SQLiteOpenHelper {
	public DBH(Context context) {
		super(context, "myDB", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table mytable (id integer primary key autoincrement, fraza text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}