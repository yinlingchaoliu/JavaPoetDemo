package com.chaoliu.javapoet.demo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoliu.api.templete.Unbinder;
import com.chaoliu.javapoet.R;

/**
 * 生成实体类赋值或初始化
 * 最原始写法
 */
public class Demo1Activity extends AppCompatActivity {

    TextView helloTv;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //绑定视图
        unbinder = new Demo1Activity_ViewBinding( Demo1Activity.this,getWindow().getDecorView() );

        helloTv.setOnClickListener( v->{
            Toast.makeText( this,"hello butterknife",Toast.LENGTH_SHORT ).show();
        } );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
    }
}