#!/bin/bash
mkdir -p ./classes

#compiling interfaces
javac -d classes -classpath .:classes ./chatInterfaces/*.java
javac -d classes -classpath .:classes ./clientChat/*.java
javac -d classes -classpath .:classes ./serverChat/*.java

#generating .jar files from interfaces
cd classes
jar cvf Chat_itf.jar ./chatInterfaces/Chat_itf.class
jar cvf Client_itf.jar ./chatInterfaces/Client_itf.class
jar cvf ChatRoom_itf.jar ./chatInterfaces/ChatRoom_itf.class
jar cvf Server.jar ./serverChat/Server.class

javac -cp .:classes:Chat_itf.jar:Server.jar:ChatRoom_itf.jar ../serverChat/RunServer.java 
javac -cp .:classes:Client_itf.jar ../clientChat/RunClient.java 
#jar cvf ../Chat.jar serverChat chatInterfaces clientChat

#generate a .jar for each main class
touch ./MANIFEST.mf
echo "Manifest-Version: 1.0" >> ./MANIFEST.mf
echo "Main-Class: serverChat.RunServer" >> ./MANIFEST.mf
jar cfm ../Server.jar ./MANIFEST.mf ./chatInterfaces/* ./clientChat/* ./serverChat/*
rm ./MANIFEST.mf

touch ./MANIFEST.mf
echo "Manifest-Version: 1.0" >> ./MANIFEST.mf
echo "Main-Class: clientChat.RunClient" >> ./MANIFEST.mf
jar cfm ../Client.jar ./MANIFEST.mf ./chatInterfaces/* ./clientChat/* ./serverChat/*
rm ./MANIFEST.mf

#clean up compiled files
cd ..
rm -rf classes
rm serverChat/RunServer.class
rm clientChat/RunClient.class



