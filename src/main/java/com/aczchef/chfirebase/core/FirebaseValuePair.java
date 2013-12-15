package com.aczchef.chfirebase.core;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

/**
 *
 * @author cgallarno
 */
public class FirebaseValuePair {
    private ValueEventListener vel;
    private Firebase ref;

    public FirebaseValuePair(Firebase ref, ValueEventListener vel) {
	this.ref = ref;
	this.vel = vel;
    }
    
    public Firebase getFirebase() {
	return ref;
    }
    
    public ValueEventListener getListener() {
	return vel;
    }
}
