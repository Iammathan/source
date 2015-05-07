/* $Id$*/

/**
 * ApiParamsValidator.java
 *
 *
 * Created: DEC 24 2014
 *
 * @author <a href="mailto: meganath.v@">Meganath V</a>
 * @version
 *
 * Last Edited By : <a href="mailto: meganath.v@">Meganath V</a>
 **/

package wh.util; 

import java.util.*;
import java.io.*;
import org.json.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.logging.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wh.constants.ApiConstants; 

public class ApiParamsValidator
{
	private static Logger logger = Logger.getLogger(ApiParamsValidator.class.getName());

	public static HashMap requestParamsValidation(HttpServletRequest request,HttpServletResponse response,String action) throws Exception
	{
		try
		{
			// Get mandatory params from constant.
			//action - This is the param used for idendify the request actions, ex:::- adduser/notifyme 
                        //AuthKey is mandatory for all the action. (encrypted key)
                        logger.log(Level.INFO, "requestParamsValidation:::"+action);
                        
			HashMap params  = new HashMap();
			boolean allMandatoryFieldsPresent=false;
			String validParams = ApiConstants.getActionValue(action);
                        System.out.println("validParams >>>>>>>"+validParams);
                        //get parameter map from JSON
			HashMap paramMap = getParamsMap(request,response);
                        
                        //SET REQUEST METHOD TYPE
                        String REQUESTED_METHOD_TYPE=request.getMethod();
                        params.put("REQUESTED_METHOD_TYPE", REQUESTED_METHOD_TYPE);
                        
                        System.out.println("REQUESTED_METHOD_TYPE >>>>>>"+REQUESTED_METHOD_TYPE);

			
			if(validParams != null)
			{
                                String[] paramsArr = validParams.split(",");
				for(int i=0; i < paramsArr.length; i++)
				{
					String f = paramsArr[i];
					if(paramMap.containsKey(f))
					{
                                                System.out.println("mandatory param are >>>"+f);
						allMandatoryFieldsPresent = true;		
					}
					else
					{
                                            System.out.println("missed param map >>>"+f);
						allMandatoryFieldsPresent = false;
						params.put("missing_param",f);
						params.put("mandatory_check","failure"); //
						//params.put("action",action); //if need will add
						break;
					}
				}
			}
                        System.out.println("allMandatoryFieldsPresent >>>>>"+allMandatoryFieldsPresent);
			if(allMandatoryFieldsPresent)
			{
				paramMap.put("mandatory_check","success");
				//paramMap.put("action",action); //if need will add
				return paramMap;
			}
			else
			{
                                
				return params;
			}
		}
		catch(Exception e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e); // This case will not occur
		}
		return null;
	}

	public static HashMap getParamsMap(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HashMap paramMap = new HashMap();
		try
        {
            // Get all params from request,.

            String paramName="";
            String paramValue="";
            String uri = request.getRequestURI();
            paramMap.put("uri",uri);

            Enumeration e = request.getParameterNames();
            while(e.hasMoreElements())
            {
                paramName = (String)e.nextElement();
                paramValue = (String)request.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }	

            /*JSONObject jsnData = new JSONObject(paramMap.get("data").toString());	
            if (jsnData!=null && !jsnData.equals("null"))
            {
                Iterator iter = jsnData.keys();
                while(iter.hasNext())
                {
                    String key = (String)iter.next();
                    String value = jsnData.getString(key);
                    paramMap.put(key,value);
                }
            }*/
        }
		catch (Exception e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e); // This case will not occur
		}
                System.out.println("getParamsMap:: paramMap :::"+paramMap);
		return paramMap;
	}
}
