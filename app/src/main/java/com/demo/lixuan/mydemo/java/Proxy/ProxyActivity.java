package com.demo.lixuan.mydemo.java.Proxy;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ProxyActivity extends BaseActivity {
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    private TextView LogText;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView() {
        mLlContainer.addView(generateTextButton(" text proxy", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Subject subject = new SubjectImpl();

                Subject proxy = (Subject) Proxy.newProxyInstance(SubjectImpl.class.getClassLoader(), SubjectImpl.class.getInterfaces(), new SubjectInvocationHandler(subject));
                proxy.test();

//                System.out.println(proxy);
            }
        }));

        LogText = (TextView) generateTextButton("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mLlContainer.addView(LogText);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class SubjectImpl implements Subject {

        @Override
        public void test() {
            System.out.println("This is test method");
            LogText.append("SubjectImpl : " + "This is test method" + "\n");
        }
    }

    public interface Subject {
        void test();
    }

    public class SubjectInvocationHandler implements InvocationHandler {

        private Subject target;

        public SubjectInvocationHandler(Subject subject) {
            this.target = subject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("before method!");
            LogText.append("invoke : " + "before method!" + "\n");
            Object result = method.invoke(target, args);

            System.out.println("after method!");
            LogText.append("invoke : " + "after method!" + "\n");
            return result;
        }
    }


}
