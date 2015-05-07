/* $Id$*/

/**
 * ApiConstants.java
 *
 *
 * Created: DEC 24 2014
 *
 * @author <a href="mailto: meganath.v@">Meganath V</a>
 * @version
 *
 * Last Edited By : <a href="mailto: meganath.v@">Meganath V</a>
 **/

package wh.constants; 

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class ApiConstants
{
    //DEVELOPEMENT MODE CHECK
    //TODO: Have to move this constant varibale to properties file.
    public static final boolean IS_DEVELOPMENT_MODE=true;
    
    //API VERSION NUMBER
    public static final String API_VERSION="/v1"; 
    
    //WEB PAGE CONTEXT NAME
    public static final String CONTEXT_NAME="/"; 
    
    //NotifyMe
    public static final String API_ADD_CONTACT_IN_NOTIFYME="/addcontact/notify"; 
    public static final String API_GET_NOTIFY_USER_DETAILS="/getcontacts/notify"; 
    
    //Contactus
    public static final String API_ADD_CONTACT_DETAILS_ADD=(String) getAPIVersion()+"/contact/add"; 
    public static final String API_CONTACT_DETAILS=(String) getAPIVersion()+"/contact/fetch";
    
     //PsdtoHTML
    public static final String API_ADD_PSDTOHTML_ORDERNOW_DATA= (String) getAPIVersion()+"/psdtohtml/addorder"; 
     
    //PsdtoEMAIL
    public static final String API_ADD_PSDTOEMAIL_ORDERNOW_DATA= (String) getAPIVersion()+"/psdtoemail/order/add"; 
    
    //WEBDESIGN ORDER
    public static final String API_ADD_WEBDESIGN_ORDERNOW_DATA= (String) getAPIVersion()+"/webdesign/order/add"; 
    
    //WORDPRESS ORDER
    public static final String API_ADD_WORDPRESS_ORDERNOW_DATA= (String) getAPIVersion()+"/wordpress/order/add"; 
    
    //copied from megan source start
    public static final String API_ADD_NOTIFYME_CONTACT="/notifyme/addcontact"; 
    public static final String API_GET_NOTIFYME_CONTACT_DETAILS="/notifyme/getcontact";
    public static final String API_ADD_ATTACHEMENT_DETAILS="attachment/upload";
    public static final String API_GET_ATTACHMENT_DETAILS="attachment/get";
    public static final String API_ADD_CLIENT_DETAILS="client/add"; 
    public static final String API_GET_CLIENT_DETAILS="client/get";
    public static final String API_ADD_PSDTOEMAIL_ORDER="psdtoemail/addorder";
    public static final String API_GET_PSDTOEMAIL_ORDER_DETAILS="psdtoemail/orderdetails";
    public static final String API_ADD_PSDTOHTML_ORDER="psdtohtml/addorder";
    public static final String API_GET_PSDTOHTML_ORDER_DETAILS="psdtohtml/orderdetails";
    public static final String API_ADD_WEBDESIGINING_ORDER="webdesign/addorder";
    public static final String API_GET_WEBDESIGINING_ORDER="webdesign/get";
    //copied from megan source end
    
    
    //Error  Message/Code with response
    public static final Integer API_SUCCESS_CODE= 200;
    public static final String API_SUCCESS_MSG= "Success"; // MSG: Success 
    public static final String API_MANDATORY_FIELDS_ARE_NOT_FOUND_MSG="Some mandatory fields are missing";
    public static final String API_MANDATORY_URL_PATTERN_ERROR="Please check your URL";
    
    //COMMON ERROR CODES
    //Start from 600 - 800
    public static final Integer API_MANDATORY_FIELDS_ARE_NOT_FOUND_CODE=601;
    public static final Integer API_MANDATORY_URL_PATTERN_ERROR_CODE=602;
    
    //ORDER SECTION ERROR 2001-3000
    //copied from @megan source start
    public static final Integer API_ADDING_ERROR_IN_NOTIFYME= 2001;
    public static final Integer API_ADDING_ERROR_IN_ATTACHMENT=2002;
    public static final Integer API_ADDING_ERROR_IN_CLIENT_DETAILS=2003;
    public static final Integer API_ADDING_ERROR_IN_PSDTOEMAILORDER=2004;
    public static final Integer API_ADDING_ERROR_IN_PSDTOHTMLORDER=2005;
    public static final Integer API_ADDING_ERROR_IN_WEBDESIGNINIG_ORDER=2006;
    public static final Integer API_ADDING_ERROR_IN_WORDPRESS_ORDER=2007;
    //copied from @megan source end
    
    
    
    /**
     *	API's one time mandatory params loader. 
     **/
    private static final Map API_MANDATORY_FIELDS = Collections.unmodifiableMap(loadApiMandatoryFields());
    private HashMap paramMap;

    public static String getActionValue(String key)
    {
        return ((String)API_MANDATORY_FIELDS.get(key));
    }

    /**
     *	API's one time mandatory params loader. 
     **/

    private static HashMap loadApiMandatoryFields()
    {
        HashMap map = new HashMap();
        map.put(API_ADD_CONTACT_IN_NOTIFYME,"email");
        map.put(API_ADD_CONTACT_DETAILS_ADD,"email,name,message");
        map.put(API_ADD_PSDTOHTML_ORDERNOW_DATA,"layout,name,emailid,phonenumber,projectbrief");
        map.put(API_ADD_PSDTOEMAIL_ORDERNOW_DATA,"numberoftemplate,IS_HAVE_INTEGRATION_OPTION,name,emailid,phonenumber,projectbrief");
        map.put(API_ADD_WEBDESIGN_ORDERNOW_DATA,"name,emailid,phonenumber,projectbrief");
        map.put(API_ADD_WORDPRESS_ORDERNOW_DATA,"name,emailid,phonenumber,projectbrief");
        map.put(API_CONTACT_DETAILS,"clientKey");
        //PSDTOHTML Ordernow form submision
        
        return map;
    }
    
    public static String getAPIVersion()
    {
        return ((String)API_VERSION);
    }
    public static String getContext()
    {
        return ((String)CONTEXT_NAME);
    }

    
}
