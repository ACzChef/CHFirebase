package com.aczchef.chfirebase.core.functions;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.laytonsmith.PureUtilities.Common.StackTraceUtils;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.StaticLayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHLog;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.LogLevel;
import com.laytonsmith.core.constructs.CClosure;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.environments.GlobalEnv;
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
public class FireBase {
    
    @api
    public static class fire_set extends AbstractFunction {
	
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
	    
	    final Runnable execute = new Runnable() {
		public void run() {
		    ref.setValue(value, new Firebase.CompletionListener() {

			public void onComplete(FirebaseError fe) {
			    final String error;
                            if (fe != null) {
                                error = fe.getMessage();
                            } else {
				error = "";
			    }
			    
			    final CString CError = new CString(error, t);
			    StaticLayer.GetConvertor().runOnMainThreadLater(environment.getEnv(GlobalEnv.class).GetDaemonManager(), new Runnable() {
				public void run() {
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
		    });
		}
	    };
	    
	    if (callback == null) {
		ref.setValue(value);
	    } else {
		Thread th = new Thread() {
		    @Override
		    public void run() {
			execute.run();
		    }
		};
		
		th.start();
	    }
	    
            return new CVoid(t);
	    
        }

        public String getName() {
            return "fire_set";
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
    public static class fire_read_once extends AbstractFunction {

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
	    
	    final Runnable execute = new Runnable() {
		public void run() {
		    ref.addListenerForSingleValueEvent(new ValueEventListener() {

			public void onDataChange(DataSnapshot ds) {
			    final CString data = new CString(ds.getValue().toString(), t);
			    StaticLayer.GetConvertor().runOnMainThreadLater(environment.getEnv(GlobalEnv.class).GetDaemonManager(), new Runnable() {
				public void run() {
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
			    });
			}

			public void onCancelled() {
			    throw new UnsupportedOperationException("Not supported yet.");
			}
		    });
		}
	    };
	    
	    Thread th = new Thread() {
		@Override
		public void run() {
		    execute.run();
		}
	    };
	    th.start();
	    
	    return new CVoid(t);
	    
	}

	public String getName() {
	    return "fire_read_once";
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
    public static class fire_read extends AbstractFunction {

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
	    
	    final Runnable execute = new Runnable() {
		public void run() {
		    ref.addValueEventListener(new ValueEventListener() {

			public void onDataChange(DataSnapshot ds) {
			    final CString data = new CString(ds.getValue().toString(), t);
			    
			    StaticLayer.GetConvertor().runOnMainThreadLater(environment.getEnv(GlobalEnv.class).GetDaemonManager(), new Runnable() {
				public void run() {
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
			    });
			}

			public void onCancelled() {
			    throw new UnsupportedOperationException("Not supported yet.");
			}
		    });
		}
	    };
	    
	    Thread th = new Thread() {
		@Override
		public void run() {
		    execute.run();
		}
	    };
	    th.start();
	    
	    return new CVoid(t);
	    
	}

	public String getName() {
	    return "fire_read";
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
}