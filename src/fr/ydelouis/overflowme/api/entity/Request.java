package fr.ydelouis.overflowme.api.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public abstract class Request<T>
{
	private List<T>	items;
	@SerializedName("quota_remaining")
	private int quotaRemaining;
	@SerializedName("quota_max")
	private int quotaMax;
	@SerializedName("has_more")
	private boolean hasMore;
	private int total;

	public List<T> getItems() {
		return items;
	}
	
	public int getQuotaRemaining() {
		return quotaRemaining;
	}

	public int getQuotaMax() {
		return quotaMax;
	}

	public boolean hasMore() {
		return hasMore;
	}

	public int getTotal() {
		return total;
	}
}
