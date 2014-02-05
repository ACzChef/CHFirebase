package com.aczchef.chfirebase.core;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Query;

/**
 *
 * @author cgallarno
 */
public class FirebaseChildPair implements FirebasePair {
    private ChildEventListener cel;
    private Query query;

    public FirebaseChildPair(Query query, ChildEventListener cel) {
	this.query = query;
	this.cel = cel;
    }
    
    public Query getQuery() {
	return query;
    }
    
    public ChildEventListener getListener() {
	return cel;
    }
}
