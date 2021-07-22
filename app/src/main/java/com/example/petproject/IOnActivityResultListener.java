package com.example.petproject;

import android.content.Intent;

/**
 * Created by anatoliy on 01.11.16.
 * <p>
 * Интерфейс слушателя для обработки входящих
 * данных в методе Activity.onActivityResult
 */

public interface IOnActivityResultListener {

    /**
     * Передача входных данных слушателю
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     * @return TRUE - данные пригодились, иначе FALSE
     */
    boolean onActivityResult(int requestCode, int resultCode, Intent intent);
}
