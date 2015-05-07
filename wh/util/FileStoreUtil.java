/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wh.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 *
 * @author mathan-1403
 */
public class FileStoreUtil {
    
     Logger logg = Logger.getLogger(this.getClass().getName());
    
    private static FileStoreUtil util=null;
    
    
    public static FileStoreUtil getInstance(){
        if(util==null){
            util=new FileStoreUtil();
        }
        return util;
    }
    /*
     * To read file from local disc space used by filepath and service name
     */
    public InputStream readFile(String filePointer,String serviceName) throws Exception
    {
        
        return null;
    }
    
    /*
     * To write file to local disc space with unique filepath and service name
     */
     public String writeFile( String filePointer,InputStream is,String serviceName) throws Exception
    {
        
        return null;
    }
    
     /*
     * To overwrite file to local disc space in existing location with unique filepath and service name
     */
    public String overWriteFile(String filePointer,InputStream is,String serviceName) throws Exception
    {
        
        return null;
    }
    /*
     * To copy existing file with given location and make a cpoy by given a newfile name.
     */
    public String copyFile(String filePointer,String serviceName,String fileName) throws Exception
    {
        return null;
    }
    
    /*
     * To delete exist file in disc location.
     */
    public String deleteFile(String filePointer,String serviceName)
    {
        return null;
    }
    
    
}
