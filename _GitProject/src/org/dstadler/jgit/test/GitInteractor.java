package org.dstadler.jgit.test;

import org.eclipse.jgit.lib.Repository;

public class GitInteractor
{
	/**
	 * All Git supported protocols i.e.
	 * <br><code>
	 * "http://", "https://", "git://", "ssh://", "file://"
	 * <code>
	 */
	public static final String PROTOCOLS[] =
	{ 
		"http://", "HTTP://", "https://", "HTTPS://", "git://", "GIT://",
		"ssh://", "SSH://", "file:///", "FILE:///"
	};
	
	private Repository repository;
	private String gitUrl;
	private String userName;
	private String password;
	private long latestRevision;
	private long endRevision;
	
	
}
