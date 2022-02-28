package br.com.samsung.wms.latam.cellowmsestore.entity.security;

public enum TokenTypeEnum {
	ACCESS_TOKEN 
   ,REFRESH_TOKEN;
	
	public static TokenTypeEnum valueOfLabel(String label) {
	    for (TokenTypeEnum e : values()) {
	        if (e.toString().equalsIgnoreCase(label)) {
	            return e;
	        }
	    }
	    return null;
	}
}
