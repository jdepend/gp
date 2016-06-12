package com.rofine.gp.domain.organization.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rofine.gp.domain.organization.target.scheme.Scheme;
import com.rofine.gp.domain.organization.target.scheme.SchemeDomainService;
import com.rofine.gp.domain.organization.target.scheme.SchemeObject;

@Service
@Transactional(rollbackFor = Exception.class)
public class UnitScoreService {

	@Autowired
	private SchemeDomainService schemeDomainService;

	@Autowired
	private UnitScoreRepo unitScoreRepo;

	public Map<String, Float> getScores(int year) {
		Map<String, Float> scores = new HashMap<String, Float>();
		
		List<UnitScore> unitScores = unitScoreRepo.findByYear(year);
		for(UnitScore unitScore : unitScores){
			scores.put(unitScore.getUnitId(), unitScore.getScore());
		}

		return scores;

	}

	public void create(int year) {
		
		List<Scheme> schemes = schemeDomainService.listScheme(year);

		Map<String, Float> scores = new HashMap<String, Float>();
		Float score;
		for (Scheme scheme : schemes) {
			for (SchemeObject schemeObject : scheme.getSchemeObjects()) {
				score = scores.get(schemeObject.getObjectId());
				if (score == null) {
					scores.put(schemeObject.getObjectId(), schemeObject.getScore());
				} else {
					score += schemeObject.getScore();
					scores.put(schemeObject.getObjectId(), score);
				}
			}
		}
		
		List<UnitScore> unitScores = new ArrayList<UnitScore>();
		UnitScore unitScore;
		for(String unitId : scores.keySet()){
			unitScore = new UnitScore();
			unitScore.setUnitId(unitId);
			unitScore.setScore(scores.get(unitId));
			unitScore.setYear(year);
			
			unitScores.add(unitScore);
		}
		
		unitScoreRepo.deleteByYear(year);
		unitScoreRepo.save(unitScores);

	}

}
