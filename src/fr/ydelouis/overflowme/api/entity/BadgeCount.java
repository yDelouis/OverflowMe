package fr.ydelouis.overflowme.api.entity;

import java.io.Serializable;

public class BadgeCount implements Serializable
{
	private static final long	serialVersionUID	= -3391919510832282642L;
	
	private int	bronze;
	private int	silver;
	private int	gold;

	public int getBronze() {
		return bronze;
	}

	public void setBronze(int bronze) {
		this.bronze = bronze;
	}

	public int getSilver() {
		return silver;
	}

	public void setSilver(int silver) {
		this.silver = silver;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getTotal() {
		return gold+silver+bronze;
	}
}
