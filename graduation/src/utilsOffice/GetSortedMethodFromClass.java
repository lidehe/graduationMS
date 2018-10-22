package utilsOffice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 因为获取的类的方法是无序的，对该类的对象的初始化会出错; 而获取到的属性是有序的，所以通过把属性名第一个字母大写，加上set，拼接成方法名
 * 这样方法名字就有序了
 * 
 * @author DaMoTou
 *
 */
public class GetSortedMethodFromClass {

	/**
	 * 类名要首字母大写
	 * 
	 * @param className
	 * @return
	 */
	public static<T> List<Method> getSortedMethods(Class<T> c) {
		List<Method> sortedMethods = new ArrayList<>();
//		
		Field[] fields = c.getDeclaredFields();
		Method[] methods = c.getMethods();
		
		for (int n = 0; n < fields.length; n++) {
			String methodName = fields[n].getName();
			Character firstChar = methodName.charAt(0);
			Character big = firstChar.toUpperCase(firstChar);
			// 根据属性名字拼接方法名字，只用到set方法，所以方法名字开头是set
			methodName = "set" + String.valueOf(big) + methodName.substring(1, methodName.length());
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().equals(methodName)) {
					sortedMethods.add(methods[i]);
				}
			}
		}
		return sortedMethods;
	}
	
	/**
	 * 类名要首字母大写
	 * 
	 * @param className
	 * @return
	 */
	public static<T> List<Method> getSortedGetMethods(Class<T> c) {
		List<Method> sortedMethods = new ArrayList<>();
//		Class<?> c = null;
//		try {
//			c = Class.forName("domain." + className);
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("类信息加载失败" + e);
//		}
		Field[] fields = c.getDeclaredFields();
		Method[] methods = c.getMethods();
		for (int n = 0; n < fields.length; n++) {
			String methodName = fields[n].getName();
			Character firstChar = methodName.charAt(0);
			Character big = firstChar.toUpperCase(firstChar);
			// 根据属性名字拼接方法名字，只用到set方法，所以方法名字开头是set
			methodName = "get" + String.valueOf(big) + methodName.substring(1, methodName.length());
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().equals(methodName)) {
					sortedMethods.add(methods[i]);
				}
			}
		}
		return sortedMethods;
	}
}
