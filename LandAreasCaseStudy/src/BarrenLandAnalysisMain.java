import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BarrenLandAnalysisMain {

	// Initializing Field object
	private static Field myField = new Field();

	public static void main(String[] args) throws IOException {

		// set all nodes to 0
		myField.zeroMatrix();

		// Parse user input
		System.out.println("Please enter a set/sets of four numbers, or enter 'q' to exit: ");
		readInput();

		// Calculate non-barren land sections
		myField.calculatefarmableLand();

		// Print results to the console.
		myField.printGrid();

		System.exit(0);
	}

	// makes sure input coordinates are within given range
	public static void assertRange(Field land, String[] coordinates) {

		if ((new Integer(coordinates[0]) >= Field.xLimit) || new Integer(coordinates[2]) >= Field.xLimit) {

			throw new IllegalArgumentException(
					"Input coordinate is larger then given field size. This farm has width of:" + Field.xLimit);

		}

		if ((new Integer(coordinates[1]) >= Field.yLimit) || (new Integer(coordinates[3]) >= Field.yLimit)) {
			throw new IllegalArgumentException(

					"Input coordinate is larger then given field size. This farm has height of:" + Field.yLimit);
		}
	}

	// Reads and validates input
	public static void readInput() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		String stop = "q";
		if (s.equals(stop)) {
			System.out.println("System will now exit.");
			System.exit(0);
		}
		String[] parts = s.split(",");

		for (int i = 0; i < parts.length; i++) {
			String[] numbers = parts[i].replaceAll("[^0-9]+", " ").trim().split(" ");

			if (numbers.length == 4) {

				assertRange(myField, numbers);

				myField.setBarren(new Point(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])),
						new Point(Integer.parseInt(numbers[2]), Integer.parseInt(numbers[3])));
			} else {

				System.out.println("ERROR: " + Arrays.toString(numbers) + " needs to have exactly 4 numbers." + '\n'
						+ "Enter in a valid set of numbers, or enter 'q' to exit.");
				System.exit(0);

			}

		}
	}
}