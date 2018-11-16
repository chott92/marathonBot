package de.chott.marathonbot.service.discord;

import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.DashboardDataService;
import de.chott.marathonbot.service.template.TemplateService;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordChatBotService implements SingletonService {

	private final TemplateService templateService;
	private final DashboardDataService dashboardDataService;

	private DiscordApi bot;

	public DiscordChatBotService() {
		this.templateService = SingletonServiceFactory.getInstance(TemplateService.class);
		this.dashboardDataService = SingletonServiceFactory.getInstance(DashboardDataService.class);
	}

	public void startBot(String oAuth) {
		System.out.println("API token: " + oAuth);

		bot = new DiscordApiBuilder().setToken(oAuth).login().join();

		bot.addMessageCreateListener(event -> {
			if (event.getMessage().getContent().equalsIgnoreCase("!ping")) {
				event.getChannel().sendMessage("Pong!");
			}
		});

	}

}
