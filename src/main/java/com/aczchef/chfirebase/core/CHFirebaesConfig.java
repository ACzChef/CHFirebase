package com.aczchef.chfirebase.core;

import com.laytonsmith.PureUtilities.Preferences;
import com.laytonsmith.core.Static;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Cgallarno
 */
public class CHFirebaesConfig {
    
    private static Preferences prefs;
    
    public static void init(File f) throws IOException {
	ArrayList<Preferences.Preference> def = new ArrayList<Preferences.Preference>();
	def.add(new Preferences.Preference("firebase-url", "https://firebase.firebaseio.com", Preferences.Type.STRING, "The url that represents the location of your firebase data."));
	def.add(new Preferences.Preference("firebase-token", "SuperSecretToken", Preferences.Type.STRING, "The secret token retrieved from firebase used to auth the server."));
	def.add(new Preferences.Preference("auth-id", "CHFirebase-Minecraft-Server", Preferences.Type.STRING, "The name that is passed into the auth data under CHFirebase-server to uniquely identify this server."));
	prefs = new Preferences("CHfirebase", Static.getLogger(), def);
	prefs.init(f);
    }
    
    public static boolean isInitialized(){
	return prefs != null;
    }
    
    public static String FirebaseUrl() {
	return (String)prefs.getPreference("firebase-url");
    }
    
    public static String FirebaseToken() {
	return (String)prefs.getPreference("firebase-token");
    }
    
    public static String AuthId() {
	return (String)prefs.getPreference("auth-id");
    }
    
    
}
