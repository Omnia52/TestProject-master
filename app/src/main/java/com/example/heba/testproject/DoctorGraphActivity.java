package com.example.heba.testproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class DoctorGraphActivity extends AppCompatActivity {
    BarChart barChart;
    String courseCode, evalType;
    private String dirpath;
    private static final int REQUEST_STORAGE=225;
    private static final int TAT_STORAGE=2;
    //ImageView savePdf;

    PermissionUri permissionUri;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_graph);
        permissionUri = new PermissionUri(this);

        final ArrayList<String> yList,xList;

        barChart = (BarChart) findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries= new ArrayList<>();

//        savePdf=(ImageView) findViewById(R.id.saveGraphBtn);

        yList = makeList(SharedPrefManager.getmInstance(this).getyValues());
        xList = makeList(SharedPrefManager.getmInstance(this).getxValues());
        Float yvalues[]=new Float[yList.size()];

        for (int i=0;i<yList.size();i++) {
            yvalues[i]=Float.parseFloat(yList.get(i));
        }


        for(int i=0;i<yvalues.length;i++){
            barEntries.add(new BarEntry(i,yvalues[i]));
        }

        courseCode=SharedPrefManager.getmInstance(this).getCourseCode();
        evalType =SharedPrefManager.getmInstance(this).getEvalType();
        BarDataSet barDataSet = new BarDataSet(barEntries,courseCode+" "+ evalType +" Exam Evaluation Graph");

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(.9f);

        XAxis xAxis=barChart.getXAxis();

        xAxis.setValueFormatter(new MyFormat(xList));
        //xAxis.setGranularity(1);
        xAxis.setAxisMinimum(-.5f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);

        YAxis yAxis=barChart.getAxisLeft();
        yAxis.setLabelCount(5);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(5);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setData(barData);



    //    barChart.buildDrawingCache();


        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will
//        // be null
//        barChart.setDrawingCacheEnabled(true);
//
//        barChart.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        barChart.layout(0, 0, barChart.getMeasuredWidth(), barChart.getMeasuredHeight());
//        Bitmap bitmap=Bitmap.createBitmap(barChart.getDrawingCache());
//        barChart.setDrawingCacheEnabled(false);
//        Log.v("BitMap",bitmap.toString());
        //isStoragePermissionGranted();

        /*dirpath=android.os.Environment.getExternalStorageDirectory().toString();
        Document document=new Document();

        try {
            //String dirpath=android.os.Environment.getExternalStorageDirectory().toString();
            File file=new File(dirpath,courseCode+".pdf");
            FileOutputStream fout=new FileOutputStream(file);
            PdfWriter.getInstance(document,fout);
            document.open();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);
            document.add(myImg);
            Log.v("Document1",document.toString());
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();*/


        /*String dirpath=android.os.Environment.getExternalStorageDirectory().toString();
        try {
            PdfWriter.getInstance(document,new FileOutputStream(dirpath+"/"+courseCode+".pdf")); //  Change pdf's name.
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        Image img = null;  // Change image's name and extension.
        try {
//            img = Image.getInstance(dirpath+"/"+"example.jpg");
            img =Image.getInstance(bitmap.getNinePatchChunk());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / img.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
        img.scalePercent(scaler);
        img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

        /*

        try {
            Image image=Image.getInstance(bitmap.getNinePatchChunk());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        //img.setAlignment(Image.LEFT| Image.TEXTWRAP);

 /* float width = document.getPageSize().width() - document.leftMargin() - document.rightMargin();
 float height = document.getPageSize().height() - document.topMargin() - document.bottomMargin();
 img.scaleToFit(width, height)*/
        /*try {
            document.add(img);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();*/


    }

    private void saveGraph(){

        if(checkPermission(TAT_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                showPermissionExplanation(TAT_STORAGE);
            }
            else if(!permissionUri.checkPermission("storage")){
                requestPermission(TAT_STORAGE);
                permissionUri.updatePermission("storage");
            }
            else
            {
                MyToast.viewToast("Please Allow the storage permission",this);
                Intent intent= new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
        else {
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            dirpath=android.os.Environment.getExternalStorageDirectory().getAbsolutePath()+"/Dir";
            barChart.setDrawingCacheEnabled(true);

            Bitmap bitmap=Bitmap.createBitmap(barChart.getDrawingCache());

            barChart.setDrawingCacheEnabled(false);
            Log.v("BitMap",bitmap.toString());

            Document document = new Document();

            try {
                File dir = new File(dirpath);
                if(!dir.exists())
                    dir.mkdirs();
                File file = new File(dir, courseCode + ".pdf");

                FileOutputStream fout = new FileOutputStream(file);
                Log.v("FOUT",file.getPath());
                PdfWriter.getInstance(document, fout);
                document.open();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                Image myImg = Image.getInstance(stream.toByteArray());
                myImg.scalePercent(75,100);

                document.add(myImg);
                MyToast.viewToast("Saved !",this);

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.close();
            viewPdf(courseCode+".pdf","Dir");
            progressDialog.dismiss();
        }
    }

    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Log.v("PATHS",Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            MyToast.viewToast( "Can't read pdf file", this);
        }
    }

    private ArrayList<String> makeList(String list){
        ArrayList<String> temp=new ArrayList<>();
        String [] array=list.split(",");
        for(int i=0;i<array.length;) {
            String  question =array[i++].trim();
            temp.add(question);
        }
        return temp;
    }
    private class MyFormat implements IAxisValueFormatter {
        ArrayList<String> xData;

        public MyFormat(ArrayList<String> xData) {
            this.xData = xData;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int intValue=(int)value;
            if (xData.size() > intValue && intValue >= 0)
                return xData.get((int)value);
            return "";
        }
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission

            barChart.buildDrawingCache();
            barChart.setDrawingCacheEnabled(true);
            Bitmap bitmap = barChart.getDrawingCache();
            dirpath=android.os.Environment.getExternalStorageDirectory().toString();

            Document document = new Document();

            try {
                File file = new File(dirpath, courseCode + ".pdf");
                FileOutputStream fout = new FileOutputStream(file);
                PdfWriter.getInstance(document, fout);
                document.open();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.MIDDLE);
                document.add(myImg);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.close();
        }
    }
*/
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TRUE","Permission is granted");
                return true;
            } else {

                Log.v("FALSE","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("GRANTED","Permission is granted");
            return true;
        }
    }

    private int checkPermission(int permission){
        int status=PackageManager.PERMISSION_DENIED;
        switch (permission) {
            case TAT_STORAGE:
                status= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
        return status;
    }

    private void requestPermission(int permission){
        switch (permission) {
            case TAT_STORAGE:
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_STORAGE);
                break;
        }
    }

    private void showPermissionExplanation(final int permission){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        if(permission==TAT_STORAGE){
            builder.setMessage("This app need to write to your storage .. please allow!");
            builder.setTitle("Storage Permission needed..");
        }
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermission(TAT_STORAGE);
            }
        });
        builder.setNegativeButton("Canel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.save_icon:
                saveGraph();
                break;
        }
        return true;
    }
}
