/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.service.twitch;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

/**
 *
 * @author CHOTT
 */
public class BotRunnable implements Runnable{

    private final PircBotX bot;

    public BotRunnable(PircBotX bot) {
        this.bot = bot;
    }
    
    @Override
    public void run() {
        System.out.println("Started Bot");
        
        try {
            bot.startBot();
        } catch (IOException ex) {
            Logger.getLogger(BotRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IrcException ex) {
            Logger.getLogger(BotRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMessage(String channelName, String message){
        bot.send().message(channelName, message);
    }
    
}
