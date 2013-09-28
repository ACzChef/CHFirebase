package com.aczchef.chfirebase.core.functions;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHLog;
import com.laytonsmith.core.constructs.CClosure;
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

        public Construct exec(final Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            
	    Firebase link = new Firebase(args[0].val());
            final Firebase ref;
            final String value;
            String childern = "";
            final CClosure callback;
	    
            
            switch(args.length) {
                case 4:
                    childern = args[1].val();
                    value = args[2].val();
                    callback = (CClosure) args[3];
                    break;
                case 3:
                    if(args[2] instanceof CClosure) {
                        callback = (CClosure) args[2];
                        value = args[1].val();
                    } else {
                        callback = null;
                        childern = args[1].val();
                        value = args[2].val();
                    }
                    break;
                case 2:
                    callback = null;
                    value = args[1].val();
                    break;
                //should never run
                default: value = "";
                    callback = null;
            }
            
            ref = link.child(childern);
            
            environment.getEnv(GlobalEnv.class).GetDaemonManager().activateThread(null);
	    threadPool.submit(new Runnable() {
		public void run() {
		    ref.setValue(value, new Firebase.CompletionListener() {

			public void onComplete(FirebaseError fe) {
                            if (fe != null) {
                                CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, fe.getMessage(), t);
                            }
			}
		    });
		}
	    });
            
            
            
	    
            return new CVoid(t);
	    
	    
        }

        public String getName() {
            return "base_set_value";
        }

        public Integer[] numArgs() {
            return new Integer[] {2, 3, 4};
        }

        public String docs() {
            return "void {firebase reference, data | firebase referencem }";
        }

        public Version since() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    }
}
