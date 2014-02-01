package com.aczchef.chfirebase.core;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.security.token.TokenGenerator;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;

import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Cgallarno
 */
@MSExtension("CHFirebase")
public class CHFirebase extends AbstractExtension {
    
    static Map<Integer , FirebasePair> Listeners = new HashMap<Integer, FirebasePair>();
    static Integer Counter = 0;
    
    public Version getVersion() {
	return new SimpleVersion(1, 2, 0);
    }
    
    @Override
    public void onStartup() {
        print("Initialized - " + getVersion() + " - ACzChef");
        
        try {
	    CHFirebaseAuth.init();
	    auth();
        } catch (IOException ex) {
            Logger.getLogger(CHFirebase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public void onShutdown() {
        print("De-Initialized - ACzChef");
	clearListeners();
	print("Value Event Listeners Cleard");
    }
    
    public static void print(String message) {
	System.out.println("[CommandHelper] CHFirebase: " + message);
    }
    
    public static Integer addListener(Firebase ref, ValueEventListener vel) {
	Counter++;
	Listeners.put(Counter, new FirebaseValuePair(ref, vel));
	return Counter;
    }
    
    public static Integer addListener(Firebase ref, ChildEventListener cel) {
	Counter++;
	Listeners.put(Counter, new FirebaseChildPair(ref, cel));
	return Counter;
    }
    
    public static void removeListener(Integer id) {
	FirebasePair pair = getListener(id);
	Firebase ref = pair.getFirebase();
	if (pair instanceof FirebaseValuePair) {
	    ValueEventListener vel = ((FirebaseValuePair) pair).getListener();
	    ref.removeEventListener(vel);
	} else {
	    ChildEventListener cel = ((FirebaseChildPair) pair).getListener();
	    ref.removeEventListener(cel);
	}
	
	Listeners.remove(id);
    }
    
    public static boolean listenerExists(Integer id) {
	return Listeners.containsKey(id);
    }
    
    public static FirebasePair getListener(Integer id) {
	FirebasePair pair;
	if (listenerExists(id)) {
	    pair = Listeners.get(id);
	    
	} else {
	    throw new RuntimeException("Specified listener id not found.");
	}
	return pair;
    }
    
    public static void clearListeners() {
	for (Map.Entry<Integer, FirebasePair> entry : Listeners.entrySet()) {
	    removeListener(entry.getKey());
	}
	Counter = 0;
    }
    
    public static void auth() {
        final Firebase ref;
        ref = CHFirebaseAuth.getRef();
	JSONObject arbitraryPayload = new JSONObject();
	try {
	    arbitraryPayload.put("CHFirebase", CHFirebaseAuth.getAuthId());
	} catch (JSONException e) {
	    e.printStackTrace();
	}   
	TokenGenerator tokenGenerator = new TokenGenerator(CHFirebaseAuth.getSecretToken());
	String token = tokenGenerator.createToken(arbitraryPayload);
	ref.auth(token, new Firebase.AuthListener() {

	    public void onAuthError(FirebaseError fe) {
		print("Firebase Auth Error: " + fe.getMessage());
	    }

	    public void onAuthSuccess(Object o) {
		Map data = (Map) o;
		print("Firebase Auth Succesful: " + ref.toString() + " - auth-id: " + ((Map) data.get("auth")).get("CHFirebase"));
	    }

	    public void onAuthRevoked(FirebaseError fe) {
		print("Auth Access has been Revoked: " + fe.getMessage());
	    }
	});
    }
}
