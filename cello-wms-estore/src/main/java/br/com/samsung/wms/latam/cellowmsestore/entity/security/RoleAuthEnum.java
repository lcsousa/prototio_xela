package br.com.samsung.wms.latam.cellowmsestore.entity.security;

public enum RoleAuthEnum {
	ROLE_USER_CREATE 
   ,ROLE_USER_FINDALL
   
   //,ROLE_AUTH_REFRESH_TOKEN
   ,ROLE_AUTH_IDENTITY_USER
   ,ROLE_AUTH_GET_ROLES
   ,ROLE_AUTH_HAS_ROLE
   ,ROLE_CHANGE_PASSWORD_ROLE
   ,ROLE_RESET_PASSWORD_ROLE
   
   ,ROLE_TEST_CREATE 
   ,ROLE_TEST_FINDALL
   ,ROLE_TEST_UPDATE 
   ,ROLE_TEST_FIND
   ,ROLE_TEST_DELETE;
	
	public static RoleAuthEnum valueOfLabel(String label) {
	    for (RoleAuthEnum e : values()) {
	        if (e.toString().equalsIgnoreCase(label)) {
	            return e;
	        }
	    }
	    return null;
	}
}
