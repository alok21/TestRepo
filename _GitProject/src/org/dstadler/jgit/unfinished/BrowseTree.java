package org.dstadler.jgit.unfinished;

import java.io.File;
import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Simple snippet which shows how to use RevWalk to iterate over items in a file-tree
 *
 * @author dominik.stadler@gmx.at
 */
public class BrowseTree {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		ObjectId revId = repository.resolve(Constants.HEAD);
		TreeWalk treeWalk = new TreeWalk(repository);

		RevWalk revWalk = new RevWalk(repository);
		RevTree parseTree = revWalk.parseTree(revId);
		treeWalk.addTree(parseTree);
		treeWalk.setRecursive(true);

		while (treeWalk.next())
		{
			System.out.println("---------------------------");
			System.out.append("name: ").println(treeWalk.getNameString());
			System.out.append("path: ").println(treeWalk.getPathString());
			//System.out.println(treeWalk.getObjectReader());

			ObjectLoader loader = repository.open(treeWalk.getObjectId(0));

			System.err.append("directory: ").println(loader.getType() == Constants.OBJ_TREE);
			System.out.append("size: ").println(loader.getSize());
		}

		System.err.println("-------------------------");
		File directory = repository.getDirectory();
		System.err.println(directory.getAbsolutePath());
		
		/*Git git = new Git(repository);
		CheckoutCommand checkout = git.checkout();
		Ref call = checkout.call();*/

		repository.close();
	}
}
