/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmyy.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author zlp
 */
public class Reflection {

    /**
     * 反射获取类或实例的属性.
     *
     * @param clazz 实例的类型
     * @param name 属性的名称
     * @return 属性
     */
    public static Field findField(Class<?> clazz, String name) {
        while (true) {
            try {
                Field field = clazz.getDeclaredField(name);
                return field;
            } catch (NoSuchFieldException ex) {
                if (clazz == Object.class) {
                    return null;
                }
                clazz = clazz.getSuperclass();
            } catch (SecurityException ex) {
                return null;
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
    }    

    /**
     * 反射获取实例的属性值.
     *
     * @param instance 实例
     * @param name 属性的名称
     * @return 属性的值
     */
    public static Object getField(Object instance, String name) {
        Class<? extends Object> clazz = instance.getClass();
        return getField(clazz, instance, name);
    }

    /**
     * 反射获取类或实例的属性值.
     *
     * @param clazz 实例的类型
     * @param instance 实例，获取静态属性值时可以为空
     * @param name 属性的名称
     * @return 属性的值
     */
    public static Object getField(Class<? extends Object> clazz, Object instance, String name) {
        while (true) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return field.get(instance);
            } catch (NoSuchFieldException ex) {
                if (clazz == Object.class) {
                    return null;
                }
                clazz = clazz.getSuperclass();
            } catch (SecurityException ex) {
                return null;
            } catch (IllegalArgumentException ex) {
                return null;
            } catch (IllegalAccessException ex) {
                return null;
            }
        }
    }

    /**
     * 反射设置实例的属性值.
     *
     * @param instance 实例
     * @param name 属性的名称
     * @param value 属性的值
     */
    public static void setField(Object instance, String name, Object value) {
        Class<? extends Object> clazz = instance.getClass();
        setField(clazz, instance, name, value);
    }

    /**
     * 反射设置类或实例的属性值.
     *
     * @param clazz 实例的类型
     * @param instance 实例，设置静态属性值时可以为空
     * @param name 属性的名称
     * @param value 属性的值
     */
    public static void setField(Class<? extends Object> clazz, Object instance, String name, Object value) {
        while (true) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                field.set(instance, value);
                return;
            } catch (NoSuchFieldException ex) {
                if (clazz == Object.class) {
                    return;
                }
                clazz = clazz.getSuperclass();
            } catch (SecurityException ex) {
                return;
            } catch (IllegalArgumentException ex) {
                return;
            } catch (IllegalAccessException ex) {
                return;
            }
        }
    }

    /**
     * 反射调用实例的方法.
     *
     * @param instance 实例
     * @param name 方法的名称
     * @param parameterTypes 参数的类型数组
     * @param parameters 参数数组
     * @return 方法返回值
     */
    public static Object invoke(Object instance, String name, Class<?>[] parameterTypes, Object[] parameters) {
        Class<? extends Object> clazz = instance.getClass();
        return invoke(clazz, instance, name, parameterTypes, parameters);
    }

    /**
     * 反射调用类或实例的方法.
     *
     * @param instance 实例
     * @param name 方法的名称
     * @param parameters 参数数组
     * @return 方法返回值
     */
    public static Object invoke(Object instance, String name, Object... parameters) {
        Class<? extends Object> clazz = instance.getClass();
        return invoke(clazz, instance, name, parameters);
    }

    /**
     * 反射调用类或实例的方法.
     *
     * @param clazz 实例的类型
     * @param instance 实例，调用静态方法时可以为空
     * @param name 方法的名称
     * @param parameters 参数数组
     * @return 方法返回值
     */
    public static Object invoke(Class<? extends Object> clazz, Object instance, String name, Object... parameters) {
        Class<?>[] parameterTypes = new Class<?>[parameters == null ? 0 : parameters.length];
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
        }
        return invoke(clazz, instance, name, parameterTypes, parameters);
    }

    /**
     * 反射调用类或实例的方法.
     *
     * @param clazz 实例的类型
     * @param instance 实例，调用静态方法时可以为空
     * @param name 方法的名称
     * @param parameterTypes 参数的类型数组
     * @param parameters 参数数组
     * @return 方法返回值
     */
    public static Object invoke(Class<? extends Object> clazz, Object instance, String name, Class<?>[] parameterTypes, Object[] parameters) {
        while (true) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                return invoke(instance, method, parameters);
            } catch (NoSuchMethodException e) {
                if (clazz == Object.class) {
                    return null;
                }
                clazz = clazz.getSuperclass();
            } catch (SecurityException ex) {
                return null;
            }
        }
    }

    /**
     * 反射调用类或实例的方法.
     *
     * @param instance 实例，调用静态方法时可以为空
     * @param method 要调用的方法
     * @param parameters 参数数组
     * @return 方法返回值
     */
    public static Object invoke(Object instance, Method method, Object[] parameters) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, parameters);
        } catch (SecurityException ex) {
            return null;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    /**
     * 反射调用实例的方法.
     *
     * @param instance 实例，调用静态方法时可以为空
     * @param name 方法的名称
     * @param params 成对出现的参数数组，前一个为参数的类型，后一个为参数的值
     * @return 方法返回值
     */
    public static Object invoke1(Object instance, String name, Object... params) {
        Class<? extends Object> clazz = instance.getClass();
        return invoke1(clazz, instance, name, params);
    }

    /**
     * 反射调用类或实例的方法.
     *
     * @param clazz 实例的类型
     * @param instance 实例，调用静态方法时可以为空
     * @param name 方法的名称
     * @param params 成对出现的参数数组，前一个为参数的类型，后一个为参数的值
     * @return 方法返回值
     */
    public static Object invoke1(Class<? extends Object> clazz, Object instance, String name, Object... params) {
        if (params.length % 2 == 1) {
            throw new IllegalArgumentException();
        }
        int length = params.length / 2;
        Class<?>[] parameterTypes = new Class<?>[length];
        Object[] parameters = new Object[length];
        for (int i = 0; i < params.length; i += 2) {
            int j = i / 2;
            if (params[i] == null) {
                parameterTypes[j] = params[i + 1].getClass();
            } else if (params[i] instanceof Class<?>) {
                parameterTypes[j] = (Class<?>) params[i];
            } else {
                throw new IllegalArgumentException();
            }
            parameters[j] = params[i + 1];
        }
        return invoke(clazz, instance, name, parameterTypes, parameters);
    }

    public static Object newInstance(Class<? extends Object> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        }
    }
}
