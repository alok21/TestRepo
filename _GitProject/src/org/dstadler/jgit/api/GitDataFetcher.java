package org.dstadler.jgit.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.github.core.event.Event;
import org.eclipse.egit.ui.internal.repository.tree.command.SynchronizeCommand;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.ReflogCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;



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
			UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider("alok21", "gittest");
			provider.setDefault(provider);
			Repository repository = Git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath).call().getRepository();
			Git git = new Git(repository);
			Ref head = repository.getRef("HEAD");
			RevWalk walk = new RevWalk(repository);
			RevCommit commit = walk.parseCommit(head.getObjectId());
			RevTree tree = commit.getTree();
		
			System.out.println("Having tree: " + tree);
			TreeWalk treeWalk1 = new TreeWalk(repository);
			treeWalk1.addTree(tree);
			treeWalk1.setRecursive(true);
			
			ObjectId fromString = ObjectId.fromString("98480f49edfecdb99a8e77c8847eb2fa9a666f03");
			RevCommit commit1 = walk.parseCommit(fromString);
			
			tree = commit1.getTree();
			TreeWalk treeWalk2 = new TreeWalk(repository);
			treeWalk2.addTree(tree);
			treeWalk2.setRecursive(true);
			
			TreeWalk scanTree = treeWalk1;
			TreeWalk compareTree = treeWalk2;

			
			List<String> fileNames = new ArrayList<String>();
			List<String> addedFiles = new ArrayList<String>();
			List<String> deletedFiles = new ArrayList<String>();
			List<String> modifiedFiles = new ArrayList<String>();
			Map<String,String> versionId = new HashMap<String,String>();
			
			while(compareTree.next())
			{
				String fileName = compareTree.getNameString();
				versionId.put(fileName, compareTree.getObjectId(0).toString());
				fileNames.add(fileName);
			}
			while (scanTree.next())
			{
				String nameString = scanTree.getNameString();
				if(fileNames.contains(nameString))
				{
					String oldId = versionId.get(nameString);
					if(!oldId.equals(scanTree.getObjectId(0).toString()))
						modifiedFiles.add(nameString);	
				}
				else {
					addedFiles.add(nameString);
				}		
			}
			for(int i = 0;i<addedFiles.size();i++)
			{
				System.out.println("Added Files: " + addedFiles.get(i));
			}
			for(int i = 0;i<modifiedFiles.size();i++)
			{
				System.out.println("Modified Files: " + modifiedFiles.get(i));
			}
			localPath.delete();
			
//			File workTree = new File("E:\\GitRepo\\GitRepo");
//			Repository repo = new FileRepositoryBuilder().setWorkTree(workTree).build();
//			FileTreeIterator iterator = new FileTreeIterator(repo);
//			Status status = git.status().setWorkingTreeIt(iterator).call();
//			
//			for (String added:status.getUntracked())
//				System.out.println("Added file: "+added);
//			for (String modified:status.getModified())
//				System.out.println("Modified file: "+modified);	
//			for (String modified:status.getMissing())
//				System.out.println("Deleted file: "+ modified);
				
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		//int ch = git.status().call().getChanged().size();
	}
	
	private TreeWalk getTreeWalk(Repository repository) throws IOException
	{
		Ref head = repository.getRef("HEAD");
		RevWalk walk = new RevWalk(repository);
		RevCommit commit = walk.parseCommit(head.getObjectId());
		RevTree tree = commit.getTree();
	
		System.out.println("Having tree: " + tree);
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		return treeWalk;
	}
}
