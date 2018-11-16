/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.service.twitch;

import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.DashboardDataService;
import de.chott.marathonbot.service.template.TemplateService;
import de.chott.marathonbot.util.config.ConfigConstants;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class WrCommandListener extends ListenerAdapter {

	private TemplateService templateService;
	private DashboardDataService dataService;

	private boolean isWrCommandInTimeout = false;

	public WrCommandListener() {
		templateService = SingletonServiceFactory.getInstance(TemplateService.class);
		dataService = SingletonServiceFactory.getInstance(DashboardDataService.class);
	}

	@Override
	public void onMessage(MessageEvent event) throws Exception {
		if (event.getMessage().startsWith("!wr")) {
			if (!isWrCommandInTimeout) {
				event.getChannel().send().message(templateService.getProcessedTemplate(
						ConfigConstants.WR_COMMAND_TEMPLATE_NAME, dataService.getCurrentEntry()));

				startWrCommandTimeout();
			}
		}
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

}
