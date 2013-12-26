package com.aczchef.chfirebase.core;

import com.firebase.client.Firebase;

import com.laytonsmith.PureUtilities.Common.FileUtil;
import com.laytonsmith.PureUtilities.Common.StringUtils;
import com.laytonsmith.PureUtilities.TermColors;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Cgallarno
 */
public class CHFirebaseAuth {
//    {
//        "firebase": "firebase.firebaseio.com",
//        "firebas_token": "SuperSecretToken",
//	  "auth_id": "1"
//    }
    private static Firebase ref;
    private static String secretToken;
    private static String authId;

    public static Firebase getRef() {
        return ref;
    }
    
    protected static String getSecretToken() {
	return secretToken;
    }

    public static String getAuthId() {
	return authId;
    }

    public static void setRef(String ref) {
        if (!ref.startsWith("https://")) {
            ref = "https://" + ref;
        }
        CHFirebaseAuth.ref = new Firebase(ref);
    }

    protected static void setSecretToken(String secretToken) {
	CHFirebaseAuth.secretToken = secretToken;
    }

    public static void setAuthId(String authId) {
	CHFirebaseAuth.authId = authId;
    }
    
    public static boolean readAuthConfig() throws IOException {
        File file;
        URL url = CHFirebase.class.getResource("/" + CHFirebase.class.getName().replace(".", "/") + ".class");
        String s = url.toString();
	s = s.replaceFirst("jar:file:", "");
	s = StringUtils.replaceLast(s, Pattern.quote(CHFirebase.class.getName().replace(".", "/") + ".class"), "");
        file = new File(s + "../CHFirebase.json");
        file = new File(file.getCanonicalPath());
	
        if (file.createNewFile()) {
            FileUtil.write(
            "{\n" +
            "     \"firebase\": \"https://firebase.firebaseio.com\",\n" +
            "     \"firebase_token\": \"superSecretToken\",\n" +
	    "     \"auth_id\": \"1\"\n" + 
            "}", file);
            System.out.println("[CommandHelper] CHFirebase: Created config file - ACzChef");
        }
	
	StringBuilder invalidConfig = new StringBuilder();
        invalidConfig.append(TermColors.RED).append("Compile Error").append(TermColors.WHITE).append(": Invalid CHfirebase Config: %err% \n\t:Delete ").append(TermColors.YELLOW).append(file.getCanonicalPath()).append(TermColors.WHITE).append(" for a default config.");
	
        String json = FileUtil.read(file);
        
        try {
	    JSONObject obj = new JSONObject(json);
            if (obj.has("firebase") && obj.has("firebase_token") && obj.has("auth_id")) {
                setRef(obj.getString("firebase"));
                setSecretToken(obj.getString("firebase_token"));
		setAuthId(obj.getString("auth_id"));
            } else {
		invalidConfig.insert(invalidConfig.indexOf("%err%"), "Cant't find your firebase URL, firebase token, or auth id.");
		invalidConfig.replace(invalidConfig.indexOf("%err%"), invalidConfig.indexOf("%err%") + 5, "");
                System.out.println(invalidConfig.toString() + TermColors.reset());
		return false;
            }
	} catch (JSONException e) {
	    invalidConfig.insert(invalidConfig.indexOf("%err%"), e.getMessage());
	    invalidConfig.replace(invalidConfig.indexOf("%err%"), invalidConfig.indexOf("%err%") + 5, "");
	    System.out.println(invalidConfig.toString() + TermColors.reset());
	    return false;
	}
        return true;
    }
    
}
