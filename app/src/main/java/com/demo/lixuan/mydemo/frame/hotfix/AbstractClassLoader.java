package com.demo.lixuan.mydemo.frame.hotfix;

/**
 * className: AbstractClassLoader
 * description:研究热更新原理，建立自定义类加载器
 *原理
 * 通过构造一个DexClassLoader对象来加载我们的热更新dex文件；
 * 通过反射获取系统默认的PathClassLoader.pathList.dexElements；
 * 将我们的热更新dex与系统默认的Elements数组合并，同时保证热更新dex在系统默认Elements数组之前；
 * 将合并完成后的数组设置回PathClassLoader.pathList.dexElements。
 *
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/6/19 14:22
 */
public class AbstractClassLoader {

}
