package com.rofine.gp.domain.organization.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UnitScoreService {

	@Autowired
	private UnitScoreRepo unitScoreRepo;

	@Autowired
	private List<UnitScoreLoader> loaders;

	public Map<String, Float> getScores(int year) {
		Map<String, Float> scores = new HashMap<String, Float>();

		List<UnitScore> unitScores = unitScoreRepo.findByYear(year);
		for (UnitScore unitScore : unitScores) {
			scores.put(unitScore.getUnitId(), unitScore.getScore());
		}

		return scores;

	}

	public void create(int year) {

		unitScoreRepo.deleteByYear(year);

		for (UnitScoreLoader loader : loaders) {
			Map<String, Float> scores = loader.calScore(year);

			List<UnitScore> unitScores = new ArrayList<UnitScore>();
			UnitScore unitScore;
			for (String unitId : scores.keySet()) {
				unitScore = new UnitScore();
				unitScore.setUnitId(unitId);
				unitScore.setScore(scores.get(unitId));
				unitScore.setYear(year);
				unitScore.setSource(loader.getSource());

				unitScores.add(unitScore);
			}

			unitScoreRepo.save(unitScores);
		}
	}

}
