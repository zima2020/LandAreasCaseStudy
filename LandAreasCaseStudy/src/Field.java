import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Field {
	// Queue used to perform BFS
	private LinkedList<Point> queue = new LinkedList<Point>();
	// Horizontal bounds
	public static int xLimit = 400;
	// Vertical bounds
	public static int yLimit = 600;
	// 2D array representing the 400 X 600 grid.
	int landMap[][] = new int[xLimit][yLimit];
	// HashMap of farmable land
	HashMap<Integer, Integer> farmableLandMap = new HashMap<Integer, Integer>();

	// initialize array to all 0
	public void zeroMatrix() {
		for (int i = 0; i < xLimit; i++) {
			for (int j = 0; j < yLimit; j++) {
				landMap[i][j] = 0;
			}
		}
	}

	// Traverse the entire area of the rectangle, and mark it 1 for barren.
	public void setBarren(Point lowerLeft, Point upperRight) {
		for (int i = lowerLeft.x; i <= upperRight.x; i++) {
			for (int j = lowerLeft.y; j <= upperRight.y; j++) {

				landMap[i][j] = 1;
			}
		}

	}

	// adds adjacent points to the queue of points to be checked
	public void addAdjacent(int x, int y) {
		if (x > 0) {
			if (landMap[x - 1][y] == 0) {
				queue.add(new Point(x - 1, y));
			}
		}
		if (x < (xLimit - 1)) {
			if (landMap[x + 1][y] == 0) {
				queue.add(new Point(x + 1, y));
			}
		}
		if (y > 0) {
			if (landMap[x][y - 1] == 0) {
				queue.add(new Point(x, y - 1));
			}
		}
		if (y < (yLimit - 1)) {
			if (landMap[x][y + 1] == 0) {
				queue.add(new Point(x, y + 1));
			}
		}

	}

	// implementation of the BFS traversal
	public void calculatefarmableLand() {
		int farmableLand = 1;
		int i = 0;
		int j = 0;

		while (i < xLimit && j < yLimit) {
			if (queue.isEmpty()) {
				Point coordinate = new Point(i, j);
				if (landMap[i][j] == 0) {
					farmableLand++;
					farmableLandMap.put(farmableLand, 0);
					queue.add(coordinate);
				}
				if (i == (xLimit - 1)) {
					// Move to the next row
					i = 0;
					j++;
				} else {
					// Check the next x coordinate
					i++;
				}
			}
			if (!queue.isEmpty()) {
				Point farmableCoordinate = queue.pop();
				int x = farmableCoordinate.x;
				int y = farmableCoordinate.y;

				if (landMap[x][y] == 0) {
					// Add adjacent points to the queue to be checked.
					addAdjacent(x, y);
					// Mark this location as checked.
					landMap[x][y] = farmableLand;
					// increment the value of this section of farmable land.
					farmableLandMap.put(farmableLand, farmableLandMap.get(farmableLand) + 1);
				}
			}

		}
	}

	// prints farmable land areas to the system output

	public void printGrid() {

		int i = 0;
		int[] farmableLandPieces = new int[farmableLandMap.values().size()];

		for (HashMap.Entry<Integer, Integer> entry : farmableLandMap.entrySet()) {
			try {
				farmableLandPieces[i] = entry.getValue();
				i++;
			} catch (ArrayIndexOutOfBoundsException e) {

				e.getMessage();
			}
		}

		if (farmableLandPieces.length == 0) {
			// No fertile land was found
			System.out.print("No fertile land found");
		}

		else {
			Arrays.sort(farmableLandPieces);
			String output = Arrays.toString(farmableLandPieces).replaceAll("\\[|\\]|,", " ");
			System.out.println(output);
		}

	}

}
