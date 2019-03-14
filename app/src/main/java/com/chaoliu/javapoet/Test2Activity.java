package com.chaoliu.javapoet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.chaoliu.annotation.ViewId;
import com.chaoliu.api.core.ButterKnife;
import com.chaoliu.api.templete.Unbinder;

/**
 * 注解+javapoet 自动生成模板
 *
 * 加butterknife
 */
public class Test2Activity extends Activity {

    @ViewId( R.id.register )
    Button register;

    @ViewId( R.id.login )
    Button helloTv;

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