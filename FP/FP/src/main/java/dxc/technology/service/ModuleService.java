package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;

public interface ModuleService {

	public void getModule(List<String> listKey) throws ParseException, IOException, JSONException;

	public ArrayList<Integer> getIdModule();

	public int findId(String key);
	
	public ArrayList<String> getListKey();
}
