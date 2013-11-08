package com.andre.p_db;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	Button btnl, btnr, btna;
	EditText et;
	TextView tv;
	int pos = 0;
	Cursor c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnl = (Button) findViewById(R.id.btnl);
		btnr = (Button) findViewById(R.id.btnr);
		btna = (Button) findViewById(R.id.btna);

		btnl.setOnClickListener(this);
		btnr.setOnClickListener(this);
		btna.setOnClickListener(this);
		et = (EditText) findViewById(R.id.et1);
		tv = (TextView) findViewById(R.id.tv1);
		dbh = new DBH(this);

		echo(pos);
		btna.setEnabled(false);
		
	}

	public boolean echo(int pos) {
		this.isClose();
		c = dbh.getWritableDatabase().rawQuery(
				"SELECT id, fraza FROM mytable ", null);
		if (c.getCount() > 0) {
			c.moveToFirst();
			et.setText(c.getString(1));
			tv.setText(c.getString(0));
		
			pos = 1;
		} else {
			pos = 0;
		}
		return true;
	}

	private void isClose(){
		if ((c != null) && (c.isClosed() == false))
			c.close();
	}
	public void onDestroy() {
		this.isClose();
		if (db.isOpen())
			db.close();
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
			btna.setEnabled(false);
			// cv.put("fraza", et.getText().toString());
			// db.insert("mytable", null, cv);
			break;
		case R.id.btnr:
			if ((c != null) && (c.getCount() > (pos + 1))) {
				pos++;
				c.moveToNext();
				tv.setText(c.getString(0));
				et.setText(c.getString(1));
		

			}
			break;
		case R.id.btnl:
			if ((c != null) && (pos >= 1)) {
				pos--;
				c.moveToPrevious();
				tv.setText(c.getString(0));
				et.setText(c.getString(1));
		
			}
			break;
		}
		;
	}
}
