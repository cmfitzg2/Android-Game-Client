# Android-Game-Client
Client side of an Android paint game. Users connect to a central server via this app, and participate in a Cards Against Humanity meets Pictionary style game. 

Game is playable, but there's a few caveats:
* People can't dynamically leave / join without restarting the server.
* Game state can fall out of sync if people don't keep the app in focus (need to implement message queueing, or some sort of TCP-esque ACK system).
* It's only playable on a LAN. Haven't learned proper Web Sockets yet, but it's on the TODO list. 
* It's pretty unpolished. No fancy graphics or animations. Only one layout, so low & high DPI screens / resolutions will have pretty drastically different UIs.
* It's called MyApplication
