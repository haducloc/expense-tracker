package com.expensetracker.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.appslandia.common.utils.StringUtils;
import com.expensetracker.entities.Expense;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ResUtils {

	public static void main(String[] args) {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		classes.add(Expense.class);

		genRes(classes);
	}

	static void genRes(List<Class<?>> classes) {
		for (Class<?> clazz : classes) {
			System.out.println();
			System.out.println(StringUtils.firstLowerCase(clazz.getSimpleName(), Locale.ENGLISH) + "=" + clazz.getSimpleName());

			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {

				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}

				System.out.println(StringUtils.firstLowerCase(clazz.getSimpleName(), Locale.ENGLISH) + "." + field.getName() + "="
						+ StringUtils.firstUpperCase(field.getName(), Locale.ENGLISH));
			}
		}
	}
}
