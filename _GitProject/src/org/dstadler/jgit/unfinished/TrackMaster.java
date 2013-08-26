package org.dstadler.jgit.unfinished;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


/**
 * Simple snippet which shows how to clone a repository from a remote source
 *
 * @author dominik.stadler@gmx.at
 */
public class TrackMaster {
	//private static final String REMOTE_URL = "https://github.com/github/testrepo.git";
	private static final String REMOTE_URL = "C:/Users/kishore/git/JavaRepos/.git";

	public static void main(String[] args) throws Exception
	{
		// prepare a new folder for the cloned repository
		File localPath = File.createTempFile("TestGitRepository", "");
		localPath.delete();

		// then clone
		System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
		Git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath).call();

        // now open the created repository
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(localPath).readEnvironment().findGitDir().build();

		Git git = new Git(repository);

		/*git.branchCreate()
	        .setName("master")
	        // ?!? .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
	        .setStartPoint("origin/master")
	        .setForce(true)
	        .call();*/

        System.out.println("Now tracking master in repository at " + repository.getDirectory() + " from origin/master at " + REMOTE_URL);

        repository.close();

        localPath.delete();
	}
}
