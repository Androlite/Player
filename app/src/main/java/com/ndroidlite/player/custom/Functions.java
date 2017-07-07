package com.ndroidlite.player.custom;

/**
 * @author jatin
 */

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;


import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    private static final String ProfilePicture = "Pocket Cyber Cafe/Profile";

    public static final SimpleDateFormat ServerDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public static final SimpleDateFormat NotificationDateTime = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    private static final String NAME_PATTERN = "[a-zA-Z ]*";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void fireIntentWithdata(Intent i, Context context) {
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean nameValidation(String name) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static String toStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static int toLength(EditText editText) {
        return editText.getText().toString().trim().length();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void openInMap(Context context, double latitude, double longitude, String labelName) {
        String newUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + labelName + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUri));
        context.startActivity(intent);
    }

    public static void makePhoneCall(Context context, String callNo) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + callNo));
        context.startActivity(dialIntent);
    }

    public static String parseDate2(String inputDate, SimpleDateFormat outputFormat) {

        Date date = null;
        String str = null;

        try {
            date = ServerDateTime.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDate(String inputDate, SimpleDateFormat outputFormat) {

        Date date = null;
        String str = null;

        try {
            date = NotificationDateTime.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDate3(String inputDate, SimpleDateFormat outputFormat, SimpleDateFormat inputFormat) {

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /*public static void showToast(Context context, String message) {
        new ToastLib.Builder(context, message)
                .duration(ToastEnum.SHORT)
                .backgroundColor(ContextCompat.getColor(context, R.color.FabBG))
                .textColor(ContextCompat.getColor(context, R.color.white))
                .typeface(getFontType(context, FontType.Regular.getId()))
                .textSize(18)
                .corner(12)
                .margin(96)
                .padding(24)
                .spacing(1)
                .gravity(Gravity.BOTTOM)
                .show();
    }*/

    /*public static void setToolbar(Context context, Toolbar toolbar, String title, Boolean isBackNeeded, boolean isSearchText, final OnToolbarClickListener onToolbarClickListener) {
        TfTextView txtCustomTitle = (TfTextView) toolbar.findViewById(R.id.txtCustomTitle);
        MaterialSearchView edtCustomText = (MaterialSearchView) toolbar.findViewById(R.id.searchView);
        toolbar.setTitle("");
        txtCustomTitle.setText(title);

        if (isSearchText) {
            txtCustomTitle.setVisibility(View.GONE);
            edtCustomText.setVisibility(View.VISIBLE);
        } else {
            txtCustomTitle.setVisibility(View.VISIBLE);
            edtCustomText.setVisibility(View.GONE);
        }
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        if (isBackNeeded) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onToolbarClickListener != null) {
                    onToolbarClickListener.onBack();
                }
            }
        });
        ((AppCompatActivity) context).getSupportActionBar().setElevation(context.getResources().getInteger(R.integer.toolbar_elevation));
    }*/

    public static String getTimeFromDate(String date) {
        String time = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
        try {
            Date date1 = sdf1.parse(date);
            time = sdf2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String changeDateFormat(String date) {
        String output = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date date1 = sdf1.parse(date);
            output = sdf2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            Functions.LogE("error", e.getMessage());
        }
        return output;
    }

    public interface OnToolbarClickListener {
        void onBack();
    }

    public static void LogE(String key, String value) {
//        if (BuildConfig.DEBUG) {
        Log.e(key, value);
//        }
    }

    public static Typeface getFontType(Context _context, int typeValue) {

        Typeface typeface;

        if (typeValue == 1) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");

        } else if (typeValue == 2) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/medium.ttf");

        } else if (typeValue == 3) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/semibold.ttf");

        } else if (typeValue == 4) {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/bold.ttf");

        } else {
            typeface = Typeface.createFromAsset(_context.getAssets(), "fonts/regular.ttf");
        }

        return typeface;
    }

    public static String NotificationDate(String notificationDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        SimpleDateFormat onlyDay = new SimpleDateFormat("dd", Locale.US);
        Date date = null;
        Date currentDate = new Date();
        try {
            date = sdf.parse(notificationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int compare = getZeroTimeDate(date).compareTo(getZeroTimeDate(currentDate));

        if (compare == 0) {
            return "Today";
        } else {
            int isYesterday = getZeroTimeDate(date).compareTo(getZeroTimeDate(getYesterdayDate()));
            if (isYesterday == 0) {
                return "Yesterday";
            } else {
                return notificationDate;
            }
        }
    }

    private static Date getZeroTimeDate(Date input) {
        Date output = input;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(input);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        output = calendar.getTime();

        return output;
    }

    private static Date getYesterdayDate() {
        Date output = null;
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        output = calendar.getTime();

        return output;
    }

/*
    public static void setMenuTypeface(Context context, Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
            mNewTitle.setSpan(new CustomTypefaceSpan("", Functions.getFontType(context, FontType.SemiBold.getId())), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            menuItem.setTitle(mNewTitle);
        }
    }
*/


    /*public static void closeSession(Context context) {
        PrefUtils.setLoggedIn(context, false);
        PrefUtils.setSelectedGrade(context, "");
        PrefUtils.setSelectedLanguage(context, "");
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
    }*/

    /*
    1 password
    2 new password
    3 confirm password
  */
/*    public static boolean checkPassrordLength(Context context, int whichPassrord, String password) {
        switch (whichPassrord) {
            case 1:
                if (password.trim().length() < context.getResources().getInteger(R.integer.pwd_min) || password.trim().length() > context.getResources().getInteger(R.integer.pwd_max)) {
                    Functions.showToast(context, "Password should be minimum " + context.getResources().getInteger(R.integer.pwd_min) + " and maximum " + context.getResources().getInteger(R.integer.pwd_max) + " character long.");
                    return false;
                }
                break;

            case 2:
                if (password.trim().length() < context.getResources().getInteger(R.integer.pwd_min) || password.trim().length() > context.getResources().getInteger(R.integer.pwd_max)) {
                    Functions.showToast(context, "New Password should be minimum " + context.getResources().getInteger(R.integer.pwd_min) + " and maximum " + context.getResources().getInteger(R.integer.pwd_max) + " character long.");
                    return false;
                }
                break;

            case 3:
                if (password.trim().length() < context.getResources().getInteger(R.integer.pwd_min) || password.trim().length() > context.getResources().getInteger(R.integer.pwd_max)) {
                    Functions.showToast(context, "Confirm Password should be minimum " + context.getResources().getInteger(R.integer.pwd_min) + " and maximum " + context.getResources().getInteger(R.integer.pwd_max) + " character long.");
                    return false;
                }
                break;
        }
        return true;
    }*/

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return manufacturer + " " + model;
    }


    /*public static void showUserDetails(final Context context) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("User Details");
        dialog.setMessage(PrefUtils.getUserFullProfileDetails(context).toString());
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }*/


    public static String profilePicturePath() {

        return Environment.getExternalStorageDirectory() + File.separator + ProfilePicture + File.separator;
    }

   /* public static String jsonString(Object obj) {
        return "" + MyApplication.getGson().toJson(obj);
    }*/

    /*public static int getBgColor(Context context, int position) {
        ArrayList<Integer> colors;
        colors = new ArrayList();
        int pos = position % 5;

        colors.add(ContextCompat.getColor(context, R.color.color1));
        colors.add(ContextCompat.getColor(context, R.color.color2));
        colors.add(ContextCompat.getColor(context, R.color.color3));
        colors.add(ContextCompat.getColor(context, R.color.color4));
        colors.add(ContextCompat.getColor(context, R.color.color5));

        return colors.get(pos);
    }*/

   /* public static void generateFileAndMail(Context context, String fileName, String data, String subject, String toEmails) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "HealthInsurance");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(data);
            writer.flush();
            writer.close();
            mailFile(gpxfile.getAbsolutePath(), context, subject, gpxfile.getName(), toEmails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

/*
    public static void generateFileAndMail(Context context, String fileName, ArrayList<MyMessage> messageArrayList, String subject, String toEmails) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageArrayList.size(); i++) {
            sb.append("[" + messageArrayList.get(i).getDateTime() + " " + "Name" + "] : " + messageArrayList.get(i).getMessage() + "\n");
        }

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "HealthInsurance");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sb.toString());
            writer.flush();
            writer.close();
            mailFile(gpxfile.getAbsolutePath(), context, subject, gpxfile.getName(), toEmails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public static void generateFileAndMail(Context context, String fileName, String sb, String subject, String toEmails) {
        /*StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageArrayList.size(); i++) {
            sb.append("[" + messageArrayList.get(i).getDateTime() + " " + "Name" + "] : " + messageArrayList.get(i).getMessage() + "\n");
        }*/

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "HealthInsurance");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sb.toString().trim());
            writer.flush();
            writer.close();
            mailFile(gpxfile.getAbsolutePath(), context, subject, gpxfile.getName(), toEmails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mailFile(String targetFilePath, Context context, String subject, String fileName, String toEmails) {
        String extraText = "Chat history is attached as " + fileName + " file to this email.";
        Log.e("file", targetFilePath);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822")
                .putExtra(
                        Intent.EXTRA_EMAIL,
                        new String[]{toEmails})
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, extraText);
        Uri attachmentUri = Uri.parse(targetFilePath);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + attachmentUri));
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | HH:mm", Locale.US);
        return sdf.format(new Date());
    }

    public static void showPromptDialog(Context context, String title, String message, String positive, String negative, final onPromptListener onPromptListener) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onPromptListener != null) {
                    onPromptListener.onPositive();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onPromptListener != null) {
                    onPromptListener.onNegative();
                }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public interface onPromptListener {
        void onPositive();

        void onNegative();
    }

    public static void openPlayStore(Context context) {
        String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void noInternet(final Context context) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("No Internet Connection");
        dialog.setMessage("There is no internet connectivity. Turn on your data/wifi.");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

/*
    public static void setMenuTypeface(Context context, Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
            mNewTitle.setSpan(new CustomTypefaceSpan("", Functions.getFontType(context, FontType.SemiBold.getId())), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            menuItem.setTitle(mNewTitle);
        }
    }
*/

    public static String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static String encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }

    /*public static void applyFontToMenuItem(Context context, MenuItem menuItem) {
        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", Functions.getFontType(context, FontType.Regular.getId())), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }*/

    /*public static void setRoundImage(final Context context, final String url, final ImageView imageView) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();

        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.large_user)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }*/

    public static void setPermission(final Context context, @NonNull String[] permissions, PermissionListener permissionListene) {

        if (permissions != null && permissions.length == 0 && permissionListene != null) {
            return;
        }
        new TedPermission(context)
                .setPermissionListener(permissionListene)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(permissions)
                .check();
    }

    /*public static void sendNotification(Context context) {
        Intent intent = new Intent(context, UserListActivity.class);
        intent.putExtra(AppConstants.FOR_USER_LIST, AppConstants.FROM_NOTIFICATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        android.support.v7.app.NotificationCompat.Builder mBuilder = new android.support.v7.app.NotificationCompat.Builder(context);

        android.support.v7.app.NotificationCompat.BigTextStyle s = new android.support.v7.app.NotificationCompat.BigTextStyle();
        s.setBigContentTitle(context.getString(R.string.app_name));
        s.setSummaryText(context.getString(R.string.notification_msg));
        mBuilder.setStyle(s);

        Notification notification;
        notification = mBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker(context.getString(R.string.app_name))
                .setStyle(s)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notification_msg))
                .setStyle(new android.support.v7.app.NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_msg)))
                .build();

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(m, notification);
    }*/

}
