package ca.iam.util;

public class CapFirst {

	public static String str(String str) {
	    StringBuilder ret = new StringBuilder();
		if (str != null && !str.equals("")){
		    String[] words = str.split(" ");
		    for(int i = 0; i < words.length; i++) {
		        ret.append(Character.toUpperCase(words[i].charAt(0)));
		        ret.append(words[i].substring(1));
		        if(i < words.length - 1) {
		            ret.append(' ');
		        }
		    }
		}
	    return ret.toString();
	}
}
