package com.belatrix.scrapingWeb.model;

import java.util.ArrayList;
import java.util.List;

/** 
 * Class to manage the info of the pages found 
 * in the resource file
 * @author Enrique De Lavalle 
 *
 */
public class Page {
	
	//attributes of Page class 
	private String url; 
	private String content; 
	private List<String> ref = new ArrayList<String>();
	
	
	/**
	 * Default constructor
	 */
	public Page() {		
	}

	/**
	 * Constructor with url parameter 
	 * @param url
	 */
	public Page(String url) {		
		this.url = url;
	}

	/**
	 * Constructor with all attributes
	 * @param url
	 * @param content
	 * @param ref
	 */
	public Page(String url, String content, List<String> ref) {
		this.url = url;
		this.content = content;
		this.ref = ref;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getRef() {
		return ref;
	}

	public void setRef(List<String> ref) {
		this.ref = ref;
	}
	
	
}
