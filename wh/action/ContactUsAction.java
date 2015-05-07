/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wh.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import wh.bean.CommonBean;
import wh.constants.ApiConstants;

import wh.util.ApiParamsValidator;
import wh.util.ApiUtil;
import wh.model.Contactus;
import wh.util.MailUtil;

/**
 *
 * @author meganath-1399
 */
public class ContactUsAction extends org.apache.struts.action.Action {
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    //Logging 
    private static Logger logger = Logger.getLogger(ApiParamsValidator.class.getName());
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("ContactUs Action called>>>>>>");
            request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try{
            //SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();    
            HashMap result = process(request, response);
            String finalresult =(String) ApiUtil.handleFinalResponse(request,response,result);
            out.println(finalresult);
            //out.write(finalresult);
            //out.flush();
            //out.close();

            return null;
        }catch (Exception ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            ex.printStackTrace();
            throw new Exception(ex);
        }
    }
    
    public HashMap process(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
    {
        
                        
        HashMap result = new HashMap();
        try
        {
            String uri = (String) request.getRequestURI();
            System.out.println("contact ad >> uri:::"+uri);
            HashMap aftrValidateParams = ApiParamsValidator.requestParamsValidation(request,response,uri);
            logger.log(Level.INFO,"aftrValidateParams ::::::"+aftrValidateParams);

            String mandatory_check = (String) aftrValidateParams.get("mandatory_check");
            System.out.println("conatctusactoion.java mandatory_check >>>>>>"+mandatory_check);
            CommonBean cb =  new CommonBean();
            Contactus contactus = new Contactus();
            if(mandatory_check!=null && mandatory_check.equals("success"))
            {
                if(uri!=null)
                {
                    
                    if(uri.contains(ApiConstants.API_ADD_CONTACT_DETAILS_ADD))
                    {
                        // uri : /contact/add
                        
                        //Add common data for all details going to add
                        contactus.setGeneratedTokenId(System.currentTimeMillis());
                        contactus.setTime(System.currentTimeMillis());
                        
                        Iterator itr = aftrValidateParams.keySet().iterator();
                        while(itr.hasNext())
                        {
                            String key= itr.next().toString(); 
                            if(key!=null && !key.equals("null"))
                            {
                                if(key.equalsIgnoreCase("email"))
                                {
                                    contactus.setEmail((String)aftrValidateParams.get(key));
                                }else if(key.equalsIgnoreCase("name"))
                                {
                                    contactus.setName((String)aftrValidateParams.get(key));
                                }else if(key.equalsIgnoreCase("message"))
                                {
                                    contactus.setMessage((String)aftrValidateParams.get(key));
                                }
                            }
                        }
                        Long id = cb.addData(contactus);
                        result.put("id", id);
                        result.put("uri", uri);
                        result.put("code", ApiConstants.API_SUCCESS_CODE);
                        result.put("result", "success");
                        result.put("message", "contact added sucessfully");
                        logger.log(Level.INFO,"ADD CONTACT DETAILS RESPONSE ::::::"+result);
                        
                        //SEND A MAIL TO USER "THANK GIVING MAIL FOR CONTACT"
                        //MAIL VARIABLES 
                        /*String subject = "WorkHTML Mail Notofication";
                        String fromEmailAddress = "support@workhtml.com";
                        String toEmail = "support@workhtml.com";
                        String mailContent = "Here we are going to put original mail content";
                        String mailContentType = "text/html";
                        String senderDesc = "Sender name : Name";
                        
                        MailUtil mailUtil = new MailUtil();
                        mailUtil.despatch(subject, toEmail, mailContent, mailContentType, fromEmailAddress, null, null, false, senderDesc, null, null);                    
                        */
                        return result;
                    }
                    //FETCH CONTACT DETAILS FROM Contactus TABLE
                    // uri : /contact/fetch
                    else if(uri.contains(ApiConstants.API_CONTACT_DETAILS))
                    {
                        List cDetails=cb.getDetails("Contactus");
                    }
                }
            }
            else
            {
                
                System.out.println("Mandatory check failed for contactus action*******");
                
                //Here we going to give specific error inforamtion to requested action.
                String errmsg = ApiConstants.API_MANDATORY_URL_PATTERN_ERROR;
                Integer errcode = ApiConstants.API_MANDATORY_URL_PATTERN_ERROR_CODE;
                if(mandatory_check != null)
                {
                     result.put("missing_param",(String) aftrValidateParams.get("missing_param"));
                     errmsg = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_MSG;
                     errcode = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_CODE;
                }
                result.put("uri",uri);
                result.put("code",errcode);
                result.put("message",errmsg);
                
                logger.log(Level.SEVERE,"Error Result StringBuf :::::: " +result);
                return result;
            }
        }
        catch (Exception e)
        {
            Integer errcode = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_CODE;
            String errmsg = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_MSG;
            
            logger.log(Level.SEVERE,"Error while processing contact details related action :::::"+e.getMessage());
            result.put("code",errcode);
            result.put("message",errmsg);
            e.printStackTrace();
            return result; 
        }
        return null;
    }
}
