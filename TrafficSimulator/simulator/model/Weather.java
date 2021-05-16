package simulator.model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public Image getImage()
	{
		if(this == SUNNY) return loadImage("sun.png");
		else if(this == CLOUDY) return loadImage("cloud.png");
		else if(this == RAINY) return loadImage("rain.png");
		else if(this==WINDY) return loadImage("wind.png");
		else return loadImage("storm.png");
	}
}
