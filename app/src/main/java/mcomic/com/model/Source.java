package mcomic.com.model;

public enum Source {

	CENTRAL_DE_MANGAS("Central de Mangas","http://centraldemangas.org", "http://mangas.centraldemangas.com.br", "/mangas/search?q=");

	private String name;

	private String url;

	private String urlSubdomain;

	private String search;

	private Source(String name, String url,String urlSubdomain, String search){
		this.name = name;
		this.url = url;
		this.urlSubdomain = urlSubdomain;
		this.search = search;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	public String getSearch() {
		return search;
	}

	public String getUrlSubdomain() {
		return urlSubdomain;
	}
}
