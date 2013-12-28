package com.aczchef.chfirebase.core.functions;

import com.aczchef.chfirebase.core.CHFirebase;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.laytonsmith.PureUtilities.Common.StackTraceUtils;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHLog;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.LogLevel;
import com.laytonsmith.core.constructs.CClosure;
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
            
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
                //Should never run
                default: value = "";
                    callback = null;
            }
            
            ref = link.child(childern);
	    
	    if (callback == null) {
		ref.setValue(value);
	    } else {
		ref.setValue(value, new Firebase.CompletionListener() {

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
				callback.execute(new Construct[]{CError});
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
		});
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
            return "void {firebase reference, data | firebase referencem }";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
    
    }
    
    @api
    public static class firebase_push extends AbstractFunction {
	
        public Exceptions.ExceptionType[] thrown() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public boolean isRestricted() {
            return true;
        }

        public Boolean runAsync() {
            return false;
        }

        public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
            
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
                //Should never run
                default: value = "";
                    callback = null;
            }
            
            ref = link.child(childern).push();
	    
	    if (callback == null) {
		ref.setValue(value);
	    } else {
		ref.setValue(value, new Firebase.CompletionListener() {

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
				callback.execute(new Construct[]{CError});
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
		});
	    }
            return new CVoid(t);
        }

        public String getName() {
            return "firebase_push";
        }

        public Integer[] numArgs() {
            return new Integer[] {2, 3, 4};
        }

        public String docs() {
            return "void {firebase reference, data | firebase reference }";
        }

        public Version since() {
            return CHVersion.V3_3_1;
        }
    
    }
    
    @api
    public static class firebase_read_once extends AbstractFunction {

	public ExceptionType[] thrown() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    
	    Firebase link = new Firebase(args[0].val());
            final Firebase ref;
            String childern = "";
            final CClosure callback;
	    
	    switch(args.length) {
		case 3:
		    childern = args[1].val();
		    callback = (CClosure) args[2];
		    break;
		case 2:
		    callback = (CClosure) args[1];
		    break;
		//Should never run
		default:
		    callback = null;
	    }
	    
	    ref = link.child(childern);
	    
	    ref.addListenerForSingleValueEvent(new ValueEventListener() {

		public void onDataChange(DataSnapshot ds) {
		    Construct data = Construct.GetConstruct(ds.getValue());
			try{
			    callback.execute(new Construct[]{data});
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
		    throw new UnsupportedOperationException("Not supported yet.");
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
	    return "";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }

    @api
    public static class firebase_read extends AbstractFunction {

	public ExceptionType[] thrown() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    
	    Firebase link = new Firebase(args[0].val());
            final Firebase ref;
            String childern = "";
            final CClosure callback;
	    switch(args.length) {
		case 3:
		    childern = args[1].val();
		    callback = (CClosure) args[2];
		    break;
		case 2:
		    callback = (CClosure) args[1];
		    break;
		//Should never run
		default:
		    callback = null;
	    }
	    
	    ref = link.child(childern);
	    
	    int id = CHFirebase.addListener(ref, ref.addValueEventListener(new ValueEventListener() {

		public void onDataChange(DataSnapshot ds) {
		    Construct data = Construct.GetConstruct(ds.getValue());
			try{
			    callback.execute(new Construct[]{data});
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
		    throw new UnsupportedOperationException("Not supported yet.");
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
	    return "";
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
    
    @api
    public static class firebase_child_added extends AbstractFunction {

	public ExceptionType[] thrown() {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	public boolean isRestricted() {
	    return true;
	}

	public Boolean runAsync() {
	    return false;
	}

	public Construct exec(final Target t, final Environment environment, Construct... args) throws ConfigRuntimeException {
	    
	    Firebase link = new Firebase(args[0].val());
            final Firebase ref;
            String childern = "";
            final CClosure callback;
	    switch(args.length) {
		case 3:
		    childern = args[1].val();
		    callback = (CClosure) args[2];
		    break;
		case 2:
		    callback = (CClosure) args[1];
		    break;
		//Should never run
		default:
		    callback = null;
	    }
	    
	    ref = link.child(childern);
	    
	    int id = CHFirebase.addListener(ref, ref.addChildEventListener(new ChildEventListener() {

                public void onChildAdded(DataSnapshot ds, String string) {
                    Construct data = Construct.GetConstruct(ds.getValue());
			try{
			    callback.execute(new Construct[]{data});
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
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
	    return "";
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
	    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public Version since() {
	    return CHVersion.V3_3_1;
	}
	
    }
}