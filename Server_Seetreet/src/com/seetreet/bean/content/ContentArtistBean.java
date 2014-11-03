package com.seetreet.bean.content;

import com.seetreet.bean.ArtistBean;
import com.seetreet.bean.GenreBean;
import com.seetreet.bean.ProviderBean;

public class ContentArtistBean extends ContentProviderBean{
	public static final String KEY_ARTIST 	= "artists";
	private ArtistBean[] artist = null;
	public ContentArtistBean(String title, GenreBean genre, String type,
			int start, int end, ProviderBean provider , ArtistBean[] artist) {
		super(title, genre, type, start, end, provider);
		// TODO Auto-generated constructor stub
		this.artist = artist;
	}
	public ArtistBean[] getArtist() {
			return artist;
	}
}
