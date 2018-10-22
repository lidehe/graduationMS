package utilsOffice;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用表格中的数据初始化对象
 * 
 * @author DaMoTou
 *
 */
public class InitialInstances {

	public static<T> List<T> doInit(Class<T> c,File file)
			throws IllegalAccessException, InstantiationException {
		List<T> objects = new ArrayList<T>();// 存放初始化得到的对象
		//String path = FileTools.getFilePath();
		//String path=docFilePath;
		List<Method> methods = new ArrayList<>();// 存放calssName类中排序后的方法，
		methods = GetSortedMethodFromClass.getSortedMethods(c);
		Map<Integer, List<String>> dataFromExcel = new HashMap<>();// 存放excel读取到的数据
		dataFromExcel = ReadExcel.getDataFromExcel(file);
		T object = null;
		Method method = null;
		if (dataFromExcel == null) {
			System.out.println("未读取任何数据");
		} else {
			// 对象循环,要从第二行开始读取，因为第一行是标题
			for (int i = 1; i < dataFromExcel.size(); i++) {
				object = c.newInstance();
				// 属性循环,属性个数和类中set方法个数一样
				for (int j = 0; j < dataFromExcel.get(i).size(); j++) {
					String value = dataFromExcel.get(i).get(j);
					method = methods.get(j+1);//因为id是不用给初值的，所以要从第二个属性开始赋值
					System.out.println("方法是："+method.getName()+"值是："+value);
					switch (method.getParameterTypes()[0].getName()) {
					case "java.lang.String":
						try {
							method.invoke(object, value);
						} catch (Exception e) {
							throw new RuntimeException("用excel数据初始化对象失败：string转换异常");
						}
						break;
					case "int":
						try {
							method.invoke(object, Integer.valueOf(value));
						} catch (Exception e) {
							throw new RuntimeException("用excel数据初始化对象失败：int转换异常");
						}
						break;
					case "double":
						try {
							method.invoke(object, Double.valueOf(value));
						} catch (Exception e) {
							throw new RuntimeException("用excel数据初始化对象失败：double转换异常");
						}
						break;
					default:
						break;
					}
				}
				objects.add(object);
			}
		}
		System.out.println(objects==null);
		if (objects.size()>0) {
			return objects;
		}else{
			return null;
		}
	}
}
