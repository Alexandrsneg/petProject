package com.example.petproject;

import androidx.annotation.NonNull;

/**
 * Created by anatoliy on 18.11.16.
 * <p>
 * Интерфейс слушателя для обработки входящих
 * данных в методе Activity.onRequestPermissionsResult
 */
public interface IOnRequestPermissionsResultListener {

    /**
     * Передача входных данных слушателю
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @return TRUE - данные пригодились, иначе FALSE
     */
    boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
