package mx.com.intx.responses;

import java.util.List;

public class SearchResponse extends ResponseWS{
	
	// Objetos particulares
	private List<?> objects;
	private Long    total;
	private Integer offset;
	private Integer limit;
	private Integer totalPages;
	
	public List<?> getObjects() {
		return objects;
	}
	public void setObjects(List<?> objects) {
		this.objects = objects;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
}
