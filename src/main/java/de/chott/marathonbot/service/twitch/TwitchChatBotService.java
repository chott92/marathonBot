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
import org.jibble.pircbot.PircBot;

public class TwitchChatBotService extends PircBot implements SingletonService {

    private String channelName = "";
    
    private TemplateService templateService;
    private DashboardDataService dataService;
    
    public TwitchChatBotService() {
        templateService = SingletonServiceFactory.getInstance(TemplateService.class);
        dataService = SingletonServiceFactory.getInstance(DashboardDataService.class);
    }

    public void startBot(String username, String oauth, String channel) throws Exception {
        channelName = "#" + channel;

        setEncoding("UTF-8");
        setVerbose(true);
        setName(username);
        setLogin(username);

        connect("irc.twitch.tv", 6667, oauth);
    }

    @Override
    protected void onConnect() {
        joinChannel(channelName);
        super.onConnect();
    }

    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        super.onJoin(channel, sender, login, hostname);
        sendMessage(channelName, "MarathonBot started.");
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.equals("!wr")) {
            sendMessage(
                    templateService.getProcessedTemplate(
                            ConfigConstants.WR_COMMAND_TEMPLATE_NAME, dataService.getCurrentEntry())
            );
        }

    }

    public void sendMessage(String message) {
        sendMessage(channelName, message);
    }

    @Override
    public void close() {
        this.disconnect();
    }
}
