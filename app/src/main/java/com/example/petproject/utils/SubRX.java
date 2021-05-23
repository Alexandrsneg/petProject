package com.example.petproject.utils;

import rx.Subscriber;

/**
 * Created by anatoliy on 12.11.16.
 */

public class SubRX<T> extends Subscriber<T> {

    private IComplete iComplete;
    private IError iError;
    private INext<T> iNext;
    private IFinally<T> iFinally;

    private T onNextResult;
    private Throwable onErrorReason;

    public SubRX() {
    }

    public SubRX(IFinally<T> iFinally) {
        this.iFinally = iFinally;
    }

    public SubRX<T> setComplite(IComplete iComplete) {
        this.iComplete = iComplete;
        return this;
    }

    public SubRX<T> setError(IError iError) {
        this.iError = iError;
        return this;
    }

    public SubRX<T> setNext(INext<T> iNext) {
        this.iNext = iNext;
        return this;
    }

    /**
     * Выполняется после onError или onComplete
     * onError -> onFinally
     * onNext -> onComplete -> onFinally
     *
     * @param iFinally
     * @return
     */
    public SubRX<T> setFinal(IFinally<T> iFinally) {
        this.iFinally = iFinally;
        return this;
    }

    @Override
    public void onCompleted() {
        unsubscribe();
        if (iComplete != null)
            iComplete.onCompleted();
        if (iFinally != null)
            iFinally.onFinally(onNextResult, onErrorReason);
    }

    @Override
    public void onError(Throwable e) {
        if (iError != null)
            iError.onError(e);
        onErrorReason = e;
        if (iFinally != null)
            iFinally.onFinally(onNextResult, onErrorReason);
    }

    @Override
    public void onNext(T t) {
        if (iNext != null)
            iNext.onNext(t);
        onNextResult = t;
    }

    public interface IComplete {
        void onCompleted();
    }

    public interface IError {
        void onError(Throwable e);
    }

    public interface INext<T> {
        void onNext(T result);
    }

    public interface IFinally<T> {
        /**
         * @param result Результат из onNext
         * @param e      Ошибка из onError
         */
        void onFinally(T result, Throwable e);
    }
}
