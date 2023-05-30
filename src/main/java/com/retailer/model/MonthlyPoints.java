package com.retailer.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class MonthlyPoints {

	@EmbeddedId
	private MonthlyPointsKey monthlyPointsKey;

	private int monthlyPoints;

	public MonthlyPoints() {
	}

	public MonthlyPoints(MonthlyPointsKey monthlyPointsKey, int monthlyPoints) {
		this.monthlyPointsKey = monthlyPointsKey;
		this.monthlyPoints = monthlyPoints;
	}

}
