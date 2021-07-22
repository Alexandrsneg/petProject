package com.example.petproject;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by anatoliy on 18.11.16.
 */

public interface IOnRequestPermissionsResultProvider extends BaseRouter {

    /**
     * Проверяет что все значения массива соответствую константе PackageManager.PERMISSION_GRANTED
     * @param permissions
     * @return [0..1] процент удовлетвотенных разрешений
     */
    float checkPermissions(@NonNull int[] permissions);

    /**
     * Реально должно проверить наличие разрешений из переданного масива
     * @param permissions
     * @return [0..1] процент удовлетвотенных разрешений
     */
    float checkPermissions(@NonNull String[] permissions);

    /**
     * Запустить выполнение запроса разрешений
     * @param permissions
     * @param requestCode
     * @return
     */
    boolean runRequestPermissions(@NonNull String[] permissions, int requestCode);

    /**
     * Получить доступ к объекту списка слушателей
     *
     * @return
     */
    ArrayList<IOnRequestPermissionsResultListener> getOnRequestPermissionsResultListeners();

    /**
     * Подписать слушателя
     *
     * @param listener
     */
    void addOnRequestPermissionsResultListener(IOnRequestPermissionsResultListener listener);

    /**
     * Отписать слушателя
     *
     * @param listener
     */
    void removeOnRequestPermissionsResultListener(IOnRequestPermissionsResultListener listener);

    /**
     * Полностью отписать всех слушателей
     */
    void clearOnRequestPermissionsResultListeners();
}
