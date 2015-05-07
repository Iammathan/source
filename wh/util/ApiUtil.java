
/**
 * ApiUtil.java
 *
 *
 * Created: DEC 24 2014
 *
 * @author Meganath V</a>
 * @version
 *
 * Last Edited By : Meganath V</a>
 *
 */
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

public class ApiUtil {

    private static Logger logger = Logger.getLogger(ApiUtil.class.getName());

    public static String handleFinalResponse(HttpServletRequest request, HttpServletResponse response, HashMap result) {
        
        //"Access-Control-Allow-Origin"
        response.setHeader("Access-Control-Allow-Origin", "*");
        //response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        String res = null;

        try {
            //SET RESPONSE CODE DEPEND ON THE RESULT
            if(result != null && !result.isEmpty())
            {
                response.setStatus(Integer.valueOf(result.get("code").toString()));
            }
            System.out.println("response code :::"+response.getStatus());
            System.out.println("response getContentType :::"+response.getContentType());
            JSONObject json = new JSONObject();
            JSONObject resp = new JSONObject(result);
            json.put("response", resp);
            res = json.toString();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            res = "{\"response\":{\"message\":\"An unknown error occurred. Please contact support at support@workhtml.com.\",\"code\":\"-2\",\"uri\":\"\",\"version\":\"\"}}";
        }
        return res;
    }
}
