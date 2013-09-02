package org.dstadler.jgit.porcelain;

import java.io.File;
import java.util.Collection;

import org.dstadler.jgit.unfinished.PullRemoteRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gitective.core.CommitFinder;
import org.gitective.core.RepositoryService;



/**
 * Simple snippet which shows how to clone a repository from a remote source
 *
 * @author dominik.stadler@gmx.at
 */
public class CloneRemoteRepository {
	private static final String REMOTE_URL = "https://bitbucket.org/alok21/testgitrep";

	public static void main(String[] args){
		try
		{
			
			File localPath = File.createTempFile("TestGitRepository", "");
			localPath.delete();
//			//Repository repository = CookbookHelper.openJGitCookbookRepository();
//			// then clone
//			System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
//			JGitText jgitText = JGitText.get();
//			jgitText.credentialUsername = "alok21";
//			jgitText.credentialPassword = "gittest";
//			UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(jgitText.credentialUsername, jgitText.credentialPassword );
//			provider.setDefault(provider);
//			CloneCommand cloneCmd = Git.cloneRepository();
//			cloneCmd.setCloneAllBranches(true);
//			Repository repository = cloneCmd.setURI(REMOTE_URL).setDirectory(localPath).call().getRepository();
			//Git.lsRemoteRepository().setRemote("").setHeads(true).call();
			
			// now open the created repository
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			Repository repository = openJGitRepository(REMOTE_URL, "alok21", "gittest");
			LsRemoteCommand cmd = new LsRemoteCommand(repository);
			Collection<Ref> refs = cmd.setRemote(REMOTE_URL).setHeads(true).call();
			
			for(Ref ref : refs) {
				if(ref.getName().equals("refs/heads/master"))
					System.out.println("Head: " + ref.getObjectId());
			}
			URIish urish = new URIish(REMOTE_URL);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static Repository openJGitRepository(String repPath,
			String userName, String password) throws Exception
	{
		File dir = new File(repPath);

		setCredentialsProvider(userName, password);
		// now open the created repository
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository =
			builder.setGitDir(dir).readEnvironment() // scan environment GIT_* variables
					.findGitDir() // scan up the file system tree
					.build();


		return repository;
	}
	
	private static void setCredentialsProvider(String userName, String password)
	{
		if (userName != null && password != null)
		{
			CredentialsProvider provider =
					new UsernamePasswordCredentialsProvider(userName,
							password);
			UsernamePasswordCredentialsProvider.setDefault(provider);
		}
	}

}
