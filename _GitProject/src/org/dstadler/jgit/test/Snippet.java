package org.dstadler.jgit.test;

import static org.eclipse.jgit.lib.Constants.R_HEADS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Iterator;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.jgit.lib.TreeEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.RepositoryUtils;

public class Snippet extends RepositoryUtils
{
	static Repository repo = CookbookHelper.openJGitCookbookRepository();
	
	public static void main(String[] args)
	{
		Collection<Ref> refs = RepositoryUtils.getRefs(repo, org.eclipse.jgit.lib.Constants.R_REFS);
		RepositoryUtils.getBranches(repo);
		
		RepositoryUtils.getTags(repo);
		Iterator<Ref> iterator = refs.iterator();
		while (iterator.hasNext())
		{
			Ref ref = (Ref)iterator.next();
			System.out.println(ref.getName());
			System.out.println(ref.getObjectId().getName());
			System.out.println(ref.getStorage().name());
			System.out.println(ref.getTarget().getName());
		}
	}
	
	public void checkout(String file, String toDir) throws IOException
	{
	
		RevWalk rw = new RevWalk(repo);
		ObjectId objHead = repo.resolve("HEAD");
		RevCommit commitHead = rw.parseCommit(objHead);
	
		/*Tree tree = repo.ree(commitHead.getTree());
		TreeEntry fileTreeEntry = getFile(file.split("/"), 0, tree);
	
		ObjectLoader loader = repo.open(fileTreeEntry.getId());
	
		// Write content
		byte[] bytes = loader.getBytes();
		File outFile = new File(toDir + "/" + fileTreeEntry.getName());
	
		FileChannel channel = new FileOutputStream(outFile).getChannel();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		channel.write(buffer);
		channel.close();*/
	}
	
	private TreeEntry getFile(String[] path, int pathPos, Tree tree)
			throws IOException
	{
		if (path.length == 0 && pathPos > path.length)
		{
			return null;
		}
	
		boolean isFile = path.length == pathPos + 1;
	
		for (TreeEntry entry : tree.members())
		{
			if (path[pathPos].equals(entry.getName()))
			{
				if (isFile)
				{
					return entry;
				}
				else
				{
					//return getFile(path, ++pathPos, repo.mapTree(entry.getId()));
				}
			}
		}
		return null;
	}
}
