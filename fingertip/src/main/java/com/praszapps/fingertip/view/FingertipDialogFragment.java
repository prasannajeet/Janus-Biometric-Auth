package com.praszapps.fingertip.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.praszapps.fingertip.MVP.FingertipMVPContract;
import com.praszapps.fingertip.R;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.DaggerDialogFragment;

public final class FingertipDialogFragment extends DaggerDialogFragment implements FingertipMVPContract.View {

    @Inject
    @Named("dialogFragmentPresenter")
    public FingertipMVPContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fingerprint_dialog, container, false);
        return view;
    }

    @Override
    public void setUpFingerprintViews() {

    }

    @Override
    public void handleNoFingerprintAuthPossible() {

    }

    @Override
    public void onFingerPrintAuthenticationSuccess() {

    }

    @Override
    public void onFingerprintAuthenticationFailed(String text) {

    }
}
