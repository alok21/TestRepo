package org.dstadler.jgit.api;

import java.io.IOException;
import java.io.InputStream;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

public class GitLogFetcher {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();
		repository.getObjectDatabase();
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
