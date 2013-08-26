package org.dstadler.jgit.api;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.treewalk.filter.PathFilter;

/**
 * Snippet which shows how to use RevWalk and TreeWalk to read the contents 
 * of a specific file from a specific commit.
 *
 * @author dominik.stadler@gmx.at
 */
public class ReadFileFromCommit {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		// find the HEAD
		ObjectId lastCommitId = repository.resolve(Constants.HEAD);
		
		//ObjectId parent = repository.resolve("HEAD^");
       // ObjectId tree1 = repository.resolve("HEAD^{tree}");
        //System.out.println("HEAD has parent " + parent.name() + " and tree " + tree1.name());


		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk revWalk = new RevWalk(repository);
		RevCommit commit = revWalk.parseCommit(lastCommitId);
		// and using commit's tree find the path
		RevTree tree = commit.getTree();
		System.out.println("Having tree: " + tree);

		TreeWalk forPath = TreeWalk.forPath(repository, "C:/Users/kishore/git/JavaRepos/_GitProject/src/org/dstadler/jgit/porcelain/AddFile.java.java", tree);
		//WorkingTreeIterator.initRootIterator(repository);
		FileTreeIterator fileTreeIterator = new FileTreeIterator(repository);
		WorkingTreeOptions options = fileTreeIterator.getOptions();
		String name = options.getCheckStat().name();
		
		// now try to find a specific file
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		PathFilter create = PathFilter.create("ReadFileFromCommit.java");
		String path = create.getPath();
		treeWalk.setFilter(create);
		if (!treeWalk.next()) {
		  throw new IllegalStateException("Did not find expected file 'ReadFileFromCommit.java'");
		}

		ObjectId objectId = treeWalk.getObjectId(0);
		ObjectLoader loader = repository.open(objectId);

		// and then one can the loader to read the file
		loader.copyTo(System.out);

		repository.close();
	}
}
