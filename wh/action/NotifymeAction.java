/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wh.action;

import java.io.IOException;
import java.io.PrintWriter;
import static java.rmi.server.LogStream.log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//WORKHTML IMPORTS
import wh.bean.CommonBean;
import wh.constants.ApiConstants;
import wh.util.ApiParamsValidator;
import wh.util.ApiUtil;

import wh.model.Notifyme;
/**
 *
 * @author meganath-1399
 */
public class NotifymeAction extends org.apache.struts.action.Action {
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

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
            request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try{
            //SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();    
            HashMap result = process(request, response);
            String finalresult =(String) ApiUtil.handleFinalResponse(request,response,result);
            out.println(finalresult);
            return null;
        }catch (Exception ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public HashMap process(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
    {
        HashMap result = new HashMap();
        try
        {
            String uri = (String) request.getRequestURI();
            HashMap aftrValidateParams = ApiParamsValidator.requestParamsValidation(request,response,uri);
            String mandatory_check = (String) aftrValidateParams.get("mandatory_check");
            CommonBean cb = new CommonBean();

            if(mandatory_check!=null && mandatory_check.equals("success"))
            {
                if(uri!=null)
                {
                    Notifyme notifyme = new Notifyme();
                    if(uri.contains(ApiConstants.API_ADD_NOTIFYME_CONTACT))
                    {
                        // uri : /addcontact/notifyme
                        Iterator itr = aftrValidateParams.keySet().iterator();
                        while(itr.hasNext())
                        {
                            String key= itr.next().toString(); 
                            if(key!=null && !key.equals("null"))
                            {
                                if(key.equalsIgnoreCase("email"))
                                {
                                    notifyme.setEmail((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("browserdetails"))
                                {
                                    notifyme.setBrowserDetails((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("country"))
                                {
                                    notifyme.setCountry((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("time"))
                                {
                                    notifyme.setTime((Integer)aftrValidateParams.get(key));
                                } 
                            }
                        }
                        try
                        {
                            Long id = cb.addData(notifyme);
                            result.put("id",id);
                            result.put("uri",uri);
                            result.put("code","200");
                            result.put("result","success"); 
                            result.put("message","Notifyme details added sucessfully");
                        }
                        catch (Exception ex)
                        {
                            result.put("uri",uri);
                            result.put("code",ApiConstants.API_ADDING_ERROR_IN_NOTIFYME);
                            result.put("result","failure"); 
                            result.put("message","Problem while adding notifyme details");
                        }
                        /*else
                        {
                            //No data 
                            result.put("uri",uri);
                            result.put("code","604");
                            result.put("result","failure");
                            result.put("message","Empty data, So cannot add contact");
                        }*/
                        return result;
                    }
                    else if(uri.contains(ApiConstants.API_GET_NOTIFYME_CONTACT_DETAILS))
                    {
                        List details = cb.getDetails("Notifyme");
                        result.put("uri",uri);
                        result.put("code","200");
                        result.put("response",details);
                        result.put("result","success");
                    }
                }
            }
            else
            {
                Integer errcode = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_CODE;
                String errmsg = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_MSG;

                String missing_param = (String) aftrValidateParams.get("missing_param");
                result.put("uri",uri);
                result.put("missing_param",missing_param);
                result.put("code",errcode);
                result.put("message",errmsg);
                log("Error Result StringBuf :::::: " +result);
                return result;
            }
        }
        catch (Exception e)
        {
            Integer errcode = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_CODE;
            String errmsg = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_MSG;

            log(e.getMessage());
            result.put("code",errcode);
            result.put("message",errmsg);
            return result; 
        }
        return null;
    }
}