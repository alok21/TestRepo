package org.dstadler.jgit.unfinished;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;

/**
 * Simple snippet which shows how to use RevWalk to iterate over items in a file-tree
 *
 * @author dominik.stadler@gmx.at
 */
public class WalkTreeNonRecursive {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();
		
		// find the HEAD
		FS fs = repository.getFS();
		File userHome = fs.userHome();
		StoredConfig config = repository.getConfig();
		String name = config.getString("user", null, "name");
		String email = config.getString("user", null, "email");
		String url = config.getString("remote", "origin", "url");
		Set<String> sections = config.getSections();
		Iterator<String> iterator = sections.iterator();
		while (iterator.hasNext())
		{
			String string = (String)iterator.next();
			
		}
		/*File workTree = new File("https://github.com/github/testrepo.git");
		Repository repository = new FileRepositoryBuilder().setWorkTree(workTree).build();

		Git git = new Git(repository);
		ObjectId head1 = repository.resolve(Constants.HEAD);
		// All commits, use setMaxCount(1) for newest, use addPath for filtering to path
		Iterable<RevCommit> commits = git.log().add(head1).call();*/
				
		Ref head = repository.getRef("HEAD");

		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk walk = new RevWalk(repository);

		RevCommit commit = walk.parseCommit(head.getObjectId());
		RevTree tree = commit.getTree();
		System.out.println("Having tree: " + tree);

		// now use a TreeWalk to iterate over all files in the Tree recursively
		// you can set Filters to narrow down the results if needed
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(false);
		while(treeWalk.next()) {
			System.out.println("-------------------------------------------");
			System.out.println("Folder Path: " + treeWalk.getPathString());
			System.out.println("Folder Name: " + treeWalk.getNameString());
			System.out.println("Folder depth: " + treeWalk.getDepth());
			System.out.println("Folder Tree Count: " + treeWalk.getTreeCount());
			//ObjectReader objectReader = treeWalk.getObjectReader();
		}


		repository.close();
	}
}
