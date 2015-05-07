/*
 * To change this template, choose Tools | Templates
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

//WORKHTML IMPORT
import wh.bean.CommonBean;
import wh.constants.ApiConstants;
import wh.util.ApiParamsValidator;
import wh.util.ApiUtil;

//WORKHTML MODEL
import wh.model.AttachmentDetails;
import wh.model.ClientContactDetails;
import wh.model.PsdToHtmlFreeOptions;
import wh.model.PsdToHtmlPaidOptions;
import wh.model.WebDesignOrdernow;
import wh.model.WordpressOrdernow;
/**
 *
 * @author mathan-1403
 */
public class WordpressDevelopementAction extends org.apache.struts.action.Action {

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

            if(mandatory_check!=null && mandatory_check.equals("success"))
            {
                if(uri!=null)
                {   
                    //PSD TO HTML FREE OPTION TABLE
                    PsdToHtmlFreeOptions PsdToHtmlFreeOptionModel = new PsdToHtmlFreeOptions();
                    
                    //PSD TO HTML PAID OPTION TABLE
                    PsdToHtmlPaidOptions PsdToHtmlPaidOptionModel = new PsdToHtmlPaidOptions();
                    
                    //PSD TO HTML CONTACT DETAILS TABLE
                    ClientContactDetails contactDetailsModel = new ClientContactDetails();
                    
                    //PSD TO HTML ATTACHMENT DETAILS TABLE
                    AttachmentDetails attachmentDetailsModel = new AttachmentDetails();
                    
                    //WORDPRESS MODEL
                    WordpressOrdernow wordpressOrderModel = new WordpressOrdernow();
                    
                    if(uri.contains(ApiConstants.API_ADD_WORDPRESS_ORDERNOW_DATA))
                    {
                        // uri : v1/webdesign/order/add
                        Iterator itr = aftrValidateParams.keySet().iterator();
                        while(itr.hasNext())
                        {
                            String key= itr.next().toString(); 
                            if(key!=null && !key.equals("null"))
                            {
                                if(key.equalsIgnoreCase("layout"))
                                {
                                    PsdToHtmlFreeOptionModel.setLayout((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("font"))
                                {
                                    PsdToHtmlFreeOptionModel.setFont((String)aftrValidateParams.get(key));
                                }
                                 else if(key.equalsIgnoreCase("nonwebfont"))
                                {
                                    PsdToHtmlFreeOptionModel.setNonWebFont((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("issemanticdata"))
                                {
                                    PsdToHtmlFreeOptionModel.setAdvancedSemanticData((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("wcag2"))
                                {
                                    PsdToHtmlFreeOptionModel.setWcag2((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("dynamicdropdownmenu"))
                                {
                                    PsdToHtmlFreeOptionModel.setDynamicDropDownMenu((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("dreamweavercompatability"))
                                {
                                    PsdToHtmlFreeOptionModel.setDreamweaverCompatability((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("browercompatability"))
                                {
                                    PsdToHtmlFreeOptionModel.setBrowserCompatability((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("corouselimage"))
                                {
                                    PsdToHtmlFreeOptionModel.setCorouselImage((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("jsoption"))
                                {
                                    PsdToHtmlFreeOptionModel.setJsLibrary((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("istooltipneeded"))
                                {
                                    PsdToHtmlFreeOptionModel.setTooltip((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("cssversion"))
                                {
                                    PsdToHtmlFreeOptionModel.setCssVersion((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("commentedcss"))
                                {
                                    PsdToHtmlFreeOptionModel.setCommentedCss((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("codingtype"))
                                {
                                    PsdToHtmlFreeOptionModel.setCodingType((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("commentedhtml"))
                                {
                                    PsdToHtmlFreeOptionModel.setCommentedHtml((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("optimizedforloadspeed"))
                                {
                                    PsdToHtmlFreeOptionModel.setOptimizedForLoadSpeed((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("seosemanticcoding"))
                                {
                                    PsdToHtmlFreeOptionModel.setSeoSemanticCoding((Boolean)aftrValidateParams.get(key));
                                }
                                
                                //PAID OPTIONS
                                else if(key.equalsIgnoreCase("isResponsive"))
                                {
                                    PsdToHtmlPaidOptionModel.setIsResponsiveLayout((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("ismobilecompatability"))
                                {
                                    PsdToHtmlPaidOptionModel.setMobileCompatability((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("isprintedversioncss"))
                                {
                                    PsdToHtmlPaidOptionModel.setIsPrintedCss((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("iswcac598"))
                                {
                                    PsdToHtmlPaidOptionModel.setIsWcag508((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("cssoption"))
                                {
                                    PsdToHtmlPaidOptionModel.setCssOption((String)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("customfontelements"))
                                {
                                    PsdToHtmlPaidOptionModel.setCustomFontElements((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("tabs"))
                                {
                                    PsdToHtmlPaidOptionModel.setTabs((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("formvalidation"))
                                {
                                    PsdToHtmlPaidOptionModel.setFormValidation((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("isanimatedfilter"))
                                {
                                    PsdToHtmlPaidOptionModel.setAnimatedFilters((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("isslider"))
                                {
                                    PsdToHtmlPaidOptionModel.setSlider((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("customscroll"))
                                {
                                    PsdToHtmlPaidOptionModel.setCustomScroll((Boolean)aftrValidateParams.get(key));
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
                                //WORDPRESS TABLE DETAILS
                                else if(key.equalsIgnoreCase("istheme"))
                                {
                                    wordpressOrderModel.setIsTheme((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("isplugin"))
                                {
                                    wordpressOrderModel.setIsPlugin((Boolean)aftrValidateParams.get(key));
                                }
                                else if(key.equalsIgnoreCase("numberofhomepage"))
                                {
                                    wordpressOrderModel.setNumberOfHomePage(Integer.valueOf(aftrValidateParams.get(key).toString()));
                                }
                                else if(key.equalsIgnoreCase("numberofsubpage"))
                                {
                                    wordpressOrderModel.setNumberOfHomePage(Integer.valueOf(aftrValidateParams.get(key).toString()));
                                }
                                else if(key.equalsIgnoreCase("isquoted"))
                                {
                                    wordpressOrderModel.setIsQuoted((Boolean)aftrValidateParams.get(key));
                                }
                            }
                        }
                        try
                        {
                            //ADD DATA TO FREE OPTION TABLE
                            Long freeOptionId = cb.addData(PsdToHtmlFreeOptionModel);
                            Long paidOptionId = cb.addData(PsdToHtmlPaidOptionModel);
                            Long contactDetailsId = cb.addData(contactDetailsModel);
                            
                            logg.log(Level.INFO, "*****************DATA ADDING PROCESS FOR WORDPRESS ORDER NOW PAGE*****************");
                            
                            logg.log(Level.INFO, "DATA ADDED:@PsdToHtmlFreeOptions :::: PRIMARY KEY OF ROW IS : "+freeOptionId);
                            logg.log(Level.INFO, "DATA ADDED:@PsdToHtmlPaidOptions :::: PRIMARY KEY OF ROW IS : "+paidOptionId);
                            logg.log(Level.INFO, "DATA ADDED:@ClientContactDetails :::: PRIMARY KEY OF ROW IS : "+contactDetailsId);
                            
                            //SET CHILD TABLE ID TO MAIN TABLE(@wordpressOrderModel)
                            wordpressOrderModel.setHtmlFreeAddonId(freeOptionId);
                            wordpressOrderModel.setHtmlPaidAddonId(paidOptionId);
                            wordpressOrderModel.setContactDetailsId(contactDetailsId);
                            
                            Long id = cb.addData(wordpressOrderModel);
                            result.put("id",id);
                            result.put("uri",uri);
                            result.put("code","200");
                            result.put("result","success"); 
                            result.put("message","Wordpress order placed sucessfully");
                            logg.log(Level.INFO, "User Wordpress order placed successfully and send mail to user::: result:"+result);
                             /*
                             * TODO : Send a confirmation mail to ordered user.
                             * Then,
                             * Send a order information mail to @Workhtml Team
                             */
                        }
                        catch (Exception e)
                        {
                            result.put("uri",uri);
                            result.put("code",ApiConstants.API_ADDING_ERROR_IN_WORDPRESS_ORDER);
                            result.put("result","failure"); 
                            result.put("message","Problem while adding wordpress order details");
                            logg.log(Level.SEVERE, "ERROR WHILE ADD DATA:@wordpressOrderModel table row \n\n RESULT :::"+result);
                            e.printStackTrace();
                        }
                        return result;
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
            logg.log(Level.SEVERE, "ERROR WHILE PROCESSING ODER DETAILS IN WORDPRESS ORDER");
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