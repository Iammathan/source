/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wh.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

//TABLE MODELS
import wh.model.PsdToEmailOrdernow;
import wh.model.PsdToEmailFreeOption;
import wh.model.PsdToEmailPaidOption;
import wh.model.PsdToEmailIntegrationOptions;
import wh.model.AttachmentDetails;
import wh.model.ClientContactDetails;

/**
 *
 * @author meganath
 */
public class PsdToEmailAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    public Logger logg = Logger.getLogger(this.getClass().getName());

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
    @Override
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
            
            if(mandatory_check !=null && mandatory_check.toString().equals("success"))
            {
                if(uri!=null)
                {
                    //PSD TO EMAIL BASE TABLE
                    PsdToEmailOrdernow psdtoemailModel = new PsdToEmailOrdernow();
                    
                    //PSD TO EMAIL FREE OPTION TABLE
                    PsdToEmailFreeOption PsdToEmailFreeOptionModel = new PsdToEmailFreeOption();
                    
                    //PSD TO EMAIL PAID OPTION TABLE
                    PsdToEmailPaidOption PsdToEmailPaidOptionModel = new PsdToEmailPaidOption();
                    
                    //PSD TO EMAIL PAID OPTION TABLE
                    PsdToEmailIntegrationOptions PsdToEmailIntegrationOption = new PsdToEmailIntegrationOptions();
                    
                    //PSD TO EMAIL CONTACT DETAILS TABLE
                    ClientContactDetails contactDetailsModel = new ClientContactDetails();
                    //SET AS NULL FOR EMAIL ORDER NOW 
                    contactDetailsModel.setAttachmentId(null);
                    
                    //PSD TO EMAIL ATTACHMENT DETAILS TABLE
                    AttachmentDetails attachmentDetailsModel = new AttachmentDetails();
                    
                    if(uri.contains(ApiConstants.API_ADD_PSDTOEMAIL_ORDERNOW_DATA))
                    {
                        // uri : psdtoemail/addorder
                        Iterator itr = aftrValidateParams.keySet().iterator();
                        while(itr.hasNext())
                        {
                            String key= itr.next().toString(); 
                            if(key!=null && !key.equals("null"))
                            {
                                //FREE OPTION DATA ADD
                                if(key.equalsIgnoreCase("supportedplatforms"))
                                {
                                    PsdToEmailFreeOptionModel.setSupportedPlatforms((String)aftrValidateParams.get(key));
                                }
                                //PAID OPTION DATA ADD
                                else if(key.equalsIgnoreCase("isresponsivetemplate"))
                                {
                                    PsdToEmailPaidOptionModel.setIsResponsive((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("ismobilecompatability"))
                                {
                                    //DATA FORMAT : Android, Iphone, Windows
                                    PsdToEmailPaidOptionModel.setMobileCompatability((String)aftrValidateParams.get(key));
                                } 
                                //EMAIL INTEAGRATION TABLE DATA
                                else if(key.equalsIgnoreCase("iscampaignmonitor"))
                                {
                                    PsdToEmailIntegrationOption.setCampaignMonitor((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("ismailchimp"))
                                {
                                    PsdToEmailIntegrationOption.setMailChimp((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("iszohocampaign"))
                                {
                                    PsdToEmailIntegrationOption.setZohoCampaigns((Boolean)aftrValidateParams.get(key));
                                }
                                //CLIENT CONTACT DETAILS
                                else if(key.equalsIgnoreCase("name"))
                                {
                                    contactDetailsModel.setName((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("emailid"))
                                {
                                    contactDetailsModel.setEmail((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("phonenumber"))
                                {
                                    contactDetailsModel.setPhoneNumber((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("projectbrief"))
                                {
                                    contactDetailsModel.setProjectBreif((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("addedlinks"))
                                {
                                    contactDetailsModel.setAddedLinks((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("ishaveattachment"))
                                {
                                    //contactDetailsModel.setIsAttachments((Boolean)aftrValidateParams.get(key));
                                    contactDetailsModel.setIsAttachments(true);
                                    
                                    //IF ATTACHMENT IS IN REQUEST, WILL ADD FILES DETAILS IN 'AttachmentDetails' table and add id to 'ClientContactDetails' table
                                    attachmentDetailsModel.setAttachmentLocation("Attachment Location");
                                    attachmentDetailsModel.setAttachmentName("Attachment name");
                                    attachmentDetailsModel.setFileSize(Long.valueOf("1234567890"));
                                    attachmentDetailsModel.setUploadedTime(System.currentTimeMillis());
                                    attachmentDetailsModel.setFileType("File Type");
                                    
                                    //ADD DATA TO ATTACHMENT TABLE
                                    Long attachmentId = cb.addData(attachmentDetailsModel);
                                    
                                    logg.log(Level.INFO, "DATA ADDED:@AttachmentDetails :::: PRIMARY KEY OF ROW IS : "+attachmentId);
                                    
                                    //SET ATTACHMENT ID
                                    contactDetailsModel.setAttachmentId(attachmentId);
                                    
                                }  
                                //EMAILORDERNOW TABLE ENTRIES
                                else if(key.equalsIgnoreCase("numberoftemplate"))
                                {
                                    psdtoemailModel.setNumberOfPage(Integer.valueOf(aftrValidateParams.get(key).toString()));
                                }
                                contactDetailsModel.setIsAttachments((Boolean) false);
                                
                            }
                        }
                        try
                        {
                            //set default value as null
                            PsdToEmailPaidOptionModel.setEmailIntegrationId(null);
                            if(request.getAttribute("IS_HAVE_INTEGRATION_OPTION") != null && request.getAttribute("IS_HAVE_INTEGRATION_OPTION").equals("true"))
                            {
                                //ADD DATA TO @PsdToEmailIntegrationOptions table
                                Long emailIntegrationId = cb.addData(PsdToEmailIntegrationOption);
                                PsdToEmailPaidOptionModel.setEmailIntegrationId(emailIntegrationId);
                            }
                            
                            
                            //ADD DATA TO @PsdToEmailPaidOption table
                            Long freeOptionId = cb.addData(PsdToEmailFreeOptionModel);
                            Long paidOptionId = cb.addData(PsdToEmailPaidOptionModel);
                            Long contactDetailsId = cb.addData(contactDetailsModel);
                            
                            logg.log(Level.INFO, "DATA ADDED:@PsdToEmailFreeOptions :::: PRIMARY KEY OF ROW IS : "+freeOptionId);
                            logg.log(Level.INFO, "DATA ADDED:@PsdToEmailPaidOptions :::: PRIMARY KEY OF ROW IS : "+paidOptionId);
                            logg.log(Level.INFO, "DATA ADDED:@ClientContactDetails :::: PRIMARY KEY OF ROW IS : "+contactDetailsId);
                            
                            //SET CHILD TABLE ID TO MAIN TABLE(@PsdToEmailOrdernow)
                            psdtoemailModel.setMailFreeOptionId(freeOptionId);
                            psdtoemailModel.setMailPaidOptionId(paidOptionId);
                            psdtoemailModel.setContactDetailsId(contactDetailsId);

                            Long emailOderId = cb.addData(psdtoemailModel);
                            
                            result.put("id",emailOderId);
                            result.put("uri",uri);
                            result.put("code","200");
                            result.put("result","success"); 
                            result.put("message","Your order placed successfully");
                            logg.log(Level.INFO, "User Psd to Email order placed successfully and send mail to user::: result:"+result);
                            
                            /*
                             * TODO : Send a confirmation mail to ordered user.
                             * Then,
                             * Send a order information mail to @Workhtml Team
                             */
                        }
                        catch (Exception e)
                        {
                            result.put("uri",uri);
                            result.put("code",ApiConstants.API_ADDING_ERROR_IN_PSDTOEMAILORDER);
                            result.put("result","failure"); 
                            logg.log(Level.SEVERE, "ERROR WHILE ADD DATA:@PsdToEmailOrdernow table row \n\n RESULT :::"+result);
                            e.printStackTrace();
                        }
                        return result;
                    }
                    else if(uri.contains(ApiConstants.API_GET_PSDTOEMAIL_ORDER_DETAILS))
                    {
                        //uri : client/get
                        List details = cb.getDetails("PsdToEmailOrdernow");
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
                //log("Error Result StringBuf :::::: " +result);
                return result;
            }
        }
        catch (Exception e)
        {
            logg.log(Level.SEVERE, "ERROR WHILE PROCESSING ODER DETAILS IN PSD TO EMAIL");
            e.printStackTrace();
            Integer errcode = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_CODE;
            String errmsg = ApiConstants.API_MANDATORY_FIELDS_ARE_NOT_FOUND_MSG;

            //log(e.getMessage());
            result.put("code",errcode);
            result.put("message",errmsg);
            return result; 
            
        }
        return null;
    }
}
