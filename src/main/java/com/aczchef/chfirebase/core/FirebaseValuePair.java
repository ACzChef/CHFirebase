package com.aczchef.chfirebase.core;

import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 *
 * @author cgallarno
 */
public class FirebaseValuePair implements FirebasePair {
    private ValueEventListener vel;
    private Query query;

    public FirebaseValuePair(Query query, ValueEventListener vel) {
	this.query = query;
	this.vel = vel;
    }
    
    public Query getQuery() {
	return query;
    }
    
    public ValueEventListener getListener() {
	return vel;
    }
}
