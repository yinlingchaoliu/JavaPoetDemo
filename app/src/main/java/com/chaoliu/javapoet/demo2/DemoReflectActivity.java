package com.chaoliu.javapoet.demo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoliu.api.core.ButterKnife;
import com.chaoliu.api.templete.Unbinder;
import com.chaoliu.javapoet.R;

/**
 *
 * 手写模板
 * 通过反射这种方式
 *
 */
public class DemoReflectActivity extends AppCompatActivity {

    TextView helloTv;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        unbinder = ButterKnife.bind( this );

        helloTv.setOnClickListener( v->{
            Toast.makeText( this,"hello butterknife",Toast.LENGTH_SHORT ).show();
        } );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind( unbinder );
    }

}