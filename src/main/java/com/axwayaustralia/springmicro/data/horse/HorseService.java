package com.axwayaustralia.springmicro.data.horse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class HorseService {

	public void generateHorses() {
		HorseStoreEnum.INSTANCE.clearHorses();
		addHorse("1", "Cross Counter", "Kerrin McEvoy");
		addHorse("2", "Rekindling", "Corey Brown");
		addHorse("3", "Almandin", "Borey Crown");
		addHorse("4", "Prince of Penzance", "Michelle Payne");
		addHorse("5", "Protectionist", "Ryan Moore");
		addHorse("6", "Fiorente", "Damien Oliver");
		addHorse("7", "Green Moon", "Brett Pebble");
		addHorse("8", "Dunaden", "Christophe Lemaire");
		addHorse("9", "Americain", "Gerald Mosse");
		addHorse("10", "Shocking", "Bart Cummings");
		addHorse("11", "Viewed", "B Shinn");
		addHorse("12", "Efficient", "Michael Rodd");
		addHorse("13", "Delta Blues", "Yasunari Iwata");
		addHorse("14", "Makybe Diva", "Glen Boss");
		addHorse("15", "Media Puzzle", "Gai Waterhouse");
		
		HorseStoreEnum.INSTANCE.setInitiated(true); 
	}
	
	private void addHorse(String number, String name, String jockey) {
		Horse horse = new Horse(number, name, jockey);
		HorseStoreEnum.INSTANCE.addHorse(horse);
	}
	
	//sets starting price
	public void initiatePrices() {
		for (Horse horse : HorseStoreEnum.INSTANCE.getMapHorses().values()) {
			horse.setPrice(getRandomPriceAsString(1D, 100D));
		}
	}
	
	public void updatePrices() {
		Random rand = new Random();
	 
	    int numberHorsesToUpdate = rand.nextInt(HorseStoreEnum.INSTANCE.getMapHorses().size());
	 
	    for (int i = 0; i < numberHorsesToUpdate; i++) {
	        int randomIndex = rand.nextInt(HorseStoreEnum.INSTANCE.getMapHorses().size());
	        List<Horse> lstHorses = HorseStoreEnum.INSTANCE.getHorsesAsList();
	        Horse randomHorse = lstHorses.get(randomIndex);
	        
	        HorseStoreEnum.INSTANCE.getMapHorses()
	        .get(randomHorse.getNumber())
	        .setPrice(generateRandomNumber(randomHorse.getPrice()));
	    }
	}
	
	public void initiateRace() {
		if (HorseStoreEnum.INSTANCE.getMapHorses().values() == null || HorseStoreEnum.INSTANCE.getMapHorses().values().size() == 0) {
			generateHorses();
		}
		
		for (Horse thisHorse : HorseStoreEnum.INSTANCE.getMapHorses().values()) {
			thisHorse.setDistanceTravelled(0);
		}
		HorseStoreEnum.INSTANCE.setRaceStarted(true);
	}
	
	public void raceStep() {
		for (Horse thisHorse : HorseStoreEnum.INSTANCE.getMapHorses().values()) {
			if (thisHorse.isHasFinished() == false) {
				thisHorse.setDistanceTravelled(thisHorse.getDistanceTravelled() + getRandomLongValue(7, 21));
				if (thisHorse.getDistanceTravelled() >= HorseStoreEnum.INSTANCE.getRaceDistance()) {
					thisHorse.setHasFinished(true);
					HorseStoreEnum.INSTANCE.setHorsesFinished(HorseStoreEnum.INSTANCE.getHorsesFinished() + 1);
					thisHorse.setPosition(HorseStoreEnum.INSTANCE.getHorsesFinished());
				}
			}
		}
		setHorsePositions();

		if (isRaceFinished()) {
			HorseStoreEnum.INSTANCE.setRaceFinished(true);
		}
	}
	
	
	
	private void setHorsePositions() {
		List<Horse> lstHorses = HorseStoreEnum.INSTANCE.getSortedUnfinishedHorses();
		long numHorsesFinished = HorseStoreEnum.INSTANCE.getHorsesFinished();
		for (Horse thisHorse : lstHorses) { 
			numHorsesFinished++;
			HorseStoreEnum.INSTANCE.getMapHorses().get(thisHorse.getNumber()).setPosition(numHorsesFinished);
		}
	}
	
	private boolean isRaceFinished() {
		for (Horse thisHorse : HorseStoreEnum.INSTANCE.getMapHorses().values()) {
			if (thisHorse.isHasFinished() == false) {
				return false;
			}
		}
		return true;
	}
	
	private String generateRandomNumber(String originalValue) {
		Random rand = new Random();
		boolean randomBool = rand.nextBoolean();
		
		Double dblOriginalValue = Double.parseDouble(originalValue);
		Double newValue = Double.parseDouble(getRandomPriceAsString(1D, dblOriginalValue));
		newValue = newValue / 10; //minimise change
		if (randomBool == true) {
			//increase value
			newValue = dblOriginalValue + newValue;
		} else {
			//decrease value
			newValue = dblOriginalValue - newValue;
		}
		
		//check for negatives
		if (newValue > 0) {
			Double truncatedDouble = BigDecimal.valueOf(newValue)
				    .setScale(2, RoundingMode.HALF_UP)
				    .doubleValue();
			return Double.toString(truncatedDouble);
		} else {
			return originalValue;
		}
	}
	
	private long getRandomLongValue(long leftLimit, long rightLimit) {
		long generatedlong = leftLimit + (long)(Math.random() * (rightLimit - leftLimit));
		return generatedlong;
	}
	
	private String getRandomPriceAsString(double leftLimit, double rightLimit) {
		String value = "0.00"; 
		double generatedDouble = leftLimit + Math.random() * (rightLimit - leftLimit);
		Double truncatedDouble = BigDecimal.valueOf(generatedDouble)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		value = Double.toString(truncatedDouble);
		return value;
	} 
	
}
