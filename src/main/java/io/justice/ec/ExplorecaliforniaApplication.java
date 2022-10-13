package io.justice.ec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.justice.ec.domiain.Difficulty;
import io.justice.ec.domiain.Region;
import io.justice.ec.repository.TourPackageRepository;
import io.justice.ec.repository.TourRepository;
import io.justice.ec.service.TourPackageService;
import io.justice.ec.service.TourService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@AllArgsConstructor
@SpringBootApplication
public class ExplorecaliforniaApplication implements CommandLineRunner {
	private final TourService tourService;
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
		createTours("exploreCalifornia.json");
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
						importedTour.getDescription(),
						importedTour.getBlurb(),
						importedTour.getPrice(),
						importedTour.getLength(),
						importedTour.getBullets(),
						importedTour.getKeywords(),
						importedTour.getPackageType(),
						importedTour.getDifficulty(),
						importedTour.getRegion()));
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
		private String packageType, title, description, blurb, price, length,
				bullets, keywords, difficulty, region;
		//reader
		static List<TourFromFile> read(String fileToImport) throws IOException {
			return new ObjectMapper().setVisibility(FIELD, ANY).
					readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
		}
		protected TourFromFile(){}

		String getPackageType() { return packageType; }

		String getTitle() { return title; }

		String getDescription() { return description; }

		String getBlurb() { return blurb; }

		Integer getPrice() { return Integer.parseInt(price); }

		String getLength() { return length; }

		String getBullets() { return bullets; }

		String getKeywords() { return keywords; }

		Difficulty getDifficulty() { return Difficulty.valueOf(difficulty); }

		Region getRegion() { return Region.findByLabel(region); }
	}
}
