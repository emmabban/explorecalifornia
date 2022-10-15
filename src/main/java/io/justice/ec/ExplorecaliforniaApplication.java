package io.justice.ec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.justice.ec.service.TourPackageService;
import io.justice.ec.service.TourService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

/*@AllArgsConstructor*/
@RequiredArgsConstructor
@SpringBootApplication
public class ExplorecaliforniaApplication implements CommandLineRunner {

	@Value("${ec.importfile}")
	private String importFile;
	@NonNull
	private final TourService tourService;
	@NonNull
	private final TourPackageService tourPackageService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliforniaApplication.class, args);
	}

	private void loadToursAtStartup() throws IOException {
		//Create the tour Packages
		createTourPackages();
		long numOfPackages = tourPackageService.total();

		/*Resource resource = new ClassPathResource("classpath:exploreCalifornia.json");
		InputStream inputStream = resource.getInputStream();*/

		//Load the tours from an external JSON File
		//createTours("exploreCalifornia.json");
		createTours(importFile);
		long numOfTours = tourService.total();

	}

	/**
	 * Initialize all the known tour packages
	 */
	private void createTourPackages() {
		tourPackageService.createTourPackage("BC","Backpack California");
		tourPackageService.createTourPackage("CC","California Calm");
		tourPackageService.createTourPackage("CH","California Hot Springs");
		tourPackageService.createTourPackage("CY","Cycle California");
		tourPackageService.createTourPackage("DS","From Desert to Sea");
		tourPackageService.createTourPackage("KC","Kids California");
		tourPackageService.createTourPackage("NW","Nature Watch");
		tourPackageService.createTourPackage("SC","Snowboard California");
		tourPackageService.createTourPackage("TC","Taste of California");
	}

	/**
	 * Create tour entities from an external file
	 */
	private void createTours(String fileImport) throws IOException {
		TourFromFile.read(fileImport).forEach(importedTour ->
				tourService.createTour(importedTour.getTitle(),
						importedTour.getPackageName(),
						importedTour.getDetails()));
	}



	@Override
	public void run(String... args) throws Exception {
		loadToursAtStartup();
	}

	/**
	 * Helper class to import exploreCalifornia.json for a MongoDb Document.
	 * Only interested in the title and package name, the remaining fields
	 * are a collection of key-value pairs
	 *
	 */
	/*private static class TourFromFile {
		//fields
		String title;
		String packageName;
		Map<String, String> details;

		TourFromFile(Map<String, String> record) {
			this.title =  record.get("title");
			this.packageName = record.get("packageType");
			this.details = record;
			this.details.remove("packageType");
			this.details.remove("title");
		}
		//reader
		static List<TourFromFile> read(String fileToImport) throws IOException {
			List<Map<String, String>> records = new ObjectMapper().setVisibility(FIELD, ANY).
					readValue(new FileInputStream(fileToImport),
							new TypeReference<List<Map<String, String>>>() {});
			return records.stream().map(TourFromFile::new)
					.collect(Collectors.toList());
		}

		String getTitle() {
			return title;
		}

		String getPackageName() {
			return packageName;
		}

		Map<String, String> getDetails() {
			return details;
		}
	}*/

	/**
	 * Helper class to import ExploreCalifornia.json
	 */
	private static class TourFromFile {
		//fields
		private String title, packageName;
		Map<String, String> details;

		TourFromFile(Map<String, String> record){
			this.title = record.get("title");
			this.packageName = record.get("packageType");
			this.details = record;
			this.details.remove("packageType");
			this.details.remove("title");

		}
		//reader
		static List<TourFromFile> read(String fileToImport) throws IOException {

			List<Map<String, String>> records = new ObjectMapper().setVisibility(FIELD, ANY).
					readValue(new FileInputStream(fileToImport), new TypeReference<List<Map<String,String>>>() {});

			return records.stream().map(TourFromFile::new).collect(Collectors.toList());
		}
		protected TourFromFile(){}
		String getTitle() { return title; }
		String getPackageName() { return packageName; }

		Map<String, String> getDetails() {
			return details;
		}


	}
}
