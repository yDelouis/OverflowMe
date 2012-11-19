package fr.ydelouis.overflowme.api.entity;

import com.google.gson.annotations.SerializedName;

public class Site
{
	private String	name;
	@SerializedName("api_site_parameter")
	private String	apiSiteParameter;
	@SerializedName("site_url")
	private String	siteUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiSiteParameter() {
		return apiSiteParameter;
	}

	public void setApiSiteParameter(String apiSiteParameter) {
		this.apiSiteParameter = apiSiteParameter;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
}
