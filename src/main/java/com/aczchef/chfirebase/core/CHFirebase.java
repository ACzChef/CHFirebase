/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aczchef.chfirebase.core;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cgallarno
 */
@MSExtension("CHFirebase")
public class CHFirebase extends AbstractExtension {
    
    static Map<Integer , FirebaseValuePair> listeners = new HashMap<Integer, FirebaseValuePair>();
    static Integer counter = 0;
    
    @Override
    public void onStartup() {
        System.out.println("[CommandHelper] CHFirebase: Initialized - ACzChef");
    }

    @Override
    public void onShutdown() {
        System.out.println("[CommandHelper] CHFirebase: De-Initialized - ACzChef");
	removeAllListeners();
	System.out.println("[CommandHelper] CHFirebase: Value Event Listeners Cleard");
    }
    
    public static Integer addListener(Firebase ref, ValueEventListener vel) {
	counter++;
	listeners.put(counter, new FirebaseValuePair(ref, vel));
	return counter;
    }
    
    public static void removeListener(Integer id) {
	Firebase ref;
	ValueEventListener vel;
	FirebaseValuePair pair = listeners.get(id);
	ref = pair.getFirebase();
	vel = pair.getListener();
	ref.removeEventListener(vel);
	listeners.remove(id);
    }
    
    public static void removeAllListeners() {
	for (Map.Entry<Integer, FirebaseValuePair> entry : listeners.entrySet()) {
	    FirebaseValuePair firebaseValuePair = entry.getValue();
	    firebaseValuePair.getFirebase().removeEventListener(firebaseValuePair.getListener());
	}
	listeners.clear();
	counter = 0;
    }
}
