package com.chaoliu.javapoet;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.chaoliu.annotation.ViewId;
import com.chaoliu.api.core.ButterKnife;
import com.chaoliu.api.templete.Unbinder;

import java.util.Map;

/**
 * 注解+javapoet 自动生成模板
 *
 * 加butterknife
 */
public class TestActivity extends MainActivity {

    @ViewId( R.id.guess )
    Button guess;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        unbinder = ButterKnife.bind( this );

        guess.setOnClickListener( v->{
            Toast.makeText( this,"hello butterknife",Toast.LENGTH_SHORT ).show();
        } );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind( unbinder );
    }
}