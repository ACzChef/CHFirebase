/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aczchef.chfirebase.core;

import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

/**
 *
 * @author Cgallarno
 */
public class Main {
    
    @MSExtension("CHFirebase")
    public class CHFirebase extends AbstractExtension {

	@Override
	public void onStartup() {
	    System.out.println("[CommandHelper] CHFirebase Initialized - ACzChef");
	}
	
    }
}
