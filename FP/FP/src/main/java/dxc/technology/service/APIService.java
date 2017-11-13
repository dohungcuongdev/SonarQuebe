package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;

import dxc.technology.model.DailyData;

public interface APIService {

	public List<String> getKeyAPI(String head, String foot) throws IOException, ParseException, JSONException;

	public JSONArray getValueAPI(String key) throws IOException, ParseException, JSONException;

	public ArrayList<DailyData> getDailyData(Date date);
}
