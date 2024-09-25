/**
 * 
 */
package com.nse.db.config;

/**
 * @author sanjeevkumar
 * 12-Sep-2024
 * 11:50:26 am 
 * Objective: 
 */
public class JpaConstants {
	
	//Persistence unit 
    public static final String JPA_UNIT ="ApiService";
   
	
	//Define all the entities package names here. This will be an entityManager specific
    public static final String[] JPA_PKG_ENTITIES_ARRAY=new String[] {"com.nse.entities"};    
    
  //Define all the repositories package names here. This will be an entityManager specific   
    public static final String PKG_REPO = "com.nse.repo";
  
    
    
}//End of ApiJpaConstants
