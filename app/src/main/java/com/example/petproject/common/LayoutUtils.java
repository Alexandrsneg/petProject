package com.example.petproject.common;

import androidx.annotation.LayoutRes;

/**
 * android-btvc
 * <p>
 * Класс для помощи в работе с аннотацией Layout
 * <p>
 * Created by zanti on 06.04.17.
 */

public final class LayoutUtils {

    private LayoutUtils() {
    }

    /**
     * Возвращаел LayoutRes для переданного класса. Если не находит в данном классе, то ищет в его
     * потомках, а если не находит, то возвращает 0.
     *
     * @param clazz Класс для поиска LayoutRes
     * @return Возвращает id LayoutRes
     */
    @LayoutRes
    public static int getLayoutRes(Class clazz) {
        if (clazz.isAnnotationPresent(Layout.class))
            return ((Layout) clazz.getAnnotation(Layout.class)).value();
        if (clazz.getSuperclass() != null)
            return getLayoutRes(clazz.getSuperclass());
        return 0;
    }
}
