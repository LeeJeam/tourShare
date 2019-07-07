package com.xmyy.search.core;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final public class ReflectUtils {

	public static Field[] getFields(Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		return fields;
	}
	
	public static Map<String, Field> getFieldsAsMap(Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		return Stream.of(fields).collect(Collectors.toMap(Field::getName, f->f));
	}
	

	public static Object getFieldValue(Object obj, String field) {
		Field f = getFieldsAsMap(obj.getClass()).get(field);
		return getFieldValue(f, obj, true);
	}
	
	public static Object getFieldValue(Field f, Object obj, boolean throwIfError) {
		Assert.notNull(f,"f");
		try {
			if (!f.isAccessible())
				f.setAccessible(true);
			return f.get(obj);
		} catch (Exception ex) {
			if (throwIfError)
				throw new RuntimeException("get value of field[" + f + "] error: " + ex.getMessage(), ex);
			else
				return null;
		}
	}

	/**
	 * 改变private/protected的成员变量为public
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
				.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
	 *
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Assert.notNull(obj, "object can't be null");
		Assert.notNull(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field不在当前类定义,继续向上转型
				continue;// new add
			}
		}
		return null;
	}


	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value, boolean throwIfError) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (Exception e) {
			if (throwIfError)
				throw new RuntimeException("set value of field[" + field + "] error: " + e.getMessage(), e);
		}
	}

    	private ReflectUtils(){
	}

}
