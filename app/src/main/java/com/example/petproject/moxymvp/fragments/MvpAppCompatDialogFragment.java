package com.example.petproject.moxymvp.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.arellomobile.mvp.MvpDelegate;

public class MvpAppCompatDialogFragment extends AppCompatDialogFragment {
    private boolean mIsStateSaved;
    private MvpDelegate<? extends MvpAppCompatDialogFragment> mMvpDelegate;

    public MvpAppCompatDialogFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getMvpDelegate().onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        this.mIsStateSaved = false;
        this.getMvpDelegate().onAttach();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mIsStateSaved = true;
        this.getMvpDelegate().onSaveInstanceState(outState);
        this.getMvpDelegate().onDetach();
    }

    public void onStop() {
        super.onStop();
        this.getMvpDelegate().onDetach();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.getMvpDelegate().onDetach();
        this.getMvpDelegate().onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.getActivity().isFinishing()) {
            this.getMvpDelegate().onDestroy();
        } else if (this.mIsStateSaved) {
            this.mIsStateSaved = false;
        } else {
            boolean anyParentIsRemoving = false;

            for (Fragment parent = this.getParentFragment(); !anyParentIsRemoving && parent != null; parent = parent.getParentFragment()) {
                anyParentIsRemoving = parent.isRemoving();
            }

            if (this.isRemoving() || anyParentIsRemoving) {
                this.getMvpDelegate().onDestroy();
            }

        }
    }

    public MvpDelegate getMvpDelegate() {
        if (this.mMvpDelegate == null) {
            this.mMvpDelegate = new MvpDelegate(this);
        }

        return this.mMvpDelegate;
    }
}

