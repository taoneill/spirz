package com.team3.socialnews.server.persistence;

import static org.mockito.Mockito.*;
import net.sf.jsr107cache.Cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.team3.socialnews.server.vote.LinkPredator;
import com.team3.socialnews.shared.model.Link;
import com.team3.socialnews.test.LocalServiceTest;


public class LinkRepositoryImplTest extends LocalServiceTest {
	
	LinkRepository linkRepo;
	SpirzCache mockCache;
	Cache mockCacheInstance;
	LinkPredator mockPredator;
	@Before
	public void setUp() throws Exception {
		super.setUp();
		mockCache = mock(SpirzCache.class);
		mockCacheInstance = mock(Cache.class);
		when(mockCache.getInstance()).thenReturn(mockCacheInstance);
		mockPredator = mock(LinkPredator.class);
		
		
		this.linkRepo = new LinkRepositoryImpl(mockCache, mockPredator);
	}
	
	@Test
	public void testSubmit() {
		final String URL = "http://www.example.com",
			NAME = "My Link",
			USER_ID = "test",
			USER_NICK = "test";
		Link link = linkRepo.submit(NAME, URL, USER_ID, USER_NICK);
		Assert.assertEquals((Integer)0, link.getVoteTotal());
		Assert.assertEquals(URL, link.getUrl());
		Assert.assertEquals(USER_NICK, link.getSubmitterId());
		Assert.assertEquals(USER_ID, link.getSubmitterId());
		Assert.assertEquals(NAME, link.getTitle());
		
		link = linkRepo.get(link.getId());
		Assert.assertEquals((Integer)0, link.getVoteTotal());
		Assert.assertEquals(URL, link.getUrl());
		Assert.assertEquals(USER_NICK, link.getSubmitterId());
		Assert.assertEquals(USER_ID, link.getSubmitterId());
		Assert.assertEquals(NAME, link.getTitle());
	}
	
//	@Test
//	public void testThatGetLooksInTheCacheFirst(){
//		final String URL = "http://www.example.com",
//		NAME = "My Link",
//		USER_ID = "test",
//		USER_NICK = "test";
//		Link link = linkRepo.submit(NAME, URL, USER_ID, USER_NICK);
//		
//		linkRepo.get(link.getId());
//		
//		verify(mockCache, times(2)).getInstance();
//		verify(mockCacheInstance).containsKey(link.getId());
//	}
}
