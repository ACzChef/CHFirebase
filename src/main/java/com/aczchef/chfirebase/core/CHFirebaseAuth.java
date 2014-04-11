package com.aczchef.chfirebase.core;

import com.firebase.client.Firebase;

import com.laytonsmith.PureUtilities.Common.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

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
    
    public static void init() throws IOException {
        File file;
        URL url = LifeCycle.class.getResource("/" + LifeCycle.class.getName().replace(".", "/") + ".class");
        String s = url.toString();
	s = s.replaceFirst("jar:file:", "");
	s = StringUtils.replaceLast(s, Pattern.quote(LifeCycle.class.getName().replace(".", "/") + ".class"), "");
        file = new File(s + "../CHFirebase.ini");
        file = new File(file.getCanonicalPath());
	
	CHFirebaesConfig.init(file);
        LifeCycle.print("Config file Loaded - ACzChef");
	
	setAuthId(CHFirebaesConfig.AuthId());
	setRef(CHFirebaesConfig.FirebaseUrl());
	setSecretToken(CHFirebaesConfig.FirebaseToken());
    }
}
