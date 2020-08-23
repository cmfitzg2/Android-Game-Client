# Android-Game-Client
Client side of an Android paint game. Users connect to a central server via this app, and participate in a Cards Against Humanity meets Pictionary style game. 

Game is playable, but there's a few caveats:
* People can't dynamically leave / join without restarting the server.
* Game state can fall out of sync if people don't keep the app in focus (need to implement message queueing, or some sort of TCP-esque ACK system).
* It's only playable on a LAN. Haven't learned proper Web Sockets yet, but it's on the TODO list. 
* It's pretty unpolished. No fancy graphics or animations. Only one layout, so low & high DPI screens / resolutions will have pretty drastically different UIs, perhaps even broken at specific combinations
* It's called MyApplication

![App screenshot 1](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-1.png?raw=true)
![App screenshot 2](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-2.png?raw=true)
![App screenshot 3](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-3.png?raw=true)
![App screenshot 4](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-4.png?raw=true)
![App screenshot 5](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-5.png?raw=true)
![App screenshot 6](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-6.png?raw=true)
![App screenshot 7](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-7.png?raw=true)
![App screenshot 8](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Android-8.png?raw=true)

![Server screenshot 1](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Server-1.png?raw=true)
![Server screenshot 2](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Server-2.png?raw=true)
![Server screenshot 3](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Server-3.png?raw=true)
![Server screenshot 4](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Server-4.png?raw=true)
![Server screenshot 5](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Server-5.png?raw=true)
![Server screenshot 6](https://github.com/cmfitzg2/Android-Game-Client/blob/master/app/src/main/res/drawable/promo-art/Server-6.png?raw=true)
