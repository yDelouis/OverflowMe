package fr.ydelouis.overflowme.common;

import java.util.List;

public interface DataProvider<T>
{
	public List<T> loadPage(int pageNumber);
}
