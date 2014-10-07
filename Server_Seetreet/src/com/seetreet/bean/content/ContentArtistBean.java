package com.seetreet.bean.content;

import com.seetreet.bean.GenreBean;

public class ContentArtistBean extends ContentProviderBean{
	public static final String KEY_ARTIST 	= "content_artist";
	private String artistId = null;
	public ContentArtistBean(String title, GenreBean genre, int type,
			int start, int end, String providerId , String artistId) {
		super(title, genre, type, start, end, providerId);
		// TODO Auto-generated constructor stub
		this.artistId = artistId;
	}
	public String getArtistId() {
		return artistId;
	}
}
