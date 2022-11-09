package bih.nic.in.fieldinspection.utilities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bih.nic.in.fieldinspection.R;


public class Utiilties {

    public Utiilties() {
        // TODO Auto-generated constructor stub
    }
    private static final String NUMERIC_STRING = "0123456789";
    public static long getDateDifferenceFromCurrentDate(String fdate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date fromDate = null, todayWithZeroTime=null;
        long daysDiff = 0;

        try {
            fromDate = dateFormat.parse(fdate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(fromDate != null){
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(fromDate);

            long msDiff = Calendar.getInstance().getTimeInMillis() - cCal.getTimeInMillis();
            daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
        }

        return (daysDiff);
    }
    //Used for hiding the keyboard after checking for focus on any view
    public static void hideKeyboard(Context context) {
        // Check if no view has focus:
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void ShowMessage(Context context, String Title, String Message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(Title);
        alertDialog.setMessage(Message);
        alertDialog.show();
    }

    public static void showAlet(final Context context){

        if (Utiilties.isOnline(context) == false) {
            AlertDialog.Builder ab = new AlertDialog.Builder(context);
            ab.setCancelable(false);
            ab.setTitle("Internet Connnection Error!!!");
            ab.setMessage("Please turn on your mobile data or wifi connection");
            ab.setPositiveButton("Turn On",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            GlobalVariables.isOffline = false;
                            Intent I = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            context.startActivity(I);
                        }
                    });
            ab.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {

                            GlobalVariables.isOffline = true;

                        }
                    });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.AppBaseTheme;

            ab.show();
        }else {

            GlobalVariables.isOffline = false;
            // new CheckUpdate().execute();
        }
    }


    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() == true);
    }

    public static Bitmap GenerateThumbnail(Bitmap imageBitmap,
                                           int THUMBNAIL_HEIGHT, int THUMBNAIL_WIDTH) {

        Float width = new Float(imageBitmap.getWidth());
        Float height = new Float(imageBitmap.getHeight());
        Float ratio = width / height;
        Bitmap CompressedBitmap = Bitmap.createScaledBitmap(imageBitmap,
                (int) (THUMBNAIL_HEIGHT * ratio), THUMBNAIL_WIDTH, false);

        return CompressedBitmap;
    }



    public static Bitmap DrawText(Activity activity, Bitmap mBitmap, String displaytext1, String displaytext2, String displaytext3, String displaytext4) {
        Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        // create a canvas on which to draw
        Canvas canvas = new Canvas(bmOverlay);

        int margin = 5;
        Paint paint = new Paint();
        Paint.FontMetrics fm = new Paint.FontMetrics();
        paint.setColor(activity.getResources().getColor(R.color.color_red));
        paint.setTextSize(17);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setFakeBoldText(false);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

        // if the background image is defined in main.xml, omit this line
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        canvas.drawText(displaytext1, 0, mBitmap.getHeight() - 50, paint);
        canvas.drawText(displaytext2, 0, mBitmap.getHeight() - 32, paint);

        //canvas.drawText(displaytext3, 0, mBitmap.getHeight() - 22, paint);

        paint.setTextSize(15);
        canvas.drawText(displaytext4, 0, mBitmap.getHeight() - 15, paint);
        // set the bitmap into the ImageView
        return bmOverlay;
    }


    public static Object deserialize(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getDateString() {
        SimpleDateFormat postFormater = new SimpleDateFormat(
                "MMMM dd, yyyy hh:mm a");

        String newDateStr = postFormater.format(Calendar.getInstance()
                .getTime());
        return newDateStr;




    }

    public static String getDateString(String Formats) {
        SimpleDateFormat postFormater = new SimpleDateFormat(Formats);

        String newDateStr = postFormater.format(Calendar.getInstance().getTime());
        return newDateStr;
    }

    public static String convertDateStringFormet(String presnetFormat, String requiredFormat, String dateStr){
        try{
            SimpleDateFormat format1 = new SimpleDateFormat(presnetFormat);
            SimpleDateFormat format2 = new SimpleDateFormat(requiredFormat);
            Date date = format1.parse(dateStr);
            return format2.format(date);
        }catch (Exception e){
            return dateStr;
        }
    }



//    public static void setActionBarBackground(Activity activity)
//    {
//        ActionBar actionBar;
//        actionBar=activity.getActionBar();
//        Resources res = activity.getResources();
//        Drawable drawable = res.getDrawable(R.drawable.digitallogo2);
//       // actionBar.setBackgroundDrawable(drawable);
//    }

    public static void setActionBarBackground(Activity  activity)
    {
        ActionBar actionBar;

        actionBar=activity.getActionBar();
        Resources res = activity.getResources();
        Drawable drawable = res.getDrawable(R.drawable.digitallogo2);
        actionBar.setBackgroundDrawable(drawable);
    }


    /*public static void setStatusBarColor(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= 21) {

            Window window = activity.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(Color.parseColor("#1565a9"));
        }
    }*/

    public static void setStatusBarColor(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= 21) {

            Window window = activity.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(Color.parseColor("#1565a9"));
        }
    }




    public static String getCurrentDate()
    {
        Calendar cal= Calendar.getInstance();
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        month=month+1;

        int h=cal.get(Calendar.HOUR);
        int m=cal.get(Calendar.MINUTE);
        int s=cal.get(Calendar.SECOND);

        String date=month+"/"+day+"/"+year;
        return date;

    }




    public static String getCurrentDateWithTime() throws ParseException {

        SimpleDateFormat f = new SimpleDateFormat("MMM d,yyyy HH:mm");
        Date date=null;
        date=f.parse(getDateString());
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d,yyyy HH:mm a");
        String dateString = formatter.format(date);
        return dateString;
    }



    public static String getDateTime() {

        String date=getDateString();
        String a="";
        StringTokenizer st=new StringTokenizer(date," ");
        while (st.hasMoreTokens()){
            a=st.nextToken();
        }

        if(a.equals("a.m."))
        {

            date=date.replace(a,"AM");
        }
        if(a.equals("p.m."))
        {
            date=date.replace(a,"PM");


        }


        return date;
    }



    public static String getshowCurrentDate()
    {
        Calendar cal= Calendar.getInstance();
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        month =month+1;

        int h=cal.get(Calendar.HOUR);
        int m=cal.get(Calendar.MINUTE);
        int s=cal.get(Calendar.SECOND);

        String date=day+"/"+month+"/"+year;
        return date;

    }

    public static String parseDate(String date)
    {
        StringTokenizer st=new StringTokenizer(date,"/");
        String month="",day="",year="";
        try {
            month = st.nextToken();
            day = st.nextToken();
            year = st.nextToken();
        }catch (Exception e){e.printStackTrace();}

        return day+"/"+month+"/"+year;
    }

    public static void displayPromptForEnablingGPS(final Activity activity)
    {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Do you want open GPS setting?";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                                activity.finish();
                            }
                        });
        builder.create().show();
    }
    public static boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public static boolean isfrontCameraAvalable(){
        int numCameras= Camera.getNumberOfCameras();
        for(int i=0;i<numCameras;i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if(Camera.CameraInfo.CAMERA_FACING_FRONT == info.facing){
                return true;
            }
        }
        return false;
    }
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    //TODO check android.support.v and correct this.
//    public static boolean checkPermission(final Context context)
//    {
//        int currentAPIVersion = Build.VERSION.SDK_INT;
//        if(currentAPIVersion>= Build.VERSION_CODES.M)
//        {
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//                    alertBuilder.setCancelable(true);
//                    alertBuilder.setTitle("Permission necessary");
//                    alertBuilder.setMessage("External storage permission is necessary");
//                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                        }
//                    });
//                    AlertDialog alert = alertBuilder.create();
//                    alert.show();
//                } else {
//                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                }
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
//    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static byte[] bitmaptoByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static String BitArrayToString(byte[] b1) {
        byte[] b = b1;
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static String getOptionFilterName(int type){
        switch (type){
            case 1:
                return "Monitoring_Designation";
            case 2:
                return "surface_Inspection_Work_Status";
            case 3:
                return "Surface_Work_Persentage";
            case 4:
                return "Surface_work_done_as_previous";
            case 5:
                return "Surface_observation_Category";
            case 6:
                return "Scheme_Name";
            case 7:
                return "Surface_Scheme_Finantial_Year";
            case 8:
                return "Surface_Scheme_Fund_Type";
            case 9:
                return "Surface_scheme_Type";
            default:
                return "NA";
        }
    }

    public static byte[] convertBitmapToByteArray(Bitmap bmp){
        //Bitmap bmp = intent.getExtras().get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 45, stream);
        byte[] byteArray = stream.toByteArray();
        //bmp.recycle();
        return byteArray;
    }


    public static String getAppVersion(Context context){
        try {
            String version = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "NA";
        }
    }


    public static String getDeviceIMEI(Context context) {
        String imei;
        MarshmallowPermission permission = new MarshmallowPermission(context, Manifest.permission.READ_PHONE_STATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            imei = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        }
        else
        {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null)
            {
                imei = mTelephony.getDeviceId();
            }
            else
            {
                imei = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
            }
        }

        return imei;
    }




    public static String cleanStringForVulnerability(String aString) {
        if (aString == null) return null;
        String cleanString = "";
        for (int i = 0; i < aString.length(); ++i) {
            cleanString += cleanChar(aString.charAt(i));
        }
        return cleanString;
        //return aString;
    }

    private static char cleanChar(char aChar) {

        // 0 - 9
        for (int i = 48; i < 58; ++i) {
            if (aChar == i) return (char) i;
        }

        // 'A' - 'Z'
        for (int i = 65; i < 91; ++i) {
            if (aChar == i) return (char) i;
        }

        // 'a' - 'z'
        for (int i = 97; i < 123; ++i) {
            if (aChar == i) return (char) i;
        }

        // other valid characters
        switch (aChar) {
            case '/':
                return '/';
            case '.':
                return '.';
            case '-':
                return '-';
            case '_':
                return '_';
            case ' ':
                return ' ';
            case ':':
                return ':';
        }
        return '%';
    }


    public static String getTimeStamp()
    {
        int count=10;

        SecureRandom secureRnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) sb.append(NUMERIC_STRING.charAt(secureRnd.nextInt(NUMERIC_STRING.length())));
        return sb.toString();

    }




    public static boolean isValidEmailId(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    public static void showErrorAlet(final Context context, String title, String message)
    {

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setCancelable(false);
        ab.setTitle(title);
        ab.setMessage(message);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog,int whichButton)
            {
                dialog.dismiss();
                // context.finish();
            }
        });

        ab.create().getWindow().getAttributes().windowAnimations = R.style.AppTheme;

        ab.show();
    }


    public static void checkForEnglish(EditText etxt,Context context) {
        if (etxt.getText().length() > 0) {
            String s = etxt.getText().toString();
            if (isInputInEnglish(s)) {
                //OK
            } else {
                Toast.makeText(context, "Please type in english", Toast.LENGTH_SHORT).show();
                etxt.setText("");
            }
        }
    }
    public static boolean isInputInEnglish(String txtVal)
    {
        String regx = "^[A-Za-z0-9,+;\n.@#$_/ ()|{}&:?!-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txtVal);
        return matcher.find();
    }
    public static String ParseYYMMDD(String time) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String parseDateToyymmddHHMMSS(String time) {
       // String inputPattern = "dd/MM/yyyy HH:mm:ss";
        String inputPattern = "dd-MM-yyyy HH:mm:ss";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String parseDateToyymmdd(String time) {
       // String inputPattern = "dd/MM/yyyy HH:mm:ss";
        String inputPattern = "MM/dd/yyyy HH:mm:ss";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    public static String LastFourDigit(String number){
        String fourdigitvalue="";

        if(number!=null && !number.isEmpty()){
            fourdigitvalue = number.replaceAll("\\w(?=\\w{4})", "*");
        }else {
            fourdigitvalue = number;
        }
        return fourdigitvalue;
    }
    public static File saveImage(final Context context, final String imageData) throws IOException {
        final byte[] imgBytesData = Base64.decode(imageData,
                Base64.DEFAULT);

        final File file = File.createTempFile("image", null, context.getCacheDir());
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public static void loadDocInReader(String doc, Context context)
            throws ActivityNotFoundException, Exception {

        try {
            Intent intent = new Intent();

            // intent.setPackage("com.adobe.reader");
            intent.setDataAndType(Uri.parse(doc), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

        } catch (ActivityNotFoundException activityNotFoundException) {
            activityNotFoundException.printStackTrace();

            throw activityNotFoundException;
        } catch (Exception otherException) {
            otherException.printStackTrace();

            throw otherException;
        }
    }
    @SuppressLint("Range")
    public String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void DownloadandOpen(Context context, String base,String fileName)
    {
        String root = Environment.getExternalStorageDirectory().toString();

        Log.e("ResponseEnv",root);

        File myDir = new File(root + "/Download");
        if (!myDir.exists())
        {
            myDir.mkdirs();
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        String fname = "PMS_"+"_" + n + ".pdf";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            byte[] pdfAsBytes = Base64.decode(base, 0);
            out.write(pdfAsBytes);
            out.flush();
            out.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        File dir = new File(Environment.getExternalStorageDirectory(), "Download");
        File imgFile = new File(dir, fname);
        Intent sendIntent ;

        Uri uri = null;
        try{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {

                //  File file=new File(mFilePath);
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(uri);

                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(sendIntent);
            }
            else
            {
                sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setDataAndType(Uri.parse(fname), "application/pdf");
                sendIntent = Intent.createChooser(sendIntent, "Open File");
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static int CheckPdfSize(Intent data, Context context){
        int fileSize = 0;
        Uri filePath = data.getData();
        if (data.getData().getScheme().equals("file")) {
            File file = new File(filePath.toString());
             fileSize = (int) file.length();
            //System.out.println("This is the file size: " + fileSize);
        } else if (data.getData().getScheme().equals("content")) {
            Cursor returnCursor = context.getContentResolver().query(filePath, null, null, null, null);
            assert returnCursor != null;
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            fileSize = Integer.valueOf(returnCursor.getString(sizeIndex));
            //System.out.println("This is the file size: " + fileSize);
        }
        return fileSize;
    }

    @SuppressLint("Range")
    public static String getFileName(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static String ConvertToString(Uri uri, Context context) {
        String fileBase64string = "";
        //uriString = uri.toString();
        //Log.d("data", "onActivityResult: uri" + uri.toString());

        try {
            InputStream in = context.getContentResolver().openInputStream(uri);
            byte[] bytes = getBytes(in);
            //Log.d("data", "onActivityResult: bytes size=" + bytes.length);
            //Log.d("data", "onActivityResult: Base64string=" + Base64.encodeToString(bytes, Base64.DEFAULT));
            fileBase64string = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //Log.d("error", "onActivityResult: " + e.toString());
        }
        return fileBase64string;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static String getMimeTypeBYURI(Context context, Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }
    public void MakeStatusBarTransparent(Context context) {

       /* if (Build.VERSION.SDK_INT >= 21) {
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public static String getIMEINumber(@NonNull final Context context)
            throws SecurityException, NullPointerException {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert tm != null;
            imei = tm.getImei();
            //this change is for Android 10 as per security concern it will not provide the imei number.
            if (imei == null) {
                imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } else {
            assert tm != null;
            if (tm.getDeviceId() != null && !tm.getDeviceId().equals("000000000000000")) {
                imei = tm.getDeviceId();
            } else {
                imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        return imei;
    }

    public static boolean isNetworkConnected(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

}
