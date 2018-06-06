package com.demo.lixuan.mydemo.java.designModel.ObjectPool;

import android.util.SparseArray;

/**
 * Created by Administrator on 2018/6/6.
 */

public abstract class ObjectPool<T> {

    private final int maxCapacity;
    private SparseArray<T> mFreePool;
    private SparseArray<T> mLentPool;

    public ObjectPool(int initialCapacity, int maxCapacity) {
        initialize(initialCapacity);
        this.maxCapacity = maxCapacity;
    }

    public ObjectPool(int maxCapacity) {
       this(maxCapacity/2,maxCapacity);
    }

    private void initialize(int initialCapacity) {
        mLentPool = new SparseArray<>();
        mFreePool = new SparseArray<>();
        for (int i = 0; i < initialCapacity; i++) {
            mFreePool.put(i,creat());
        }
    }

    protected abstract T creat();

    public T acquire(){
        T t = null;
        synchronized (mFreePool){
            int freeSize = mFreePool.size();
            for (int i = 0; i < freeSize; i++) {
                int key = mFreePool.keyAt(i);
                t = mFreePool.get(key);
                if (t!= null){
                    this.mLentPool.put(key,t);
                    this.mFreePool.remove(key);
                    return t;
                }
            }

            if (t ==null &&mLentPool.size() + mFreePool.size() <maxCapacity){
             t = creat();
                mLentPool.put(mLentPool.size() + freeSize,t);
            }
        }
        return t;
    }

    public void release(T t){
        if (t == null){
           return;
        }
        int key = mLentPool.indexOfValue(t);
        restore(t);
        this.mFreePool.put(key,t);
        this.mLentPool.remove(key);
    }

    private void restore(T t) {

    }


}
