package org.dstadler.jgit.helper;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


public class CookbookHelper {

	public static Repository openJGitCookbookRepository() {
		/*FileRepositoryBuilder builder = new FileRepositoryBuilder();

		Repository repository = builder
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();*/

		File gitDir = new File("C:/Users/kishore/git/JavaRepos/.git");
//		File gitDir = new File("https://github.com/kishore74lko/jgit-cookbook.git");
		Repository repository = null;
		try
		{
			repository = new FileRepository(gitDir);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return repository;
	}

	public static Repository createNewRepository() throws IOException {
		// prepare a new folder
		File localPath = File.createTempFile("TestGitRepository", "");
		localPath.delete();

		// create the directory
        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create();

        return repository;
	}
}
