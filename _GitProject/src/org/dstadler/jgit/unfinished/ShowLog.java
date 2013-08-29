package org.dstadler.jgit.unfinished;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;



/**
 * Simple snippet which shows how to list log entries
 *
 * @author dominik.stadler@gmx.at
 */
public class ShowLog {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		Iterable<RevCommit> logs = new Git(repository).log()
			.all()
			.call();
//		for(RevCommit rev : logs) {
//			System.out.println("Commit: " + rev + " " + rev.getName() + " " + rev.getId().getName() + rev.getType());
//			RevTree tree = rev.getTree();
//			String name = tree.getName();
//		
//			
//		}

		repository.close();
	}
}
