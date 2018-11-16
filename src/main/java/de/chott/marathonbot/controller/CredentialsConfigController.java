package de.chott.marathonbot.controller;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.config.ConfigService;
import de.chott.marathonbot.service.discord.DiscordChatBotService;
import de.chott.marathonbot.service.twitch.TwitchChatBotService;
import de.chott.marathonbot.service.util.UtilService;
import static de.chott.marathonbot.util.config.ConfigConstants.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class CredentialsConfigController implements Initializable {

	@FXML
	private TextField twitchUsername;
	@FXML
	private PasswordField twitchOauth;
	@FXML
	private TextField twitchChannel;
	@FXML
	private TitledPane x1;
	@FXML
	private Button startButton;

	ConfigService configService;
	@FXML
	private TitledPane x2;
	@FXML
	private PasswordField discordOAuth;
	@FXML
	private TextField discordChannel;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		configService = SingletonServiceFactory.getInstance(ConfigService.class);

		twitchUsername.setText(configService.getConfig(TWITCH_USERNAME).orElse(""));
		twitchOauth.setText(configService.getConfig(TWITCH_OAUTH).orElse(""));
		twitchChannel.setText(configService.getConfig(TWITCH_CHANNEL).orElse(""));
		discordOAuth.setText(configService.getConfig(DISCORD_OAUTH).orElse(""));
		discordChannel.setText(configService.getConfig(DISCORD_CHANNEL).orElse(""));
	}

	@FXML
	private void startBots(ActionEvent event) {

		configService.setConfig(TWITCH_USERNAME, twitchUsername.getText());
		configService.setConfig(TWITCH_OAUTH, twitchOauth.getText());
		configService.setConfig(TWITCH_CHANNEL, twitchChannel.getText());
		configService.setConfig(DISCORD_OAUTH, discordOAuth.getText());
		configService.setConfig(DISCORD_CHANNEL, discordChannel.getText());

		try {
			SingletonServiceFactory.getInstance(TwitchChatBotService.class)
					.startBot(twitchUsername.getText(),
							twitchOauth.getText(),
							twitchChannel.getText());

			SingletonServiceFactory.getInstance(DiscordChatBotService.class)
					.startBot(discordOAuth.getText(), discordChannel.getText());

			SingletonServiceFactory.getInstance(UtilService.class).switchScene(startButton, "/fxml/Dashboard.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
