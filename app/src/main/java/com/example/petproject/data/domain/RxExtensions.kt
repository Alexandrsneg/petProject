package com.example.petproject.data.domain

import android.util.Log
import com.example.petproject.common.exception.ErrorResponse
import com.example.petproject.common.exception.RepositoryException
import com.example.petproject.common.exception.RetrofitException
import com.google.gson.Gson
import retrofit2.Response
import rx.Completable
import rx.Observable
import rx.Single
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject
import java.io.IOException


fun <T> Single<T>.standardIO() = observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())


@JvmOverloads
fun <T> Single<T>.standardIO(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit = { }) = observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(onSuccess, onError)


fun Completable.standardIO() = observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())


fun <T> BehaviorSubject<T>.standardIO() = observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())


fun <T> Observable<T>.standardIO() =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext { throwable: Throwable -> throwable.handleError() }


fun <T> Observable<T>.standardIO(subscriber: Subscriber<T>) = standardIO()
        .subscribe(subscriber)


@JvmOverloads
fun <T> Single<T>.standardIOAsync(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit = { }) = observeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .subscribe(onSuccess, onError)

fun Response<Void>.checkIsSuccessful(gson: Gson) {
    val isSuccessful = this.isSuccessful

    if (!isSuccessful) {
        val responseBody = this.errorBody()
        val errorResponse = gson.fromJson(responseBody?.string(), ErrorResponse::class.java)
        throw RepositoryException(errorResponse)
    }
}

fun <T> Throwable.handleError(): Observable<T> {
    if (this is RetrofitException) {
        val r = this
        if (r.kind == RetrofitException.Kind.HTTP) {
            return try {
                return try {
                    Observable.error(RepositoryException(r.getErrorBodyAs(ErrorResponse::class.java)))
                } catch (e: IOException) {
                    Log.e("tag", "Error when parse error body from response", e)
                    Observable.error(RepositoryException(ErrorResponse.createBackendError(r.message, r.httpCode.toString())))
                }
            } catch (e: IOException) {
                Log.e("tag", "Error when parse error body from response", e)
                Observable.error(RepositoryException(ErrorResponse.createBackendError(r.message, r.kind.toString())))
            }
        }

        if (r.kind == RetrofitException.Kind.NETWORK) {
            return Observable.error(RepositoryException(ErrorResponse.createNetworkError()))
        }
    }
    return Observable.error(RepositoryException(ErrorResponse.createBackendError()))
}