package com.rofine.gp.rest.organization;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UnitScoreRepo extends CrudRepository<UnitScore, String> {

	public int deleteByYear(int year);

	public List<UnitScore> findByYear(int year);

}
