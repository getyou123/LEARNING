package org.example;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


class ReflectLearnUse {
    int i;
    int j;

    public ReflectLearnUse() {
    }

    public ReflectLearnUse(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public ReflectLearnUse(int i) {
        this.i = i;
        this.j = 0;
    }

    public int add(int sum) {
        this.i = this.i + sum;
        return this.i;
    }

    @Override
    public String toString() {
        return "ReflectLearnUse{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}

public class ReflectLearn {


    // 测试获取Class对象
    @Test
    public void testGetClasszz() throws ClassNotFoundException {
        System.out.println(String.class);
        System.out.println("test".getClass());
        Class<?> aClass = Class.forName("org.example.ReflectLearn");
        System.out.println(aClass);
    }


    // 测试获取Class对象并获取对象
    @Test
    public void testGetClassInstance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName("org.example.ReflectLearnUse");
        // 通过无参构造函数获取对象
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(int.class, int.class);
        Object newInstance = declaredConstructor.newInstance(1, 2);
        System.out.println(newInstance);
        ReflectLearnUse r1 = (ReflectLearnUse) newInstance;
        System.out.println(r1);
    }


    // 测试通过Class对象获取类的信息
    @Test
    public void testGetClassInfo() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("org.example.ReflectLearnUse");
        //1.实现的全部接口
        Class<?>[] interfaces = aClass.getInterfaces();
        System.out.println(interfaces);

        //2.所继承的父类
        Class<?> superclass = aClass.getSuperclass();
        System.out.println(superclass);

        //3.全部的构造器 返回此 Class 对象所表示的类的所有public构造方法。
        Constructor<?>[] constructors = aClass.getConstructors();
        System.out.println(constructors);

        //返回此 Class 对象表示的类声明的所有构造方法。
        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
        System.out.println(declaredConstructors);


        //4.全部的方法
        //返回此Class对象所表示的类或接口的全部方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m);
        }


        //5.全部的Field
        Field[] fields = aClass.getFields();
        System.out.println(fields);

        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println(f);
        }

        //6. 获取类的注解
        Annotation[] annos = aClass.getAnnotations();
        for (Annotation anno : annos) {
            System.out.println(anno);
        }

    }


    @Test
    public void testGetClassFiledAndInvokeMethod() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> classzz = Class.forName("org.example.ReflectLearnUse");
        Constructor<?> declaredConstructor = classzz.getDeclaredConstructor(int.class, int.class);
        ReflectLearnUse obj = (ReflectLearnUse) declaredConstructor.newInstance(1, 2);


        // 获取数据域
        Field fieldI = classzz.getDeclaredField("i");
        fieldI.setAccessible(true);  // 对于私有域进行设置

        Object value = fieldI.get(obj);
        System.out.println("i = " + value);

        // 设置数据域
        fieldI.set(obj, 2);
        System.out.println(obj);


        //获取方法 执行方法
        Method add1 = classzz.getDeclaredMethod("add", int.class);
        add1.setAccessible(true);

        Object invokedRes = add1.invoke(obj, 5);
        System.out.println(invokedRes);
        System.out.println(obj);

    }


}
