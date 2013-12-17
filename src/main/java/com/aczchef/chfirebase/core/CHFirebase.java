/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aczchef.chfirebase.core;

import com.firebase.client.ChildEventListener;
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
    
    static Map<Integer , FirebasePair> Listeners = new HashMap<Integer, FirebasePair>();
    static Integer Counter = 0;
    
    @Override
    public void onStartup() {
        System.out.println("[CommandHelper] CHFirebase: Initialized - ACzChef");
    }

    @Override
    public void onShutdown() {
        System.out.println("[CommandHelper] CHFirebase: De-Initialized - ACzChef");
	clearListeners();
	System.out.println("[CommandHelper] CHFirebase: Value Event Listeners Cleard");
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
}
