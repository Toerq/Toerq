package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class MyPongModel implements PongModel {

	final private int windowX = 800;
	final private int windowY = 800;
	final Dimension dimension = new Dimension(windowX, windowY);
	final int halfWindowWidth = windowX / 2;
	final int halfWindowHeight = windowY / 2;
	private double gameSpeedRate;
	private boolean currentcomputeIsANewGame = true;
	private boolean gameWasPaused = false;

	// messageToWindow will be set to 0 if no one has currently won a game.
	// 1 if LEFT has currently won a game and 2 if RIGHT has won a game.
	private int messageToWindow = 0;
	private int leftBarPos = halfWindowWidth;
	private int rightBarPos = halfWindowHeight;


	// These values will be a range [intervalStart, intervalEnd] where a
	// number x will be RNG'd will be generated in this range.
	// x will be the start size of the horizontal direction vector for the ball.
	private int intervalStart = 16;
	private int intervalEnd = 20;

	private int scoreLEFT = 0;
	private int scoreRIGHT = 0;
	private int wonGamesLEFT = 0;
	private int wonGamesRIGHT = 0;

	private Point2D ball = new Point2D.Double(halfWindowWidth, halfWindowHeight);
	private Point2D direction = new Point2D.Double(0, 0);

	private String nameLEFT = "";
	private String nameRIGHT = "";
	
	/*---------------------------------------------------------------*/
	// Change these values to alter into a different but still relevant game play experience
	final int initalLeftBarHeight = 200; // 200 = standard
	final int initalRightBarHeight = 200; // 200 = standard
	private int barMovementSpeed = 30; // 30 = standard
	private double ballSpeedAddedAfterBarBounce = 2.0; // 2.0 = standard
	private int scoreToWinAGame = 10; // 10 = standard
	private boolean resetBarsAfterPoint = true; // false = standard
	
	//Set handicap (eg. barShrinkRateLEFT > barShrinkRateRIGHT if LEFT is more skilled)
	//You can even put a negative value to increase the bar height for each bounce.
	private int barShrinkRateLEFT = 20; // 20 = standard
	private int barShrinkRateRIGHT = 20; // 20 = standard
	
	//How close the ball need to be to a bar edge to change its angle direction.
	private int allowedDistanceToBarEdge = 30; // 30 = standard
	
	// amount of seconds to wait after score.
	private int waitAfterScore = 1; // 0 = standard
	
	/*---------------------------------------------------------------*/
	
	private int leftBarHeight = initalLeftBarHeight;
	private int rightBarHeight = initalRightBarHeight;

	public MyPongModel(String player1, String player2) {
		this.nameLEFT = player1;
		this.nameRIGHT = player2;
	}

	private void randomStartDirection() {
		randomStartDirection(0);
	}

	private void randomStartDirection(int server) {
		boolean trueOrFalse1 = ((int) (Math.random() * (2)) == 1);
		boolean trueOrFalse2 = ((Math.random() * (2)) == 1);
		double x = intervalStart + (Math.random() * (intervalEnd));
		double y = (int) (Math.random() * (2));

		switch (server) {
		case (0):
			System.out.println("0");
			if (trueOrFalse1) {
				x = -x;
			}
			break;
		case (1):
			System.out.println("1");
			x = Math.abs(x);
			break;
		case (2):
			System.out.println("2");
			x = -Math.abs(x);
			break;
		}
		if (trueOrFalse2) {
			y = -y;
		}
		direction.setLocation(x * gameSpeedRate, y * gameSpeedRate);
	}

	public void compute(Set<Input> input, long delta_t) {
		gameSpeedRate = (double) delta_t * (1.0 / 33.0);
		victor();
		if (currentcomputeIsANewGame) {
			randomStartDirection();
			currentcomputeIsANewGame = false;
		}
		ballMover();
		playerBarMover(input);
		gameWasPaused = false;
	}

	private void playerBarMover(Set<Input> input) {
		if (gameWasPaused) {
			input.clear();
		}
		for (Input element : input) {
			switch (element.key) {
			case LEFT:
				switch (element.dir) {
				case UP:
					if (leftBarPos > leftBarHeight / 2)
						this.leftBarPos -= (int) (barMovementSpeed * gameSpeedRate);
					break;
				case DOWN:
					if (leftBarPos < windowY - leftBarHeight / 2)
						this.leftBarPos += (int) (barMovementSpeed * gameSpeedRate);
					break;
				}
				break;

			case RIGHT:
				switch (element.dir) {
				case UP:
					if (rightBarPos > rightBarHeight / 2)
						this.rightBarPos -= (int) (barMovementSpeed * gameSpeedRate);
					break;
				case DOWN:
					if (rightBarPos < windowY - rightBarHeight / 2)
						this.rightBarPos += (int) (barMovementSpeed * gameSpeedRate);
					break;
				}
				break;
			}
		}
	}

	private void ballMover() {
		double dirX = direction.getX();
		double dirY = direction.getY();
		double ballX = ball.getX();
		double ballY = ball.getY();
		boolean ballBouncesOnLEFTBar = ((ballX <= 20) && (leftBarPos - leftBarHeight / 2) < ballY)
				&& (ballY < (leftBarPos + leftBarHeight / 2));
		boolean ballBouncesOnRIGHTBar = ((ballX >= windowX - 20) && (rightBarPos - rightBarHeight / 2) < ballY)
				&& (ballY < (rightBarPos + rightBarHeight / 2));
		boolean ballMissesLEFTBar = (!ballBouncesOnLEFTBar && (ballX <= 20));
		boolean ballMissesRIGHTBar = (!ballBouncesOnRIGHTBar && (ballX >= windowX - 20));
		boolean ballBounceAtTopOrDown = (ball.getY() <= 8 || ball.getY() >= windowY - 8);

		if (ballBounceAtTopOrDown) {
			dirY = -direction.getY();
		}

		if (ballBouncesOnLEFTBar) {
			dirY = angleModifier(leftBarPos, leftBarHeight, true);
			if (direction.getX() < 50) {
				dirX = -direction.getX()
						+ (ballSpeedAddedAfterBarBounce * gameSpeedRate);
			} else
				dirX = -direction.getX();
			if (leftBarHeight > 40 && leftBarHeight < 400)
				leftBarHeight -= barShrinkRateLEFT;

		} else if (ballBouncesOnRIGHTBar) {
			dirY = angleModifier(rightBarPos, rightBarHeight, false);
			if (direction.getX() < 50) {
				dirX = -direction.getX()
						- (ballSpeedAddedAfterBarBounce * gameSpeedRate);
			} else
				dirX = -direction.getX();
			if (rightBarHeight > 40 && rightBarHeight < 400)
				rightBarHeight -= barShrinkRateRIGHT;
		}
		if (ballMissesLEFTBar) {
			randomStartDirection(1);
			ball.setLocation(halfWindowWidth, halfWindowHeight);
			scoreRIGHT++;
			leftBarHeight = initalLeftBarHeight;
			if (resetBarsAfterPoint) {
				leftBarPos = halfWindowHeight;
				rightBarPos = halfWindowHeight;
			}
			if (waitAfterScore != 0 && scoreRIGHT != scoreToWinAGame) {
				pause(waitAfterScore);
			}
		} else if (ballMissesRIGHTBar) {
			randomStartDirection(2);
			ball.setLocation(halfWindowWidth, halfWindowHeight);
			scoreLEFT++;
			rightBarHeight = initalRightBarHeight;
			if (resetBarsAfterPoint) {
				leftBarPos = halfWindowHeight;
				rightBarPos = halfWindowHeight;
			}
			if (waitAfterScore != 0 && scoreLEFT != scoreToWinAGame) {
				pause(waitAfterScore);
			}
		} else {
			direction.setLocation(dirX, dirY);
		}

		ballX = ball.getX() + direction.getX();
		ballY = ball.getY() + direction.getY();

		ball.setLocation(ballX, ballY);
	}

	private double angleModifier(int barPos, int barHeight, boolean barIsLEFT) {
		int distanceToBarEdge = -1;
		double x = direction.getX();
		double y = direction.getY();
		boolean ballAboveCenterOfBar = false;

		if (ball.getY() > barPos) {
			distanceToBarEdge = (barPos + barHeight / 2) - (int) ball.getY();
		} else if (ball.getY() < barPos) {
			ballAboveCenterOfBar = true;
			distanceToBarEdge = -((barPos - barHeight / 2) - (int) ball.getY());
		}
		if (0 <= distanceToBarEdge
				&& distanceToBarEdge < allowedDistanceToBarEdge) {
			double percentage = distanceToBarEdge
					* (100 / allowedDistanceToBarEdge) / 100.0;
			y = (double) (x - (x * percentage));// fpsUpdateRatio));
			if (ballAboveCenterOfBar) {
				y = -y;
			}
			if (barIsLEFT) {
				y = -y;
			}
		}
		return y;
	}

	private void victor() {
		if (messageToWindow != 0) {
			pause(3);
			leftBarPos = halfWindowHeight;
			rightBarPos = halfWindowHeight;
		}
		messageToWindow = 0;
		if (scoreLEFT == scoreToWinAGame) {
			messageToWindow = 1;
			wonGamesLEFT++;
			reset();
		}
		if (scoreRIGHT == scoreToWinAGame) {
			messageToWindow = 2;
			wonGamesRIGHT++;
			reset();
		}
	}
	

	private void reset() {
		leftBarHeight = initalLeftBarHeight;
		rightBarHeight = initalRightBarHeight;
		scoreLEFT = 0;
		scoreRIGHT = 0;
		currentcomputeIsANewGame = true;
	}
	

	private void pause(int pauseTime) {
		gameWasPaused = true;
		try {
			TimeUnit.SECONDS.sleep(pauseTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getBarPos(BarKey k) {
		switch (k) {
		case LEFT:
			return leftBarPos;
		case RIGHT:
			return rightBarPos;
		default:
			return -1;
		}

	}

	public int getBarHeight(BarKey k) {
		switch (k) {
		case LEFT:
			return leftBarHeight;
		case RIGHT:
			return rightBarHeight;
		default:
			return -1;
		}
	}

	public Point getBallPos() {
		Point intBall = new Point();
		intBall.x = (int) ball.getX();
		intBall.y = (int) ball.getY();
		return intBall;
	}

	public String getMessage() {
		String message = "";
		switch (messageToWindow) {
		case 1:
			message = "---------------------- " + nameLEFT
					+ " is victorious ----------------------";

			break;
		case 2:
			message = "---------------------- " + nameRIGHT
					+ " is victorious ----------------------";

			break;
		default:
			message = wonGamesLEFT + " : " + wonGamesRIGHT;
		}
		return message;
	}

	public String getScore(BarKey k) {
		switch (k) {
		case LEFT:
			return Integer.toString(scoreLEFT);
		case RIGHT:
			return Integer.toString(scoreRIGHT);
		default:
			return "";
		}
	}

	public Dimension getFieldSize() {
		return this.dimension;
	}
}
