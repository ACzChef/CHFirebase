package com.aczchef.chfirebase.core.functions;

import com.aczchef.chfirebase.core.CHFirebaesConfig;
import com.aczchef.chfirebase.core.CHFirebase;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.laytonsmith.PureUtilities.Common.StackTraceUtils;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHLog;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.LogLevel;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CClosure;
import com.laytonsmith.core.constructs.CDouble;
import com.laytonsmith.core.constructs.CInt;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.exceptions.FunctionReturnException;
import com.laytonsmith.core.exceptions.ProgramFlowManipulationException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import com.laytonsmith.core.functions.Exceptions.ExceptionType;

/**
 *
 * @author Cgallarno
 */
public class DataManipulation {
    
    @api
    public static class firebase_set extends AbstractFunction {
	
        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[] {ExceptionType.CastException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    Firebase link = new Firebase(args[0].val());
            Firebase ref;
            Object value = "";
	    Double priority = null;
            String childern = "";
	    CArray options = new CArray(t);
            CClosure callback = null;
	    
	    switch(args.length) {
                case 4:
                    value = Construct.GetPOJO(args[1]);
		    options = (CArray) args[2];
                    callback = (CClosure) args[3];
                    break;
		    
                case 3:
                    if(args[2] instanceof CClosure) {
                        callback = (CClosure) args[2];
                        value = Construct.GetPOJO(args[1]);
                    } else {
                        options = (CArray) args[2];
                        value = Construct.GetPOJO(args[1]);
                    }
                    break;
                case 2:
                    value = Construct.GetPOJO(args[1]);
                    break;
            }
	    
	    if (options.containsKey("childern")) {
		childern = options.get("childern").val();
	    }
            
            ref = link.child(childern);
	    
	    if (options.containsKey("priority")) {
		priority = ((CDouble) options.get("priority")).getDouble();
	    }
	    
	    if (callback == null) {
		if (priority == null) {
		    ref.setValue(value);
		} else {
		    ref.setValue(value, priority);
		}
		
	    } else {
		//Make the callback final to be usable in the listener
		final CClosure finalCallback = callback;
		Firebase.CompletionListener listener = new Firebase.CompletionListener() {

		    public void onComplete(FirebaseError fe, Firebase backRef) {
			Construct CError;
			String error;
			
                        if (fe != null) {
                            error = fe.getMessage();
			    CError = new CString(error, t);
                        } else {
			    CError = new CNull(t);
			}
			
			try{
			    finalCallback.execute(new Construct[]{CError});
			} catch(FunctionReturnException e){
			    //Just ignore this if it's returning void. Otherwise, warn.
			    //TODO: Eventually, this should be taggable as a compile error
			    if(!(e.getReturn() instanceof CVoid)){
			        CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Returning a value from the closure. The value is"
				    + " being ignored.", t);
			    }
			} catch(ProgramFlowManipulationException e){
			    //This is an error
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Only return may be used inside the closure.", t);
			} catch(ConfigRuntimeException e){
			    ConfigRuntimeException.React(e, environment);
			} catch(Throwable e){
			    //Other throwables we just need to report
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.ERROR, "An unexpected exception has occurred. No extra"
				+ " information is available, but please report this error:\n" + StackTraceUtils.GetStacktrace(e), t);
			}
		    }
		};
		if (priority == null) {
		    ref.setValue(value, priority, listener);
		} else {
		    ref.setValue(value, listener);
		}
	    }
            return new CVoid(t);
        }

        public String getName() {
            return "firebase_set";
        }

        public Integer[] numArgs() {
            return new Integer[] {2, 3, 4};
        }

        public String docs() {
            return "void {firebase reference, value, [options], [callback]} Sets a value to a firebase reference. Options is an array, Please see options to see what it should contain. Callback is a closure that will run when the set is finished, it is passed data that contains an error code, null if no error occurred.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
    
    }
    
    @api
    public static class firebase_push extends AbstractFunction {
	
        public Exceptions.ExceptionType[] thrown() {
            return new ExceptionType[] {ExceptionType.CastException};
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    Firebase link = new Firebase(args[0].val());
            Firebase ref;
            Object value = "";
	    Double priority = null;
            String childern = "";
	    CArray options = new CArray(t);
            CClosure callback = null;
	    
	    switch(args.length) {
                case 4:
                    value = Construct.GetPOJO(args[1]);
		    options = (CArray) args[2];
                    callback = (CClosure) args[3];
                    break;
		    
                case 3:
                    if(args[2] instanceof CClosure) {
                        callback = (CClosure) args[2];
                        value = Construct.GetPOJO(args[1]);
                    } else {
                        options = (CArray) args[2];
                        value = Construct.GetPOJO(args[1]);
                    }
                    break;
                case 2:
                    value = Construct.GetPOJO(args[1]);
                    break;
            }
	    
	    if (options.containsKey("childern")) {
		childern = options.get("childern").val();
	    }
            
            ref = link.child(childern).push();
	    
	    if (options.containsKey("priority")) {
		priority = ((CDouble) options.get("priority")).getDouble();
	    }
	    
	    if (callback == null) {
		if (priority == null) {
		    ref.setValue(value);
		} else {
		    ref.setValue(value, priority);
		}
		
	    } else {
		//Make the callback final to be usable in the listener
		final CClosure finalCallback = callback;
		Firebase.CompletionListener listener = new Firebase.CompletionListener() {

		    public void onComplete(FirebaseError fe, Firebase backRef) {
			Construct CError;
			String error;
			
                        if (fe != null) {
                            error = fe.getMessage();
			    CError = new CString(error, t);
                        } else {
			    CError = new CNull(t);
			}
			
			try{
			    finalCallback.execute(new Construct[]{CError});
			} catch(FunctionReturnException e){
			    //Just ignore this if it's returning void. Otherwise, warn.
			    //TODO: Eventually, this should be taggable as a compile error
			    if(!(e.getReturn() instanceof CVoid)){
			        CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Returning a value from the closure. The value is"
				    + " being ignored.", t);
			    }
			} catch(ProgramFlowManipulationException e){
			    //This is an error
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Only return may be used inside the closure.", t);
			} catch(ConfigRuntimeException e){
			    ConfigRuntimeException.React(e, environment);
			} catch(Throwable e){
			    //Other throwables we just need to report
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.ERROR, "An unexpected exception has occurred. No extra"
				+ " information is available, but please report this error:\n" + StackTraceUtils.GetStacktrace(e), t);
			}
		    }
		};
		if (priority == null) {
		    ref.setValue(value, priority, listener);
		} else {
		    ref.setValue(value, listener);
		}
	    }
            return new CString(ref.toString(), t);
        }

        public String getName() {
            return "firebase_push";
        }

        public Integer[] numArgs() {
            return new Integer[] {2, 3, 4};
        }

        public String docs() {
            return "string {firebase reference, value, [options], [callback]} Pushes a value to a firebase reference. Options is an array, Please see options to see what it should contain. Callback is a closure that will run when the push is finished, it is passed data that contains an error code, null if no error occurred. Returns the id of the pushed value.";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
    
    }
    
    @api
    public static class firebase_set_priority extends AbstractFunction {

	public ExceptionType[] thrown() {
	    return new ExceptionType[] {ExceptionType.CastException};
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
	    Firebase ref;
	    long priority;
	    
	    ref = new Firebase(args[0].val());
            if (args[1] instanceof CInt) {
                priority = ((CInt) args[1]).getInt();
            } else {
                throw new ConfigRuntimeException("Expected integer for priority but recieved: " + args[1].val(), ExceptionType.CastException, t);
            }
	    ref.setPriority(priority);
	    return new CVoid(t);
	}

	public String getName() {
	    return "firebase_set_priority";
	}

	public Integer[] numArgs() {
	    return new Integer[] {2};
	}

	public String docs() {
	    return "void {firebase refernce, priority} Sets the priority of a firebase refernce.";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
    
    @api
    public static class firebase_read_once extends AbstractFunction {

	public ExceptionType[] thrown() {
	    return new ExceptionType[] {ExceptionType.CastException};
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    
	    Firebase link = new Firebase(args[0].val());
            Firebase ref;
            String childern = "";
	    CArray options = new CArray(t);
            CClosure callback = null;
	    
	    switch(args.length) {
		case 3:
		    options = (CArray) args[1];
		    callback = (CClosure) args[2];
		    break;
		case 2:
		    callback = (CClosure) args[1];
		    break;
	    }
	    
	    if (options.containsKey("childern")) {
		childern = options.get("childern").val();
	    }
	    
	    ref = link.child(childern);
	    
	    
	    // Query
	    Query query = ref;
	    
	    if (options.containsKey("limit")) {
		System.out.println(options.get("limit"));
		query = query.limit((int) ((CInt) options.get("limit")).getInt());
	    }
	    if (options.containsKey("start")) {
		query = query.startAt(((CDouble) options.get("start")).getDouble());
	    }
	    if (options.containsKey("end")) {
		query = query.endAt(((CDouble) options.get("end")).getDouble());
	    }
	    
	    //Make the callback final to be usable in the listener
	    final CClosure finalCallback = callback;
	    query.addListenerForSingleValueEvent(new ValueEventListener() {

		public void onDataChange(DataSnapshot ds) {
		    Construct data = Construct.GetConstruct(ds.getValue());
			try{
			    finalCallback.execute(new Construct[]{data});
			} catch(FunctionReturnException e){
			    //Just ignore this if it's returning void. Otherwise, warn.
			    //TODO: Eventually, this should be taggable as a compile error
			    if(!(e.getReturn() instanceof CVoid)){
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Returning a value from the closure. The value is"
				+ " being ignored.", t);
			    }
			} catch(ProgramFlowManipulationException e){
			    //This is an error
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Only return may be used inside the closure.", t);
			} catch(ConfigRuntimeException e){
				ConfigRuntimeException.React(e, environment);
			} catch(Throwable e){
			    //Other throwables we just need to report
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.ERROR, "An unexpected exception has occurred. No extra"
				+ " information is available, but please report this error:\n" + StackTraceUtils.GetStacktrace(e), t);
			}
		}

		public void onCancelled(FirebaseError fe) {
		    
		}
	    });
	    
	    return new CVoid(t);
	    
	}

	public String getName() {
	    return "firebase_read_once";
	}

	public Integer[] numArgs() {
	    return new Integer[] {2, 3};
	}

	public String docs() {
	    return "void {firebase reference, [options], callback} Reads the data at a firebase reference. The data is only read once and does the continue to be read." + 
		    "callback is a closure that is run when the data is recieved, It is passed the data at the requested location. " + 
		    "Options is an array, Please see options for what it should contain.";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }

    @api
    public static class firebase_read extends AbstractFunction {

	public ExceptionType[] thrown() {
	    return new ExceptionType[] {ExceptionType.CastException};
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    
	    Firebase link = new Firebase(args[0].val());
            Firebase ref;
            String childern = "";
	    CArray options = new CArray(t);
            CClosure callback = null;
	    switch(args.length) {
		case 3:
		    options = (CArray) args[1];
		    callback = (CClosure) args[2];
		    break;
		case 2:
		    callback = (CClosure) args[1];
		    break;
	    }
	    
	    if (options.containsKey("childern")) {
		childern = options.get("childern").val();
	    }
	    
	    ref = link.child(childern);
	    
	    // Query
	    Query query = ref;
	    
	    if (options.containsKey("limit")) {
		System.out.println(options.get("limit"));
		query = query.limit((int) ((CInt) options.get("limit")).getInt());
	    }
	    if (options.containsKey("start")) {
		query = query.startAt(((CDouble) options.get("start")).getDouble());
	    }
	    if (options.containsKey("end")) {
		query = query.endAt(((CDouble) options.get("end")).getDouble());
	    }
	    
            //Make the callback final to be usable in the listener
	    final CClosure finalCallback = callback;
	    int id = CHFirebase.addListener(query, query.addValueEventListener(new ValueEventListener() {

		public void onDataChange(DataSnapshot ds) {
		    Construct data = Construct.GetConstruct(ds.getValue());
			try{
			    finalCallback.execute(new Construct[]{data});
			} catch(FunctionReturnException e){
			    //Just ignore this if it's returning void. Otherwise, warn.
			    //TODO: Eventually, this should be taggable as a compile error
			    if(!(e.getReturn() instanceof CVoid)){
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Returning a value from the closure. The value is"
				+ " being ignored.", t);
			    }
			} catch(ProgramFlowManipulationException e){
			    //This is an error
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Only return may be used inside the closure.", t);
			} catch(ConfigRuntimeException e){
				ConfigRuntimeException.React(e, environment);
			} catch(Throwable e){
			    //Other throwables we just need to report
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.ERROR, "An unexpected exception has occurred. No extra"
				+ " information is available, but please report this error:\n" + StackTraceUtils.GetStacktrace(e), t);
			}
		}

		public void onCancelled(FirebaseError fe) {
		    
		}
	    }));
	    return new CInt(id, t);
	    
	}

	public String getName() {
	    return "firebase_read";
	}

	public Integer[] numArgs() {
	    return new Integer[] {2, 3};
	}

	public String docs() {
	    return "int {firebase reference, [options], callback} Reads the data at a firebase reference. " + 
		    "callback is a closure that is run when the data is recieved or changes, It is passed the data at the requested location. " + 
		    "Options is an array, Please see options for what it should contain. " +
		    "Returns the listener id.";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
    
    @api
    public static class firebase_child_added extends AbstractFunction {

	public ExceptionType[] thrown() {
	    return new ExceptionType[] {ExceptionType.CastException};
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    
	    Firebase link = new Firebase(args[0].val());
            Firebase ref;
            String childern = "";
            CArray options = new CArray(t);
            CClosure callback = null;
	    switch(args.length) {
		case 3:
		    options = (CArray) args[1];
		    callback = (CClosure) args[2];
		    break;
		case 2:
		    callback = (CClosure) args[1];
		    break;
	    }
	    
            if (options.containsKey("childern")) {
                childern = options.get("childern").val();
            }
            
	    ref = link.child(childern);
	    
            // Query
	    Query query = ref;
	    
	    if (options.containsKey("limit")) {
		System.out.println(options.get("limit"));
		query = query.limit((int) ((CInt) options.get("limit")).getInt());
	    }
	    if (options.containsKey("start")) {
		query = query.startAt(((CInt) options.get("start")).getInt());
	    }
	    if (options.containsKey("end")) {
		query = query.endAt(((CDouble) options.get("end")).getDouble());
	    }
	    
            
            //Make the callback final to be usable in the listener
            final CClosure finalCallback = callback;
	    int id = CHFirebase.addListener(query, query.addChildEventListener(new ChildEventListener() {

                public void onChildAdded(DataSnapshot ds, String string) {
                    Construct data = Construct.GetConstruct(ds.getValue());
			try{
			    finalCallback.execute(new Construct[]{data});
			} catch(FunctionReturnException e){
			    //Just ignore this if it's returning void. Otherwise, warn.
			    //TODO: Eventually, this should be taggable as a compile error
			    if(!(e.getReturn() instanceof CVoid)){
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Returning a value from the closure. The value is"
				+ " being ignored.", t);
			    }
			} catch(ProgramFlowManipulationException e){
			    //This is an error
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.WARNING, "Only return may be used inside the closure.", t);
			} catch(ConfigRuntimeException e){
				ConfigRuntimeException.React(e, environment);
			} catch(Throwable e){
			    //Other throwables we just need to report
			    CHLog.GetLogger().Log(CHLog.Tags.RUNTIME, LogLevel.ERROR, "An unexpected exception has occurred. No extra"
				+ " information is available, but please report this error:\n" + StackTraceUtils.GetStacktrace(e), t);
			}
                }

                public void onChildChanged(DataSnapshot ds, String string) {
                    
                }

                public void onChildRemoved(DataSnapshot ds) {
                    
                }

                public void onChildMoved(DataSnapshot ds, String string) {
                    
                }

                public void onCancelled(FirebaseError fe) {
		    
                }
	    }));
	    return new CInt(id, t);
	    
	}

	public String getName() {
	    return "firebase_child_added";
	}

	public Integer[] numArgs() {
	    return new Integer[] {2, 3};
	}

	public String docs() {
	    return "int {firebase reference, [options], callback} Adds a listener for when childern are added to a firebase reference. Runs for every child alreay existing and every new child added." + 
		    "callback is a closure that is run when a child is added, It is passed the data of the added child. " + 
		    "Options is an array, Please see options for what it should contain. " +
		    "Returns the listener id.";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
    
    @api
    public static class firebase_remove_listener extends AbstractFunction {

	public ExceptionType[] thrown() {
	    return new ExceptionType[] {ExceptionType.CastException};
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
	    if (args[0] instanceof CInt) {
		    CHFirebase.removeListener(Integer.valueOf(args[0].val()));
	    } else {
		throw new Exceptions.CastException("Expected an Integer.", t);
	    }
	    
	    return new CVoid(t);
	}

	public String getName() {
	    return "firebase_remove_listener";
	}

	public Integer[] numArgs() {
	    return new Integer[] {1};
	}

	public String docs() {
	    return "void {listener id} Unbinds a listener so that is no longer firing. All listeners are unbound on stop or reloadalisa.";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
    
    @api
    public static class firebase_config extends AbstractFunction {

	public ExceptionType[] thrown() {
	    return new ExceptionType[] {ExceptionType.CastException};
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
	    String ret;
	    if ("firebase-url".equals(args[0].val())) {
		ret = CHFirebaesConfig.FirebaseUrl();
	    } else if("auth-id".equals(args[0].val())) {
		ret = CHFirebaesConfig.AuthId();
	    } else {
		throw new ConfigRuntimeException("Invalid config option", ExceptionType.NotFoundException, t);
	    }
	    
	    return new CString(ret, t);
	}

	public String getName() {
	    return "firebase_config";
	}

	public Integer[] numArgs() {
	    return new Integer[] {1};
	}

	public String docs() {
	    return "string {option} Returns a config option set in the CHFirebase.ini file. This is useful for using a common firebase reference. The auth-token can not be obtained through this function.";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
}