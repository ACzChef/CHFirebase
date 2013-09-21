package com.aczchef.chfirebase.core.functions;

import com.firebase.client.Firebase;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;

/**
 *
 * @author Cgallarno
 */
public class FireBase {
    
    @api
    public static class base_set_value extends AbstractFunction {

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
            
            Firebase link = new Firebase("https://cgallarno.firebaseio.com/");
            Firebase ref = link;
            String value;
            
            if (args.length == 3) {
                ref = ref.child(args[1].val());
                
                value = args[2].val();
            } else {
                value = args[1].val();
            }
            
            System.out.println(ref);
            System.out.println(value);
            
            ref.setValue(value);
            
            
            return new CVoid(t);
        }

        public String getName() {
            return "base_set_value";
        }

        public Integer[] numArgs() {
            return new Integer[] {2, 3};
        }

        public String docs() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public Version since() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    }
}
