package engine.rendering.shaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ShaderBuilder {
	public static List<String> readShaderSourceFromFile(String filename) {
		StringBuilder  vsSource = new StringBuilder("");
		StringBuilder  psSource = new StringBuilder("");
		int shaderType = -1;
		
		try (BufferedReader br = new BufferedReader(new FileReader("res/glsl/Default.glsl"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       if (line.contains("#shader")) {
		    	   if (line.contains("vertex")) {
		    		   shaderType = 0;
		    	   } else if (line.contains("pixel")) {
		    		   shaderType = 1;
		    	   }
		       }
		       else {
		    	   (shaderType == 0 ? vsSource : psSource).append(line + "\n");
		       }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Arrays.asList(vsSource.toString(), psSource.toString());
	}
}
