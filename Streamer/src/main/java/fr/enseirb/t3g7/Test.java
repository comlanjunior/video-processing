package fr.enseirb.t3g7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.opencv.core.Core;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;


public class Test {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // NOPMD
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		int count=0;
		HeapList<MatOfRect> previousDetections = new HeapList<MatOfRect>(5);

		for (int j = 0; j < 10; j++) {
			
			JSONParser parser = new JSONParser();
			  
	            Object obj = parser.parse(new FileReader(
	                    "src/main/resources/rects.json"));
	 
	            JSONObject jsonObject = (JSONObject) obj;
	            
	            Rect[] rects = new Rect[((JSONArray) jsonObject.get("rects")).size()];
	            for (int i = 0; i < rects.length; i++) {
					rects[i] = new Rect(((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("x")).intValue(),
							((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("y")).intValue(),
							((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("width")).intValue(),
							((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("height")).intValue());
				}
				MatOfRect currentDetection = new MatOfRect(rects);
				
				
				
				Object obj2 = parser.parse(new FileReader(
	                    "src/main/resources/rects2.json"));
	 
	            JSONObject jsonObject2 = (JSONObject) obj2;
	            
	            Rect[] rects2 = new Rect[((JSONArray) jsonObject2.get("rects")).size()];
	            for (int i = 0; i < rects.length; i++) {
					rects2[i] = new Rect(((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("x")).intValue(),
							((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("y")).intValue(),
							((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("width")).intValue(),
							((Long) ((JSONObject) ((JSONArray) jsonObject.get("rects")).get(i)).get("height")).intValue());
				}
				MatOfRect foundFaces = new MatOfRect(rects2);
				
				count = count + PeopleTrack.countNewPersons(currentDetection,
						previousDetections, foundFaces);
				previousDetections.queue(new MatOfRect(currentDetection));
			
		}
            System.out.println(count);
	}

}
