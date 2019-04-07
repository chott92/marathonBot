package de.chott.marathonbot.service.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import de.chott.marathonbot.model.ui.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.data.RunConfigDataService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleSheetsImportService implements SingletonService {

	private static final String APPLICATION_NAME = "Germench MarathonBot";

	private final GoogleCredentialService credentialService;
	private final RunConfigDataService dataService;

	public GoogleSheetsImportService() {
		this.credentialService = SingletonServiceFactory.getInstance(GoogleCredentialService.class);
		this.dataService = SingletonServiceFactory.getInstance(RunConfigDataService.class);
	}

	public boolean importDataFromGoogleSheet(String spreadsheetId, String sheetName) {
		try {

			List<String[]> loadDataFromSheet = loadDataFromSheet(spreadsheetId, sheetName);

			List<RunConfigTableEntry> tableEntries = loadDataFromSheet.stream()
					.skip(1)
					.filter(DataColumn.B.isBlank().negate())
					.map(this::toConfigTableEntry)
					.collect(Collectors.toList());

			dataService.replaceData(tableEntries);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private RunConfigTableEntry toConfigTableEntry(String[] row) {
		String game = DataColumn.B.get(row);
		String runnerName = DataColumn.E.get(row);
		String wrTime = DataColumn.H.get(row);
		String runnerPB = DataColumn.F.get(row);
		String wrHolderName = DataColumn.I.get(row);
		String category = DataColumn.C.get(row);
		String speedrunComLink = DataColumn.D.get(row);

		return new RunConfigTableEntry(game, runnerName, wrTime, runnerPB, wrHolderName, category, speedrunComLink);
	}

	private List<String[]> loadDataFromSheet(String spreadsheetId, String sheetName) {

		try {
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentialService.getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME)
					.build();

			String range = sheetName + "!A1:L200";

			ValueRange response = service.spreadsheets().values()
					.get(spreadsheetId, range)
					.execute();
			List<List<Object>> values = response.getValues();

			return getStringFormattedValues(values);

		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	private List<String[]> getStringFormattedValues(List<List<Object>> values) {
		List<String[]> data = new ArrayList<>();

		for (List<Object> valueRow : values) {
			String[] row = new String[valueRow.size()];
			for (int i = 0; i < valueRow.size(); i++) {
				row[i] = valueRow.get(i).toString();
			}
			data.add(row);
		}

		return data;
	}

}
