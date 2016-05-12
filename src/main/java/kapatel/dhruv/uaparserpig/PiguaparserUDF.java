/**
 * PiguaparserUDF.java.
 
 * Copyright (C) 2016 kapatel dhruv,
 * https://github.com/kapateldhruv
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package kapatel.dhruv.uaparserpig;


import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import ua_parser.Client;
import ua_parser.Parser;

/**
 * This is a UDF extract property from useragent string. 
 * Used https://github.com/tobie/ua-parser/tree/master/java library.
 *
 * The function will need two arguments. 
 * 1. useragent string
 * 2. attribute name (USERAGENT_FAMILY, USERAGENT_MAJOR, USERAGENT_MINOR, OS_FAMILY, OS_MAJOR, OS_MINOR or DEVICE_FAMILY)
 * 
 */

public class PiguaparserUDF extends EvalFunc<String> {
    public String exec(Tuple input) throws IOException {
        if (input == null ||  input.size() == 0)
            return null;
        try {
        	
        	if(input.size() != 2)
        	{
        		throw new IllegalArgumentException("This function takes two arguments as input.");
        	}
        	
        	String uaString = input.get(0).toString();
    		String attributeName = input.get(1).toString();
        	
    		Parser uaParser= null;
    		try 
    		{
    			uaParser = new Parser();
    		} 
    		catch (IOException e) 
    		{
    			//e.printStackTrace();
    			 throw new RuntimeException("Could not instantiate User-Agent parser.");
    		}
    		 
    		Client c = uaParser.parse(uaString);
    		
    		// Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3
    		
    		switch(attributeName)
    		{
    			case "USERAGENT_FAMILY":   
    				return c.userAgent.family;  // => "Mobile Safari"
    						
    			case "USERAGENT_MAJOR":
    				return c.userAgent.major; 	// => "5"	
    			
    			case "USERAGENT_MINOR":
    				return c.userAgent.minor;	// => "1"
    				
    			case "OS_FAMILY":
    				return c.os.family;		// => "iOS"
    			
    			case "OS_MAJOR":
    				return c.os.major; 	// => "5"
    			
    			case "OS_MINOR":
    				return c.os.minor;	// => "1"
    			
    			case "DEVICE_FAMILY":
    				return c.device.family;	 // => "iPhone"
    				
    			default:
    				throw new IllegalArgumentException("Invalid attrubute name");
    		}
           
                    

        } catch (Exception e) {
            // TODO: handle exception
        	 throw new IOException("Caught exception processing input row ", e);
        }
    }
}
