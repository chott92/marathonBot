package de.chott.marathonbot.service.template;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import de.chott.marathonbot.model.ui.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.config.ConfigService;
import static de.chott.marathonbot.util.config.ConfigConstants.*;
import static de.chott.marathonbot.util.config.ConfigConstants.WR_COMMAND_TEMPLATE_NAME;
import java.util.HashMap;
import java.util.Map;

public class TemplateService implements SingletonService {

	private ConfigService configService;

	private Map<String, String> templateMap;

	public TemplateService() {
		configService = SingletonServiceFactory.getInstance(ConfigService.class);

		setupTemplateMap();
	}

	private void setupTemplateMap() {
		templateMap = new HashMap<>();

		templateMap.put(TITLE_TEMPLATE_NAME, configService.getConfig(TITLE_TEMPLATE_NAME).orElse(""));
		templateMap.put(WR_COMMAND_TEMPLATE_NAME, configService.getConfig(WR_COMMAND_TEMPLATE_NAME).orElse(""));
		templateMap.put(DISCORD_TEMPLATE_NAME, configService.getConfig(DISCORD_TEMPLATE_NAME).orElse(""));
	}

	public String getProcessedTemplate(String templateName, RunConfigTableEntry entry) {
		return templateMap.get(templateName)
				.replace(TEMPLATE_VARIABLE_GAME, entry.getGame())
				.replace(TEMPLATE_VARIABLE_RUNNER_NAME, entry.getRunnerName())
				.replace(TEMPLATE_VARIABLE_RUNNER_PB, entry.getRunnerPB())
				.replace(TEMPLATE_VARIABLE_WR_HOLDER_NAME, entry.getWrHolderName())
				.replace(TEMPLATE_VARIABLE_WR_TIME, entry.getWrTime())
				.replace(TEMPLATE_VARIABLE_CATEGORY, entry.getCategory())
				.replace(TEMPLATE_VARIABLE_SPEEDRUNCOM, entry.getSpeedrunComLink());
	}

	public void updateTemplate(String templateName, String template) {
		templateMap.put(templateName, template);

		configService.setConfig(templateName, template);
	}
}
