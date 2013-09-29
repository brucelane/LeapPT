import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class PlayerData {
	
	JSONObject obj = new JSONObject();
	JSONArray list = new JSONArray();
	
	PlayerData(){
		
		
	}
	public void addData() {
		 
		
		obj.put("name", "mkyong.com");
		obj.put("age", new Integer(100));
	 
		
		list.add("msg 1");
		list.add("msg 2");
		list.add("msg 3");
	 
		obj.put("messages", list);
	 
		try {
	 
			FileWriter file = new FileWriter("data/playerData.json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		System.out.print(obj);
	 
	     }
	 
}
