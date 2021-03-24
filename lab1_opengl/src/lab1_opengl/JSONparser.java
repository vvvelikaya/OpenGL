package lab1_opengl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONparser {

	public static JSONObject readFile(String fileName) throws IOException, ParseException {

		JSONParser parser = new JSONParser();
		Reader reader = new FileReader(fileName);
		JSONObject jsonObject = (JSONObject) parser.parse(reader);

		return jsonObject;

	}

	public static float[] getArray(String obj, JSONObject jsonObject) {
		float[] arr = new float[8];
		String s = "";
		JSONArray el = (JSONArray) jsonObject.get(obj);

		for (Object o : el) {
			s += o.toString();
		}
		s = s.replaceAll("\\[", "");
		int k = 0;
		for (String str : s.split("\\]|\\,|\\[")) {
			arr[k] = Float.parseFloat(str);
			k++;
		}

		return arr;
	}
}
