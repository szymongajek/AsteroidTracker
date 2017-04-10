package com.sz.asteroid;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UtilsIO {

	/**
	 * Return string content of src main resources file
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFileMainResources (String fileName, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get("src","main","resources",fileName));
			  return new String(encoded, encoding);
			}
	
	

	/**
	 * Return string content of src test resources file
	 * 
	 * @param fileName
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFileTestResources (String fileName, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get("src","test","resources",fileName));
			  return new String(encoded, encoding);
			}
	
}
