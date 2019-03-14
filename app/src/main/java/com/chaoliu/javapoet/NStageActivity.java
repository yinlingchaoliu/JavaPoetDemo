package com.chaoliu.javapoet;

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
public class NStageActivity extends TestActivity {

    @ViewId( R.id.stage )
    Button stage;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        unbinder = ButterKnife.bind( this );

        helloTv.setOnClickListener( v->{
            Toast.makeText( this,"hello butterknife",Toast.LENGTH_SHORT ).show();
        } );

        login.setOnClickListener( v->{
            Toast.makeText( this,"登录",Toast.LENGTH_SHORT ).show();
        } );

        stage.setOnClickListener( v->{
            Toast.makeText( this,"别看我层数多，但是能取",Toast.LENGTH_SHORT ).show();
        } );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind( unbinder );
    }
}