package com.bincy.simplegitreadergh;

import org.kohsuke.github.GHRepository;


/**
 * Main application to run GitReader
 *
 */
public class App 
{
	public static void main( String[] args )
	{
		GitReader gitReader = new GitReader();
		gitReader.connect("bincy987", "april16");
		GHRepository repo = gitReader.getRepo("ruby/ruby");
		gitReader.populateBranchData(repo);
		gitReader.populateTagData(repo);
		gitReader.printBranches();
		gitReader.printTags();
		gitReader.printBranchesWithNoTravis();
		gitReader.printTagsWithNoTravis();

	}
}
