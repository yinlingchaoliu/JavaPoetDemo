package com.chaoliu.javapoet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaoliu.annotation.ViewId;
import com.chaoliu.api.core.ButterKnife;
import com.chaoliu.api.templete.Unbinder;

public class BlankFragment extends Fragment {

    private Unbinder unbinder1;

    @ViewId(R.id.blankTv)
    TextView blankTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_blank, container, false );
        unbinder1 = ButterKnife.bind( this, rootView );
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
        unbinder1 = null;
    }

}
