package com.axwayaustralia.springmicro.data.horse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum HorseStoreEnum {

	INSTANCE;
	
	private Map<String, Horse> mapHorses;
	private boolean isInitiated = false;
	private boolean raceStarted = false;
	private boolean raceFinished = false;
	private long raceDistance = 100;
	private int horsesFinished = 0;
	
	public Map<String, Horse> getMapHorses() {
		if (mapHorses == null) {
			mapHorses = new LinkedHashMap<String, Horse>();
		}
		return mapHorses;
	}
	
	public void addHorse(Horse horse) {
		if (mapHorses == null) {
			mapHorses = new LinkedHashMap<String, Horse>();
		}
		mapHorses.put(horse.getNumber(), horse);
	}
	
	public void clearHorses() {
		if (mapHorses != null) {
			mapHorses.clear();
		}
	}
	
	public List<Horse> getHorsesAsList() {
		if (mapHorses != null) {
			return mapHorses.values().stream().collect(Collectors.toList());
		} else {
			return null;
		}
	}
	
	public List<Horse> getSortedUnfinishedHorses() {
		if (mapHorses != null) {
			List<Horse> lsthorses = new ArrayList<Horse>();
			for (Horse thisHorse : getMapHorses().values()) {
				if (thisHorse.isHasFinished() == false) {
					lsthorses.add(thisHorse);
				}
			}
			lsthorses.sort(Comparator.comparing(Horse::getDistanceTravelled).reversed());
			return lsthorses;
		} else {
			return null;
		}
	}

	/**
	 * @return the isInitiated
	 */
	public boolean isInitiated() {
		return isInitiated;
	}

	/**
	 * @param isInitiated the isInitiated to set
	 */
	public void setInitiated(boolean isInitiated) {
		this.isInitiated = isInitiated;
	}
	
	/**
	 * @return the raceStarted
	 */
	public boolean isRaceStarted() {
		return raceStarted;
	}

	/**
	 * @param raceStarted the raceStarted to set
	 */
	public void setRaceStarted(boolean raceStarted) {
		this.raceStarted = raceStarted;
	}

	/**
	 * @return the raceDistance
	 */
	public long getRaceDistance() {
		return raceDistance;
	}

	/**
	 * @param raceDistance the raceDistance to set
	 */
	public void setRaceDistance(long raceDistance) {
		this.raceDistance = raceDistance;
	}

	/**
	 * @return the raceFinished
	 */
	public boolean isRaceFinished() {
		return raceFinished;
	}

	/**
	 * @param raceFinished the raceFinished to set
	 */
	public void setRaceFinished(boolean raceFinished) {
		this.raceFinished = raceFinished;
	}

	/**
	 * @return the horsesFinished
	 */
	public int getHorsesFinished() {
		return horsesFinished;
	}

	/**
	 * @param horsesFinished the horsesFinished to set
	 */
	public void setHorsesFinished(int horsesFinished) {
		this.horsesFinished = horsesFinished;
	}

}
