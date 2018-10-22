package utils;

public class DengJi {
	private DengJi() {

	}

	public static int getFsToDengJi(float fs) {
		if (fs >= 90)
			return 4;
		else if (fs >= 80)
			return 3;
		else if (fs >= 70)
			return 2;
		else if (fs >= 60)
			return 1;
		else
			return 0;
	}
}
