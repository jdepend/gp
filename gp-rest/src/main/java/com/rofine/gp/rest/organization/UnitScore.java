package com.rofine.gp.rest.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rofine.gp.platform.entity.IdEntity;

@Entity
@Table(name = "unit_score")
public class UnitScore extends IdEntity {

	@Column(name = "unit_id")
	private String unitId;

	@Column(name = "unit_type")
	private String unitType;

	private int year;

	private Float score;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
