package bettersoftware.birdwatching;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameField {

	public enum PlacingMode {
	    Custom, Random
	}

	List<Bird> birds;
	int width;
	int height;
	int depth;
	boolean gameStarted;
	private final FieldSize fieldSize;


	public GameField(final int width, final int height, final int depth, final FieldSize fieldSize) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.fieldSize = fieldSize;
		birds = new ArrayList<Bird>();
	}

	public void addBird(final Bird b) {
		birds.add(b);
	}

	//Check if the GameField is Valid
	private boolean isGameFieldValid()
	{
		boolean isValid = true;
		for(final Bird bird : birds) {
			final int h = bird.getHeight();
			final Location location = bird.getLocation();
			final int x = location.x;
			final int y = location.y;
			isValid =  fieldSize.isWithinField(h, x, y);
			if (!isValid) {
				break;
			}
		}
		return isValid;

	}

	//Place the birds on the fields
	private void placeBirds(final PlacingMode type) throws Exception {

		//Random Distribution
		if (type == PlacingMode.Random) {
			for(final Bird bird : birds) {
				final Location location = new Location(new Random().nextInt(this.width), new Random().nextInt(this.height));
				bird.setLocation(location);
				if (!(bird instanceof Chicken)) {
					bird.setHeight(new Random().nextInt(this.depth));
				}
			}
		}
		//Custom Distribution
		else if (type == PlacingMode.Custom) {

		}
	}

	//Shot to a bird
	public boolean shot(final int x, final int y, final int h) {
		boolean hit = false;
		if (gameStarted)
		{
			for(final Bird bird : birds) {
				final int height = bird.getHeight();
				final Location location = bird.getLocation();
				hit =  location.x == x && location.y == y && height == h;
				if (hit)
				{
					bird.sing();
					break;
				}
			}
		}
         return hit;
	}

	/**
	 * Starta the game
	 *
	 * @param pm
	 * @return
	 */
	public boolean startGame(final PlacingMode pm) {
		try {
			placeBirds(pm);
			gameStarted = (birds.size() > 0 && isGameFieldValid());
		} catch (final Exception e) {
			gameStarted = false;
		}
		return gameStarted;
	}


}
