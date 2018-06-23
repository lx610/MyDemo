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
    public void initView(Bundle savedInstanceState) {


        mLlContainer.addView(generateTextButton(" text proxy", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Subject subject = new SubjectImpl();

                Subject proxy = (Subject) Proxy.newProxyInstance(SubjectImpl.class.getClassLoader(), SubjectImpl.class.getInterfaces(), new SubjectInvocationHandler(subject));
                proxy.test("add new text");

//                System.out.println(proxy);
            }
        }));

        mLlContainer.addView(generateTextButton(" text only interface proxy", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Subject subject = creatInterface(Subject.class);
                subject.test("add new only interface proxy");
                Object object = creatInterface(Object.class);


            }
        }));
        LogText = (TextView) generateTextButton("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mLlContainer.addView(LogText);

        if(null != savedInstanceState)
        {
            int IntTest = savedInstanceState.getInt("IntTest");
            String StrTest = savedInstanceState.getString("StrTest");
            LogText.setText(StrTest);
        }
    }

    private<T> T creatInterface(final Class<T> subjectClass) {
        T proxy = (T) Proxy.newProxyInstance(subjectClass.getClassLoader(), new Class[]{subjectClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before method!");
                LogText.append("invoke : " + "before method! onlyInterface" + "\n");
                Object object = new Object();
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                String text= (String) method.invoke(this, args);
                LogText.append("invoke : " + "get method String === " + text+"\n");
                System.out.println("after method!");
                LogText.append("invoke : " + "after method!  onlyInterface" + "\n");
                return proxy;
            }
        });
        return proxy;
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
        public void test(String s) {
            System.out.println("This is test method");
            LogText.append("SubjectImpl : " + "This is test method" + "\n");
            LogText.append("SubjectImpl : " + "add String   =  " + s + "\n");
        }
    }

    public interface Subject {
        void test(String s);
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("StrTest",LogText.getText().toString() );
        super.onSaveInstanceState(outState);

    }
}
