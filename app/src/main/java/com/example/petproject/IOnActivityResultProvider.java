package com.example.petproject;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by anatoliy on 01.11.16.
 * <p>
 * Нитерфейс активити для подписки слушателей метода
 * Activity.onActivityResult
 */

public interface IOnActivityResultProvider extends BaseRouter {

    boolean runActivityForResult(Intent intent, int requestCode);

    /**
     * Получить доступ к объекту списка слушателей
     *
     * @return
     */
    ArrayList<IOnActivityResultListener> getOnActivityResultListeners();

    /**
     * Подписать слушателя
     *
     * @param listener
     */
    void addOnActivityResultListener(IOnActivityResultListener listener);

    /**
     * Отписать слушателя
     *
     * @param listener
     */
    void removeOnActivityResultListener(IOnActivityResultListener listener);

    /**
     * Полностью отписать всех слушателей
     */
    void clearOnActivityResultListeners();
}
