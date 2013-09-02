package org.dstadler.jgit.unfinished;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Simple snippet which shows how to use RevWalk to iterate over items in a file-tree
 *
 * @author dominik.stadler@gmx.at
 */
public class WalkTreeRecursive {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		//File gitDir = new File("C:/Users/kishore/git/JavaRepos/.git");
		//Repository repository = new FileRepository(gitDir);

		Ref head = repository.getRef("HEAD");
		
		RevWalk walk = new RevWalk(repository);

		ObjectId fromString = ObjectId.fromString("65ccdf5b749f5684b81aebe13298641dc53ff8df");
		//printFile(walk, repository, fromString);

		ObjectId fromString1 = ObjectId.fromString("741026a786f6311ae5d278d4f7c0ff559d1bc673");
		//printFile(walk, repository, fromString1);

		printFile(walk, repository, head.getObjectId());

		repository.close();
	}
	
	private static void printFile(RevWalk walk, Repository repository, AnyObjectId id)
	{
		try
		{
			RevCommit commit = walk.parseCommit(id);
			RevTree tree = commit.getTree();
			System.out.println("Having tree: " + tree);

			TreeWalk treeWalk = new TreeWalk(repository);
			treeWalk.addTree(tree);
			treeWalk.setRecursive(true);
			while (treeWalk.next())
			{
				String pathString = treeWalk.getPathString();
				
				if (!pathString.endsWith("WalkTreeRecursive.java"))
					continue;
			
				System.out
						.println("-------------------------------------------");
				System.out.println("Folder Path: " + pathString);
				System.out.println("Folder Name: " + treeWalk.getNameString());
				System.out.println("Folder depth: " + treeWalk.getDepth());
				System.out.println("Folder Tree Count: "
						+ treeWalk.getTreeCount());
				
				ObjectId objectId = treeWalk.getObjectId(0);
				ObjectLoader loader = repository.open(objectId);
				InputStream in = loader.openStream();
				// and then one can the loader to read the file
				loader.copyTo(System.out);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
