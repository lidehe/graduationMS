package utils;

import java.text.DecimalFormat;

public class FloatUtil {
	// 这个类不能实例化
		private FloatUtil() {

		}
		
		public static float floatSaveTwoD(double d) {
			DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
			return Float.valueOf(decimalFormat.format(d));
		}
}
