package com.axwayaustralia.springmicro.springmicro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axwayaustralia.springmicro.data.horse.Horse;
import com.axwayaustralia.springmicro.data.horse.HorseService;
import com.axwayaustralia.springmicro.data.horse.HorseStoreEnum;

@RestController
public class HorseRaceController {
	
	@Autowired
	private HorseService horseService;

	
	@GetMapping(path = "/api/horseprices")
	public ResponseEntity<List<Horse>> getHorsePriceStream() {
		
		if (HorseStoreEnum.INSTANCE.isRaceStarted() == false) {
			if (HorseStoreEnum.INSTANCE.isInitiated() == false) {
				horseService.generateHorses();
				horseService.initiatePrices();
			} else {
				horseService.updatePrices();
			}
		}

		List<Horse> lstHorses = HorseStoreEnum.INSTANCE.getHorsesAsList();

		return ResponseEntity.ok(lstHorses);
	}
	
	@GetMapping(path = "/api/horserace")
	public ResponseEntity<List<Horse>> getHorseRaceStream() {
		
		if (HorseStoreEnum.INSTANCE.isRaceStarted() == false) {
			horseService.initiateRace();
		} else if (HorseStoreEnum.INSTANCE.isRaceFinished() == false){
			horseService.raceStep();
		}
		
		List<Horse> lstHorses = HorseStoreEnum.INSTANCE.getHorsesAsList();

		return ResponseEntity.ok(lstHorses);
	
	}
	
	@GetMapping(path = "/api/horseReset")
	public ResponseEntity<String> getHorseReset() {
		
		HorseStoreEnum.INSTANCE.setRaceStarted(false);
		HorseStoreEnum.INSTANCE.setRaceFinished(false);
		HorseStoreEnum.INSTANCE.clearHorses();
		return ResponseEntity.ok("DONE!");
	}
	
	
	
}
