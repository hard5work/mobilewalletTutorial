package anish.tutorial.mobilewallet;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    TextView add, use;
    MaterialTextView cb, pb, texter;
    TextInputEditText amt, remarks;
    DBClass db;
    String rem, amts;
    int amtl;
    ArrayList<String> tablename = new ArrayList<>();
    int count = 0;
    String currentDateandTime;
    RecyclerView list;
    List<ColumnNames> col = new ArrayList<>();
    List<ColumnNames> cols = new ArrayList<>();
    RelativeLayout layout, aboutApp;
    RelativeLayout header;
    FloatingActionButton rotator;
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
        rotator = findViewById(R.id.rotator);
        aboutApp = findViewById(R.id.aboutApp);
        texter = findViewById(R.id.texter);

        list.hasFixedSize();

        tablename.add(DBClass.CURRENT_BALANCE);
        tablename.add(DBClass.PREVIOUS_BALANCE);
        tablename.add(DBClass.AD_US_BLNCE);
        tablename.add(DBClass.EDATE);
        tablename.add(DBClass.REMARKS);

        list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        db = new DBClass(getApplicationContext());
        int cc = db.currentBalance();
        int pp = db.previousBalance();
        String pbs = "Rs " + pp;
        String cbs = "Rs " + cc;        //    Toast.makeText(this, "ss" + cc + pp , Toast.LENGTH_SHORT).show();
        cb.setText(cbs);
        pb.setText(pbs);


        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G-ad 'at' HH:mm:ss z->gmt");
        try {
            //  String c  = Calendar.getInstance().getTime().toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss "); //newly used
            currentDateandTime = sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amts = amt.getText().toString();
                rem = remarks.getText().toString();
                amtl = amts.length();


                if (amtl > 1) {
                    try {
                        db.addBtn(parseInt(amts), 0, currentDateandTime, rem);
                    }catch (Exception e){
                        Log.e("add error", e.toString());
                        Toast.makeText(MainActivity.this, "Long value Cannot be added", Toast.LENGTH_SHORT).show();
                    }
                  //  cb.setText(String.valueOf(db.currentBalance()));
                   // pb.setText(String.valueOf(db.previousBalance()));
                    String ccb = "Rs " + db.currentBalance();
                    cb.setText(ccb);
                    String ppb = "Rs " + db.previousBalance();
                    pb.setText(ppb);
                    amt.setText("");
                    remarks.setText("");

                    layout.setVisibility(View.GONE);


                }
                if (amtl <= 1) {
                    Toast.makeText(MainActivity.this, "Value must be more than 1 digit", Toast.LENGTH_SHORT).show();
                }

            }
        });
       /* sendtoXcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String directory_path = Environment.getExternalStorageDirectory().getPath()
                        + "/Download";
                File file = new File(directory_path);
                if (!file.exists()) {
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
                        Toast.makeText(MainActivity.this, "upload Success " + filePath, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "error updating " + e, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });*/

        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amts = amt.getText().toString();
                rem = remarks.getText().toString();
                amtl = amts.length();

                if (amtl > 1) {
                    try {
                        db.useBtn(parseInt(amts), 1, currentDateandTime, rem);
                    }catch (Exception e){
                        Log.e("parseError",e.toString());
                        Toast.makeText(MainActivity.this, "Long value Cannot be used", Toast.LENGTH_SHORT).show();

                    }
                    String ccb = "Rs " + db.currentBalance();
                    cb.setText(ccb);
                    String ppb = "Rs " + db.previousBalance();
                    pb.setText(ppb);
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

        //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        header.setVisibility(View.GONE);
        aboutApp.setVisibility(View.GONE);
        final int orientation = getResources().getConfiguration().orientation;


        if (item.getItemId() == R.id.addStatement) {
            //  Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            layout.setVisibility(View.VISIBLE);


            col.clear();
            cols.clear();
            col = db.getAllDatas(0);
            // Toast.makeText(this, col.toString(), Toast.LENGTH_SHORT).show();
            ListAdapters adapters = new ListAdapters(col, MainActivity.this);
            list.setAdapter(adapters);
            sendtoXcel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String directory_path = Environment.getExternalStorageDirectory().getPath()
                            + "/Download";
                    File file = new File(directory_path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    SQLiteToExcel sqLiteToExcel
                            = new SQLiteToExcel(getApplicationContext(),
                            DBClass.DATABASE_NAME,
                            directory_path);
                    sqLiteToExcel.exportSingleTable(DBClass.TABLE_NAME,"addStatement.xls", new SQLiteToExcel.ExportListener() {
                        @Override
                        public void onStart() {
                            //    Toast.makeText(MainActivity.this, "starting", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCompleted(String filePath) {
                            Toast.makeText(MainActivity.this, "upload Success " + filePath, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(MainActivity.this, "error updating " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            rotator.setVisibility(View.VISIBLE);
            rotator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    count++;

                    if (count == 1) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else if (count == 2) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        count = 0;
                    }


                }
                   /* if (orientation == Configuration.ORIENTATION_PORTRAIT){

                    }
                    if(orientation == Configuration.ORIENTATION_LANDSCAPE){

                    }*/


            });


        }
        if (item.getItemId() == R.id.usedStatement) {
            //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            layout.setVisibility(View.VISIBLE);
            cols.clear();
            col.clear();
            cols = db.getAllDatas(1);
            ListAdapters adapters = new ListAdapters(cols, MainActivity.this);
            list.setAdapter(adapters);
            sendtoXcel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String directory_path = Environment.getExternalStorageDirectory().getPath()
                            + "/Download";
                    File file = new File(directory_path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    SQLiteToExcel sqLiteToExcel
                            = new SQLiteToExcel(getApplicationContext(),
                            DBClass.DATABASE_NAME,
                            directory_path);

                    sqLiteToExcel.exportSingleTable(DBClass.TABLE_NAME, "usedStatement.xls", new SQLiteToExcel.ExportListener() {
                        @Override
                        public void onStart() {
                            //    Toast.makeText(MainActivity.this, "starting", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCompleted(String filePath) {
                            Toast.makeText(MainActivity.this, "upload Success " + filePath, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(MainActivity.this, "error updating " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            rotator.setVisibility(View.VISIBLE);
            rotator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;

                    if (count == 1) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else if (count == 2) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        count = 0;
                    }

                }
            });
        }
        if (item.getItemId() == R.id.allStatement) {
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            layout.setVisibility(View.VISIBLE);
            cols.clear();
            col.clear();
            cols = db.getAllDatas();
            ListAdapters adapters = new ListAdapters(cols, MainActivity.this);
            list.setAdapter(adapters);
            sendtoXcel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String directory_path = Environment.getExternalStorageDirectory().getPath()
                            + "/Download";
                    File file = new File(directory_path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    SQLiteToExcel sqLiteToExcel
                            = new SQLiteToExcel(getApplicationContext(),
                            DBClass.DATABASE_NAME,
                            directory_path);
                    sqLiteToExcel.exportSingleTable(DBClass.TABLE_NAME,"allstatement.xls", new SQLiteToExcel.ExportListener() {
                        @Override
                        public void onStart() {
                            //    Toast.makeText(MainActivity.this, "starting", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCompleted(String filePath) {
                            Toast.makeText(MainActivity.this, "upload Success " + filePath, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(MainActivity.this, "error updating " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            rotator.setVisibility(View.VISIBLE);
            rotator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;

                    if (count == 1) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else if (count == 2) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        count = 0;
                    }

                }
            });
        }
        if (item.getItemId() == R.id.addV) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            layout.setVisibility(View.GONE);
            header.setVisibility(View.VISIBLE);
            rotator.setVisibility(View.GONE);
            aboutApp.setVisibility(View.GONE);
        }
        if (item.getItemId() == R.id.about) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            layout.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
            rotator.setVisibility(View.GONE);
            aboutApp.setVisibility(View.VISIBLE);
            String txt = "CB = Current Balance \n" +
                    "PB = Previous Balance \n" +
                    "ADD = Money Received \n " +
                    "USE = Money Used \n" +
                    "REMARKS = Comment for Money Used or Added \n" +
                    "ADD = Enter Amount to Use or Add ";
            texter.setText(txt);

        }
        if (item.getItemId()==R.id.resetDate){
            layout.setVisibility(View.GONE);
            header.setVisibility(View.VISIBLE);
            rotator.setVisibility(View.GONE);
            aboutApp.setVisibility(View.GONE);
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Reset Data");
            builder.setIcon(android.R.drawable.ic_delete);
            builder.setMessage("Are You Sure You Wanna Reset? ");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBClass db = new DBClass(MainActivity.this);
                    db.deleteTable();
                    Toast.makeText(MainActivity.this, "Data Erased", Toast.LENGTH_SHORT).show();
                    layout.setVisibility(View.GONE);
                    header.setVisibility(View.VISIBLE);
                    rotator.setVisibility(View.GONE);
                    aboutApp.setVisibility(View.GONE);
                    int cc = db.currentBalance();
                    int pp = db.previousBalance();
                    String pbs = "Rs " + pp;
                    String cbs = "Rs " + cc;        //    Toast.makeText(this, "ss" + cc + pp , Toast.LENGTH_SHORT).show();
                    cb.setText(cbs);
                    pb.setText(pbs);

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    layout.setVisibility(View.GONE);
                    header.setVisibility(View.VISIBLE);
                    rotator.setVisibility(View.GONE);
                    aboutApp.setVisibility(View.GONE);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(true);

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
        rotator.setVisibility(View.GONE);
        aboutApp.setVisibility(View.GONE);
        // System.exit(1);
        if (doubleBackToExit) {
            super.onBackPressed();
        }
        this.doubleBackToExit = true;
        Toast.makeText(getApplication(), "Press Twice to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit = false;
            }
        }, 2000);

    }
}
