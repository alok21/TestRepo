package org.dstadler.jgit.api;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;



public class GitDataFetcher {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws GitAPIException 
	 * @throws NoWorkTreeException 
	 */
	private static final String REMOTE_URL = "https://bitbucket.org/alok21/testremoterep";
	public static void main(String[] args){
		try
		{
			File localPath = File.createTempFile("TestGitRepository", "");
			localPath.delete();
			//Repository repository = CookbookHelper.openJGitCookbookRepository();
			// then clone
			System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
			UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider("alok21", "gittest");
			provider.setDefault(provider);
			Repository repository = Git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath).call().getRepository();
			Ref head = repository.getRef("HEAD");
			System.out.println("Head Id: " + head.getObjectId());
			Git git = new Git(repository);
			Status status = git.status().call();
			for (String added:status.getUntracked())
				System.out.println("Added file: "+added);
			for (String deleted:status.getRemoved())
				System.out.println("Deleted file: "+deleted);
			for (String modified:status.getModified())
				System.out.println("Modified file: "+modified);		
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		//int ch = git.status().call().getChanged().size();
	}
}
