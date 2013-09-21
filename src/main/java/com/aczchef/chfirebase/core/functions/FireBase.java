package com.aczchef.chfirebase.core.functions;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.environments.GlobalEnv;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *
 * @author Cgallarno
 */
public class FireBase {
    
    @api
    public static class base_set_value extends AbstractFunction {
	
	private static int threadCount = 0;
	    
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(3, new ThreadFactory() {

		public Thread newThread(Runnable r) {
		    return new Thread(r, Implementation.GetServerType().getBranding() + "-firebase-write-" + (threadCount++));
		}
	   });

        public Exceptions.ExceptionType[] thrown() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            
	    Firebase link = new Firebase(args[0].val());
            final Firebase ref;
            final String value;
	    
            
            if (args.length == 3) {
                ref = link.child(args[1].val());
                
                value = args[2].val();
            } else {
                value = args[1].val();
		ref = link;
            }
            
	    threadPool.submit(new Runnable() {

		public void run() {
		    ref.setValue(value, new Firebase.CompletionListener() {

			public void onComplete(FirebaseError fe) {
			    throw new UnsupportedOperationException("Not supported yet.");
			}
		    });
		}
	    });
            
            
            environment.getEnv(GlobalEnv.class).GetDaemonManager().activateThread(null);
	    
            return new CVoid(t);
	    
	    
        }

        public String getName() {
            return "base_set_value";
        }

        public Integer[] numArgs() {
            return new Integer[] {2, 3, 4};
        }

        public String docs() {
            return "void {firebase reference, data | firebase referencem }"
        }

        public Version since() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    }
}
