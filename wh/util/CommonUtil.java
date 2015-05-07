/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wh.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathan-1403
 */
public class CommonUtil {
    
    Logger logg = Logger.getLogger(this.getClass().getName());
    
    //PUBLIC VARIABLES
    public Properties WHProp=new Properties();
    
    private static CommonUtil util=null;
    

    private CommonUtil(){
        
        loadProps("workhtml.properties",WHProp);
        
    }
    public static CommonUtil getInstance(){
        if(util==null){
            util=new CommonUtil();
        }
        return util;
    }

    public void loadProps(String fileName,Properties propToLoad)
    {
        logg.log(Level.INFO,"loadProps method call :: fileName:"+fileName + "****propToLoad ::"+propToLoad);
        FileInputStream fis = null;
        String fileSep = File.separator;
        String catalinaHome = System.getProperty("catalina.home");
        String propFile =  "properties"+fileSep+fileName; 
        logg.log(Level.INFO,"Isfile exist::::"+new File(propFile).exists());
        if( (new File(propFile)).exists() )
        {
            try
            {
                fis = new FileInputStream(propFile);
                propToLoad.load(fis);
            }
            catch(Exception iex)
            {
                logg.log(Level.SEVERE,"Exception while reading the properties form "+fileName + iex.getMessage());
            }
            finally
            {
                try
                {
                    if(fis != null )
                    {
                        fis.close();
                    }
                }
                catch(Exception ioe)
                {
                    logg.log(Level.SEVERE,"Exception while reading the properties form "+fileName+ ioe.getMessage());
                }
            }
        }

    }
    
    public String getWHPropertiesVal(String key)
    {
         return (String)WHProp.get(key);
    }

    
}
