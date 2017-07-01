/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.service.twitch;

import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.DashboardDataService;
import de.chott.marathonbot.service.template.TemplateService;
import de.chott.marathonbot.util.config.ConfigConstants;
import java.nio.charset.Charset;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.hooks.managers.BackgroundListenerManager;

public class TwitchChatBotService implements SingletonService {

    private String channelName = "";

    private TemplateService templateService;
    private DashboardDataService dataService;

    BotRunnable bot;
    Thread botThread;

    public TwitchChatBotService() {

        templateService = SingletonServiceFactory.getInstance(TemplateService.class);
        dataService = SingletonServiceFactory.getInstance(DashboardDataService.class);
    }

    public void startBot(String username, String oauth, String channel) throws Exception {

        BackgroundListenerManager manager = new BackgroundListenerManager();
        manager.addListener(new WrCommandListener(), true);
        
        channelName = "#" +channel;
        
        
        
        Configuration config = new Configuration.Builder()
                .setAutoNickChange(false)
                .setOnJoinWhoEnabled(false)
                .setCapEnabled(true)
                .addCapHandler(new EnableCapHandler("twitch.tv/membership")) 
                .setEncoding(Charset.forName("UTF-8"))
                
                .addServer("irc.twitch.tv", 6667)
                .setName(username) //Your twitch.tv username
                .setServerPassword(oauth) //Your oauth password from http://twitchapps.com/tmi
                .addAutoJoinChannel(channelName) //Some twitch channel
                .setListenerManager(manager)
                
                .buildConfiguration();
        
        PircBotX chatBot = new PircBotX(config);
        
        bot = new BotRunnable(chatBot);
        
        botThread = new Thread(bot);
        botThread.start();
        
    }

    public void sendMessage(String message) {
        bot.sendMessage(channelName, message);
    }

    @Override
    public void close() {
        botThread.stop();
    }
}
