package de.chott.marathonbot.service.discord;

import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.DashboardDataService;
import de.chott.marathonbot.service.template.TemplateService;
import de.chott.marathonbot.service.twitch.WrCommandListener;
import de.chott.marathonbot.util.config.ConfigConstants;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordChatBotService implements SingletonService {

	private final TemplateService templateService;
	private final DashboardDataService dataService;

	private boolean isWrCommandInTimeout = false;
	private DiscordApi bot;
	private String channelName;

	public DiscordChatBotService() {
		this.templateService = SingletonServiceFactory.getInstance(TemplateService.class);
		this.dataService = SingletonServiceFactory.getInstance(DashboardDataService.class);
	}

	public void startBot(String oAuth, String channel) {
		channelName = channel;
		bot = new DiscordApiBuilder().setToken(oAuth).login().join();

		bot.addMessageCreateListener(event -> {
			if (event.getMessage().getContent().startsWith("!wr")) {
				if (!isWrCommandInTimeout) {
					event.getChannel().sendMessage(templateService.getProcessedTemplate(
							ConfigConstants.WR_COMMAND_TEMPLATE_NAME, dataService.getCurrentEntry()));
					startWrCommandTimeout();
				}
			}
		});

	}

	public void sendMessage(String message) {
		bot.getTextChannelsByName(channelName)
				.forEach(ch -> ch.sendMessage(message));
	}

	private void startWrCommandTimeout() {
		setIsWrCommandInTimeout(true);
		CompletableFuture.runAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException ex) {
				Logger.getLogger(WrCommandListener.class.getName()).log(Level.SEVERE, null, ex);
			}
		}).thenRun(() -> setIsWrCommandInTimeout(false));
	}

	public void setIsWrCommandInTimeout(boolean isWrCommandInTimeout) {
		this.isWrCommandInTimeout = isWrCommandInTimeout;
	}

	@Override
	public void close() {
		bot.disconnect();
	}

}
