package anish.tutorial.mobilewallet;

import android.content.pm.ActivityInfo;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    TextView add, use;
    MaterialTextView cb, pb;
    TextInputEditText amt, remarks;
    DBClass db;
    String rem, amts;
    int amtl;
    String currentDateandTime;
    RecyclerView list;
    List<ColumnNames> col = new ArrayList<>();
    List<ColumnNames> cols = new ArrayList<>();
    RelativeLayout layout;
    RelativeLayout header;
    ImageButton sendtoXcel;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        use = findViewById(R.id.use);
        amt = findViewById(R.id.addAmt);
        remarks = findViewById(R.id.remarksC);
        cb = findViewById(R.id.currentb);
        pb = findViewById(R.id.previousb);
        list = findViewById(R.id.statements);
        layout = findViewById(R.id.states);
        header = findViewById(R.id.header);
        sendtoXcel = findViewById(R.id.sendToX);

        list.hasFixedSize();

        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        db = new DBClass(getApplicationContext());
        int cc = db.currentBalance();
        int pp = db.previousBalance();
        String pbs = "Rs " + pp;
        String cbs = "Rs " + cc
;        //    Toast.makeText(this, "ss" + cc + pp , Toast.LENGTH_SHORT).show();
        cb.setText(cbs);
        pb.setText(pbs);


        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G-ad 'at' HH:mm:ss z->gmt");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss "); //newly used
        currentDateandTime = sdf.format(new Date());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amts = amt.getText().toString();
                rem = remarks.getText().toString();
                amtl = amts.length();


                if (amtl > 1) {
                    db.addBtn(parseInt(amts), 0, currentDateandTime, rem);
                    cb.setText(String.valueOf(db.currentBalance()));
                    pb.setText(String.valueOf(db.previousBalance()));
                    amt.setText("");
                    remarks.setText("");

                    layout.setVisibility(View.GONE);


                }
                if (amtl <= 1) {
                    Toast.makeText(MainActivity.this, "Value must be more than 1 digit", Toast.LENGTH_SHORT).show();
                }

            }
        });
        sendtoXcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String directory_path = Environment.getExternalStorageDirectory().getPath()
                        + "/Backup";
                File file = new File(directory_path);
                if (!file.exists()){
                    file.mkdirs();
                }

                SQLiteToExcel sqLiteToExcel
                        = new SQLiteToExcel(getApplicationContext(),
                        DBClass.DATABASE_NAME,
                        directory_path);
                sqLiteToExcel.exportAllTables("addStatement.xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                        //    Toast.makeText(MainActivity.this, "starting", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCompleted(String filePath) {
                        Toast.makeText(MainActivity.this, "upload Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "error updating " + e, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amts = amt.getText().toString();
                rem = remarks.getText().toString();
                amtl = amts.length();

                if (amtl > 1) {
                    db.useBtn(parseInt(amts), 1, currentDateandTime, rem);

                    cb.setText(String.valueOf(db.currentBalance()));
                    pb.setText(String.valueOf(db.previousBalance()));
                    amt.setText("");
                    remarks.setText("");

                    layout.setVisibility(View.GONE);

                }
                if (amtl <= 1) {
                    Toast.makeText(MainActivity.this, "Value must be more than 1 digit", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        header.setVisibility(View.GONE);
        if (item.getItemId() == R.id.addStatement) {
          //  Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            layout.setVisibility(View.VISIBLE);
            col.clear();
            cols.clear();
            col = db.getAllDatas(0);
           // Toast.makeText(this, col.toString(), Toast.LENGTH_SHORT).show();
            ListAdapters adapters = new ListAdapters( col,getApplicationContext());
            list.setAdapter(adapters);



        }
        if (item.getItemId() == R.id.usedStatement) {
          //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            layout.setVisibility(View.VISIBLE);
            cols.clear();
            col.clear();
            cols = db.getAllDatas(1);
            ListAdapters adapters = new ListAdapters(cols, getApplicationContext());
            list.setAdapter(adapters);
        }
        if (item.getItemId() == R.id.allStatement){
           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            layout.setVisibility(View.VISIBLE);
            cols.clear();
            col.clear();
            cols = db.getAllDatas();
            ListAdapters adapters = new ListAdapters(cols, getApplicationContext());
            list.setAdapter(adapters);
        }
        if (item.getItemId() == R.id.addV){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            layout.setVisibility(View.GONE);
            header.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExit = false;

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layout.setVisibility(View.GONE);
        header.setVisibility(View.VISIBLE);
       // System.exit(1);
        if (doubleBackToExit){
            super.onBackPressed();
        }
        this.doubleBackToExit = true;
        Toast.makeText(getApplication(), "Press Twice to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit = false;
            }
        },2000);

    }
}
