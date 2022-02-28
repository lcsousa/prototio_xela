package br.com.samsung.wms.latam.cellowmsestore.entity.security;

public enum RoleAuthEnum {
	ROLE_USER_CREATE 
   ,ROLE_USER_FINDALL
   ,ROLE_AUTH_REFRESH_TOKEN
   ,ROLE_AUTH_IDENTITY_USER
   ,ROLE_AUTH_GET_ROLES
   ,ROLE_AUTH_HAS_ROLE;
	
	public static RoleAuthEnum valueOfLabel(String label) {
	    for (RoleAuthEnum e : values()) {
	        if (e.toString().equalsIgnoreCase(label)) {
	            return e;
	        }
	    }
	    return null;
	}
}
