package lukfor.converter;
import java.awt.Color;

import genepi.base.Tool;
import genepi.io.text.LineReader;
import genepi.io.text.LineWriter;

public class JsonToGeoJson extends Tool {

	public JsonToGeoJson(String[] args) {
		super(args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		System.out.println("GeoJsonConverter - Sebastian Schönherr u Lukas Forer.");

	}

	@Override
	public int run() {

		int indexJson = 7;
		int indexValue = 3;
		
		String input = getValue("input").toString();
		String output = getValue("output").toString();

		System.out.println("Reading file " + input);
		
		try {

			LineWriter writer = new LineWriter(output);

			// writer header
			writer.write("{");
			writer.write("\"type\": \"FeatureCollection\",");
			writer.write("\"features\": [");

			// find max
			float maxValue = 0;
			float minValue = Float.MAX_VALUE;
			LineReader reader2 = new LineReader(input);
			while (reader2.next()) {
				String[] tiles = reader2.get().split("\t");
				try{
				float value = Float.parseFloat(tiles[indexValue]);
				if (value > maxValue) {
					maxValue = value;
				}
				
				if (value < minValue){
					minValue = value;
				}
				}catch(Exception e){
					
				}
			}
			reader2.close();

			LineReader reader = new LineReader(input);

			boolean first = true;

			System.out.println("MinValue: "+minValue);
			System.out.println("MaxValue: "+maxValue);
			
			while (reader.next()) {

				if (first) {
					first = false;
				} else {
					writer.write(",");
				}
				float value = minValue;
				String[] tiles = reader.get().split("\t");
try{
				value = Float.parseFloat(tiles[indexValue]);

				/*if (value < 5000){
					value = 5000;
				}*/
				
				/*if (value > 1000){
					value = 1000;
				}*/
				
}catch(Exception e){
	
}
				double alpha = MathHelper.map(value, minValue, maxValue, 0f, 1f);

				Color color = ColorHelper.numberToColorPercentage(alpha);				
				String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
				writer.write("{");
				writer.write("\"type\": \"Feature\",");
				writer.write("\"properties\": {\"stroke-width\": 0, \"fill\": \""
						+ hex + "\", \"fill-opacity\":\"0.5\"},");
				writer.write("\"geometry\": ");
				writer.write(tiles[indexJson].replaceAll("'", "\""));

				writer.write("}");
			}

			// write footer
			writer.write("]");
			writer.write("}");

			writer.close();
			reader.close();

			System.out.println("Created file " + output);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	@Override
	public void createParameters() {
		addParameter("input", "Input file");
		addParameter("output", "Output file");
	}

	public static void main(String[] args) {

		new JsonToGeoJson(args).start();
	}

}
