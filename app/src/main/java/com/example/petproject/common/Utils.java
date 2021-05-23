package com.example.petproject.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.petproject.App;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Utils {

    public static final SimpleDateFormat sFormatPeriod = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private static final long KOP_RATIO = 100;
    private static final double KOP_DECIMAL_RATIO = 0.01;
    public static final String DEFAULT_PHONE_NUMBER = "0000000000";
    private static String appUUID;
    private static Lock lock = new ReentrantLock();

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }

    public static void delete(File file) {

        if (file == null)
            return;

        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    delete(f);
                }
            }
            file.delete();
        }
    }

    public static void clear(File file) {

        if (file == null)
            return;

        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles())
                    delete(f);
                return;
            }
            file.delete();
        }
    }

    public static File getExternalDir(String folder) {

        Context context = App.appContext;
        File exStorage = context.getExternalFilesDir(null);
        File directory = new File(exStorage, folder);
        if (!directory.exists())
            directory.mkdirs();

        return directory;
    }

    // МЕТОД ПОКА ОСТАВЛЕН, т.к. может пригодится при сжатии изображения
    // http://stackoverflow.com/questions/4837715/how-to-resize-a-bitmap-in-android
    // http://stackoverflow.com/questions/8471226/how-to-resize-image-bitmap-to-a-given-size
    public static Bitmap getResizedBitmap(Bitmap realImage, int maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width, height, filter);
    }

    public static boolean isShownKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
    }

    public static boolean hideKeyboard(Activity activity) {
        if (activity == null)
            return false;

        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        }

        return false;
    }

    public static void close(@Nullable Closeable target) {
        try {
            if (target != null) {
                if (target instanceof Flushable)
                    ((Flushable) target).flush();
                target.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Копирование файлов
     *
     * @param input Что копируем
     * @param output Куда копируем
     * @return Скопировалось?
     */
    public static boolean copy(@NonNull InputStream input, @NonNull OutputStream output) {

        try {

            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) != -1)
                output.write(buffer, 0, read);

        } catch (Exception e) {
            return false;

        } finally {
            close(input);
            close(output);
        }

        return true;
    }

    /**
     * Копирование файлов и дирректорий
     *
     * @param source Что копируем
     * @param target Куда копируем
     * @return Скопировалось?
     */
    public static boolean copy(@NonNull File source, @NonNull File target) {

        Log.d("EAC", "copy file: from: " + source.getAbsolutePath() + " | to: " + target.getAbsolutePath());
        if (source.exists()) {

            if (source.isDirectory()) {
                if (!target.exists())
                    target.mkdir();

                for (File _source : source.listFiles())
                    if (!copy(_source, new File(source, _source.getName())))
                        return false;

                return true;
            }

            try {
                return copy(new FileInputStream(source), new FileOutputStream(target));
            } catch (Exception e) {
            }
        }

        return false;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int result = 1;
        double height = (double) options.outHeight;
        double width = (double) options.outWidth;

        while (height > reqHeight || width > reqWidth) {
            result *= 2;
            height /= 2;
            width /= 2;
        }

        return result;
    }

    /**
     * Удалит из номера телефона все что не цифра
     * и оставит только length символов справа
     *
     * @param phone  Если NULL - номер нельзя скорректировать
     * @param length
     * @return
     */
    @Nullable
    public static String phoneCorrector(String phone, int length) {

        if (phone == null || length < 1)
            return null;

        String result = phone.replaceAll("[^0-9]+", "");
        if (result.length() < length) {
            return null;
        }

        return result.substring(result.length() - length);
    }

    /**
     * Сохранить bitmap в файловой системе
     *
     * @param what    Что сохранить
     * @param where   Куда сохранить
     * @param format  Формат файла
     * @param quality Качество (0, 100]
     * @return
     */
    public static boolean saveBitmap(Bitmap what, File where, Bitmap.CompressFormat format, int quality) {

        try {
            FileOutputStream fos = new FileOutputStream(where);
            what.compress(format, quality, fos);
            fos.flush(); // Если тут фейл, то и "close" зафейлится
            fos.close(); // Ну а тут уже более нечего делать
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Получить все дочерние вью, которые можно скастить к clazz
     * Пример: Надо получить вообще все вью - clazz = View.class
     * Пример: Получить только TextView - clazz = TextView.class
     *
     * @param view
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> getAllChildren(View view, Class<T> clazz) {

        ArrayList<T> result = new ArrayList<>();

        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {

                View child = vg.getChildAt(i);

                if (clazz.isInstance(child)) {
                    T tmp = clazz.cast(child);
                    result.add(tmp);
                }

                result.addAll(getAllChildren(child, clazz));
            }
        }

        return result;
    }

    /**
     * Натягиваем маску на текст
     *
     * @param mask         Маска
     * @param text         Текст для маскирования
     * @param replacements набор символов которые подлежат замене в маске
     * @return
     */
    @NonNull
    public static String applyMask(String mask, String text, String replacements) {

        StringBuilder builder = new StringBuilder("");

        if (mask != null && text != null) {

            boolean isService = false;
            int maskLength = mask.length();
            int textLength = text.length();
            int textIndex = 0;

            for (int i = 0; i < maskLength; i++) {
                char ch = mask.charAt(i);

                if (isService) {
                    builder.append(ch);
                    isService = false;
                    continue;
                }

                isService = ch == '\\'; // Символ экранирования?
                if (isService)
                    continue;

                if (replacements.indexOf(ch) == -1) {
                    builder.append(ch);
                    continue;
                }

                if (textIndex == textLength)
                    break;

                builder.append(text.charAt(textIndex++));
            }
        }

        return builder.toString();
    }

    public static int sp2px(float sp, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics()
        );
    }

    public static float dp2px(float dp, Context context) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics()
        );
    }

    /**
     * Склонение словоформы
     *
     * @param value Число
     * @param f1    Первая форма (Пример: 1 комментарий)
     * @param f2    Вторая форма (Пример: 2 комментария)
     * @param f3    Третья форма (Пример: 9 комментариев)
     * @return Одна из 3-х входных словоформ
     */
    public static String morph(int value, String f1, String f2, String f3) {

        value = Math.abs(value) % 100;
        if (value > 10 && value < 20)
            return f3;
        value %= 10;

        if (value > 1 && value < 5)
            return f2;

        return value == 1 ? f1 : f3;
    }


    public static String arrayList2String(List<Integer> list) {

        if (list == null || list.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder().append(list.get(0));
        for (int i = 1, length = list.size(); i < length; i++) {
            sb.append(',').append(list.get(i));
        }

        return sb.toString();
    }

    public static String list2String(List<String> list) {

        if (list == null || list.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder().append(list.get(0));
        for (int i = 1, length = list.size(); i < length; i++) {
            sb.append(',').append(list.get(i));
        }

        return sb.toString();
    }


    public static String getStringFromStream(InputStream stream) {
        return getStringFromStream(stream, false);
    }

    public static String getStringFromStream(InputStream stream, boolean closeStream) {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String mLine;
            while ((mLine = reader.readLine()) != null)
                sb.append(mLine + "\n");
        } catch (IOException e) {
            //log the exception
        } finally {
            close(reader);
        }

        if (closeStream)
            close(stream);

        return sb.toString();
    }

    public static boolean openUrlInBrowser(Context context, String url) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean openPlayMarket(Context context, String pkg) {
        return Utils.openUrlInBrowser(context, "market://details?id=" + pkg)
                || Utils.openUrlInBrowser(context, "https://play.google.com/store/apps/details?id=" + pkg);
    }

    /**
     * Получить путь до карты памяти (Только для API 19 KitKat)
     * более старые версии вернет только локальное хранилище во встроенной памяти
     * @return
     */
    public static File getExternalStorageDirectorySD() {

        File fileESD = Environment.getExternalStorageDirectory();
        String sFileESD = fileESD.toString();

        Context context = App.appContext;
        try {
            for (File file : context.getExternalFilesDirs(null)) {
                if (file != null) {
                    String sFile = file.toString();
                    if (!sFile.contains(sFileESD))
                        return file;
                }
            }
        } catch (Exception e) {
        }

        File result = context.getExternalFilesDir(null);
        return result != null ? result : context.getCacheDir();
    }


    public static String getAppName(String packageName) {

        if (packageName == null)
            packageName = "";

        if (packageName.contains("facebook")) return "fb";
        if (packageName.contains("instagram")) return "fb";
        if (packageName.contains("viber")) return "viber";
        if (packageName.contains("vkontakte")) return "vk";
        if (packageName.contains("telegram")) return "telegram";
        if (packageName.contains("twitter")) return "tw";
        if (packageName.contains("ru.ok")) return "ok";

        return packageName;
    }

    public static float scope(float v, float min, float max) {
        return v < min ? min : Math.min(v, max);
    }


    public static float getRoundedDecimalValueRub(long value) {
        return new BigDecimal(String.valueOf(value * KOP_DECIMAL_RATIO)).setScale(2, RoundingMode.DOWN).floatValue();
    }
}