package org.sravan.jsonparse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	public static void main(String[] args) {

		try {

			InputStream is = new FileInputStream("file.json");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			String fileAsString = sb.toString();

			JSONObject jsonObject = new JSONObject(fileAsString);

			JsonUtil jsonUtil = new JsonUtil();

			List<String> list = jsonUtil.parseJson(jsonObject, new ArrayList<String>(), "$");

			list.forEach(e -> System.out.println(e));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<String> parseJson(JSONObject jsonObject, List<String> list, String suffix)
			throws JSONException, ParseException {

		for (String key : jsonObject.keySet()) {

			if (jsonObject.get(key) instanceof JSONArray) {
				getArray(jsonObject.getJSONArray(key), list, suffix + "." + key + "[*]");
			} else {
				if (jsonObject.get(key) instanceof JSONObject) {
					parseJson(jsonObject.getJSONObject(key), list, suffix + "." + key);
				} else {
					list.add(suffix + "." + key);
				}
			}
		}

		return list;

	}

	public void getArray(JSONArray jsonArr, List<String> list, String suffix) throws ParseException, JSONException {

		for (int k = 0; k < 1; k++) {
			if (jsonArr.length() > 0 && jsonArr.get(k) instanceof JSONObject) {
				parseJson((JSONObject) jsonArr.get(k), list, suffix);
			} else if (jsonArr.length() > 0) {
				list.add(suffix);
			}

		}
	}

}
