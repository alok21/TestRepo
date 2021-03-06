package org.dstadler.jgit.porcelain;

import java.io.File;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to clone a repository from a remote source
 *
 * @author dominik.stadler@gmx.at
 */
public class CloneRemoteRepository {
	private static final String REMOTE_URL = "E:\\GitRepo\\GitRepo\\.git";

	public static void main(String[] args){
		try
		{
			// prepare a new folder for the cloned repository
			File localPath = File.createTempFile("TestGitRepository", "");
			localPath.delete();
			//Repository repository = CookbookHelper.openJGitCookbookRepository();
			
			System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
			
			Git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath).call();
			//Git.cloneRepository().setBranch(REMOTE_URL).setDirectory(localPath).call();
			// now open the created repository
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			Repository repository =
				builder.setGitDir(localPath).readEnvironment() // scan environment GIT_* variables
						.findGitDir() // scan up the file system tree
						.build();
//			StoredConfig config = repository.getConfig();
//			config.setBoolean("core", null , "sparsecheckout", true);
			
			System.out.println("Having repository: " + repository.getDirectory());

			repository.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
