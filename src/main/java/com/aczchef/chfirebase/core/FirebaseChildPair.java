package com.aczchef.chfirebase.core;

import com.firebase.client.Firebase;
import com.firebase.client.ChildEventListener;

/**
 *
 * @author cgallarno
 */
public class FirebaseChildPair implements FirebasePair {
    private ChildEventListener cel;
    private Firebase ref;

    public FirebaseChildPair(Firebase ref, ChildEventListener cel) {
	this.ref = ref;
	this.cel = cel;
    }
    
    public Firebase getFirebase() {
	return ref;
    }
    
    public ChildEventListener getListener() {
	return cel;
    }
}
