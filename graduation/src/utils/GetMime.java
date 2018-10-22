package utils;

import java.util.HashMap;


public class GetMime {
	static HashMap<String, String> allMime = new HashMap<String, String>();
	static String result = null;
	
	static {
		allMime.put("pdf", "appliction/pdf");
		allMime.put("zip", "application/zip");
		allMime.put("rar", "appliction/octet-stream");
		allMime.put("doc", "appliction/msword");
		allMime.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	}
	public static String returnMimeFromExt(String ext) {
		result = allMime.get(ext);
		if("".equals(result));
			result = null;
		return result;
	}
}
