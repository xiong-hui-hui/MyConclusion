package Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created by hui.xiong on 2015/11/30.
 */
public class CommonUtils {

    private static final String NAME_REG = "^[\\u4E00-\\u9FA5]{2,5}$";
    private static final String MOBILE_REG = "^(?:13\\d|14\\d|15\\d|17\\d|18\\d)\\d{8}$";
    private static final String PASSWORD_REG = "^[A-Za-z0-9]{6,16}$";
    private static final String VERIFY_CODE_REG = "^[0-9]{6}$";

    /**
     * 有效密码、昵称、邮箱、号码 验证
     **/

    public static boolean isValidVerifyCode(String verifyCode) {
        Pattern pattern = Pattern.compile(VERIFY_CODE_REG);
        return pattern.matcher(verifyCode).matches();
    }

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(NAME_REG);
        return pattern.matcher(name).matches();
    }

    public static boolean isValidMobile(String phoneNum) {
        Pattern pattern = Pattern.compile(MOBILE_REG);
        return pattern.matcher(phoneNum).matches();
    }

    public static boolean isValidPasswd(String passwd) {
        Pattern pattern = Pattern.compile(PASSWORD_REG);
        return pattern.matcher(passwd).matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    /**
     * 得到该页面的屏幕宽、高，根据其返回一个点
     *
     * @param activity
     * @return Point
     */
    public static Point getWindowPoint(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Point point = new Point();
        point.set(dm.widthPixels, dm.heightPixels);
        return point;
    }
    /**
     * 关闭软键盘
     */
    public static void closeSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    /**
     * 采样和压缩
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }


    /**
     * 把filepath路径的文件压缩为流
     * @param filePath
     * @param quality
     * @return
     */
    public static InputStream compressImage(String filePath, int quality) {
        Bitmap image = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.recycle();
        return isBm;
    }

    /**
     * dp和sp转px
     *
     * @param dp
     * @param displayMetrics Context.getResources().getDisplayMetrics())
     * @return
     */
    public static int dp2px(int dp, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                displayMetrics);
    }

    public static int sp2px(int sp, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                displayMetrics);
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    public static String getFilePathFromContentUri(Uri selectedVideoUri, ContentResolver contentResolver) {
        String filePath = null;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    /**
     * 页面跳转
     */
    public static void openActivity(Activity activity, Class<?> pClass) {
        openActivity(activity, new Intent(activity, pClass));
    }

    public static void openActivity(Activity activity, Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(activity, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        openActivity(activity, intent);
    }

    public static void openActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    public static void takePic(Activity activity, File saveFile, int reqCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(saveFile));
        activity.startActivityForResult(takePictureIntent, reqCode);
    }

    public static void pickPic(Activity activity, int reqCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /* 取得相片后返回本画面 */
        activity.startActivityForResult(intent, reqCode);
    }

    public static File getCacheDir(Context context) {
        File appCacheDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File eAppCacheDir = context.getExternalCacheDir();
            if (eAppCacheDir == null) {
                appCacheDir = context.getCacheDir();
            } else {
                appCacheDir = eAppCacheDir;
            }
        } else {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    public static void installApk(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * Bitmap形式的图片以File形式保存，若已存在此文件则返回原来文件
     */
    public static File bitmapToFile(Bitmap bitmap,String fileName){
        File file = new File("data/data/com.xiong.myconclusion/");
        if (!file.exists()) {
            file.mkdir();
        }
        File imagefile = new File(file,fileName+".jpg");
        if(!imagefile.exists()) {
            try {
                imagefile.createNewFile();
                FileOutputStream fos = new FileOutputStream(imagefile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imagefile;
    }

    /***
     * 钱 分转元
     * @param value
     * @return
     */
    public static String centToDollar(String value){
        double money = Double.parseDouble(value);
        BigDecimal bigDecimal =new BigDecimal(money/100);
        return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP) + "";
    }
   /* public static String splitMoney(String){

    }*/
}

