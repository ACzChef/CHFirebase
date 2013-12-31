CHFirebase
==========
# Functions
## DataManipulation
### int firebase\_child\_added(firebase reference, [options], callback):
Adds a litener for when childern are added to a firebase reference. Runs for every child alreay existing and every new child.callback is a closure that is run when a child is added, It is passed the data of the added child. Options is an array, Please see options for what it should contain. Returns the listener id.

### string firebase\_push(firebsae reference, value, [options], [callback]):
Pushes a value to a firebase reference. Options is an array, Please see options to see what it should contain. Callback is a closure that will run when the set is finished, it is passed data that contains an error code, null if no error occurred. Returns the id of the pushed value.

### int firebase\_read(firebase reference, [options], callback):
Reads the data at a firebase reference. callback is a closure that is run when the data is recieved or changes, It is passed the data at the requested location. Options is an array, Please see options for what it should contain. Returns the listener id.

### void firebase\_read\_once(firebase reference, [options], callback):
Reads the data at a firebase reference. The data is only read oncecallback is a closure that is run when the data is recieved, It is passed the data at the requested location. Options is an array, Please see options for what it should contain.

### void firebase\_remove\_listener(listener id):
Unbinds a listener so that is no longer firing. All listeners are unbound on stop or reloadalisa.

### void firebase\_set(firebsae reference, value, [options], [callback]):
Sets a value to a firebase reference. Options is an array, Please see options to see what it should contain. Callback is a closure that will run when the set is finished, it is passed data that contains an error code, null if no error occurred.

### void firebase\_set\_priority(firebase refernce, priority):
Sets the priority of a firebase refernce.

