/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.service.twitch;

import de.chott.marathonbot.service.SingletonService;
import org.jibble.pircbot.PircBot;

public class TwitchChatBotService extends PircBot implements SingletonService{
    
    String channelName ="";
    
    public TwitchChatBotService(){
        
    }
    
    public void startBot(String username, String oauth, String channel) throws Exception{
        channelName = "#" +channel;
        
        setEncoding("UTF-8");
        setVerbose(true);
        setName(username);
        setLogin(username);
        
        connect("irc.twitch.tv", 6667, oauth);
    }
    @Override
    protected void onConnect(){
        joinChannel(channelName);
        super.onConnect();
    }
    
    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        super.onJoin(channel, sender, login, hostname);
        sendMessage(channelName, "Hello everyone, a testing bot here.");
    }
    
    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message){
        if (message.startsWith("!hello")){
            sendMessage("Hello " + sender);
        }
    }
    
    public void sendMessage(String message){
        sendMessage(channelName, message);
    }
    
    @Override
    public void close(){
        this.disconnect();
    }
}
