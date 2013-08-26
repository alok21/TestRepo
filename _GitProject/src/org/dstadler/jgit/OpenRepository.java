package org.dstadler.jgit;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to open an existing repository
 *
 * @author dominik.stadler@gmx.at
 */
public class OpenRepository {
	public static void main(String[] args) throws IOException, NoHeadException, GitAPIException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File("C:\\Users\\kishore\\git\\JavaRepos\\.git"))
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();

		System.out.println("Having repository: " + repository.getDirectory());

		ObjectId head = repository.resolve("HEAD");
		String name = head.getName();
		
		Ref HEAD = repository.getRef("refs/heads/master");
		RevWalk walk = new RevWalk(repository);
		RevTree tree = walk.parseTree(head);
		
		Git git = new Git(repository);
		Iterable<RevCommit> log = git.log().call();
		
		Iterator<RevCommit> iterator = log.iterator();
		while (iterator.hasNext())
		{
			RevCommit revCommit = (RevCommit)iterator.next();
			System.out.println(revCommit.getName());
		}
		
		PlotWalk plotWalk = new PlotWalk(repository);
		ObjectId rootId = repository.resolve("HEAD");
		RevCommit revCommit = plotWalk.parseCommit(rootId);
		plotWalk.markStart(revCommit);
		PlotCommitList<PlotLane> plotCommitList = new PlotCommitList<PlotLane>();
		plotCommitList.source(plotWalk);
		plotCommitList.fillTo(Integer.MAX_VALUE);
		
		repository.close();
	}
}
