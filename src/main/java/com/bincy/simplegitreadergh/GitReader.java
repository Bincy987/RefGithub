package com.bincy.simplegitreadergh;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

/*
 * Class to read data from GitHub
 */
public class GitReader {

	/* reference to Git Hub */
	private GitHub gitHub = null;

	/* Branch details.Count is also maintained as additional information */
	private int branchCount   = 0;
	private int branchesWithNoTravisCount = 0;
	private ArrayList<String> fullBranchList = null;
	private ArrayList<String> branchesWithNoTravis = null;

	/* Tag details.Count is also maintained as additional information */
	private int tagCount = 0;
	private int tagsWithNoTravisCount = 0;
	private ArrayList<String> fullTagList = null;
	private ArrayList<String> tagsWithNoTravis = null;

	/*
	 * Connects to the Git hub
	 * @param username - GitHub username
	 * @param password - GitHub password
	 */
	public void connect(String username,String password)
	{

		if(username == null || password == null )
		{
			throw new IllegalArgumentException();
		}
		else
		{
			username = username.trim();
			password = password.trim();
			if(username.equals("") || password.equals(""))
			{
				throw new IllegalArgumentException();
			}
		}
		try {
			gitHub = GitHub.connectUsingPassword(username,password);
			
		} catch (IOException e) {
			System.out.println("IO exception!!");
		} 
	}

	/*
	 * get the Git repository
	 * @param repoName - repository name
	 * @return Repository reference
	 */
	public GHRepository getRepo(String repoName)
	{
		GHRepository repoRef = null;
		if(repoName == null)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			repoName = repoName.trim();
			if(repoName.equals(""))
			{
				throw new IllegalArgumentException();
			}
		}
		try {
			repoRef  = gitHub.getRepository(repoName);
		} catch (IOException e) {
			System.out.println("IO exception!!");
		} 
		return repoRef;
	}

	/*
	 * Populates the branchdata.
	 * connect() needs to be called before calling this function
	 */
	public void populateBranchData(GHRepository repo)
	{
		branchesWithNoTravis = new ArrayList<String>();
		fullBranchList = new ArrayList<String>();
		try 
		{
			Map<String,GHBranch> branchesMap = repo.getBranches();
			Collection<GHBranch> branchCollection = branchesMap.values();

			for(GHBranch branch :branchCollection)
			{
				branchCount++;
				fullBranchList.add(branch.getName());
				try
				{
					repo.getFileContent(".travis.yml", branch.getName());
				}
				catch(FileNotFoundException e)
				{
					branchesWithNoTravisCount++;
					branchesWithNoTravis.add(branch.getName());
				}
			}
		} catch (IOException e) {
			System.out.println("IO exception!!");
		}
	}
	/*
	 * Populates the tags.
	 * connect() needs to be called before calling this function
	 */
	public void populateTagData(GHRepository repo)
	{
		PagedIterable<GHTag> tagIter;
		tagsWithNoTravis = new ArrayList<String>();
		fullTagList = new ArrayList<String>();
		try 
		{
			tagIter = repo.listTags();
			for(GHTag tag :tagIter )
			{
				tagCount++;
				fullTagList.add(tag.getName());
				try {
					repo.getFileContent(".travis.yml", tag.getName());
				} catch (FileNotFoundException e) {
					tagsWithNoTravisCount++;
					tagsWithNoTravis.add(tag.getName());
				}
			}
		} catch (IOException e1) {
			System.out.println("IO exception!!");
		}
	}
	/*
	 * Prints all branches.
	 * Make sure populateBranchdata is called before calling this function
	 */
	public void printBranches()
	{
		System.out.println("Total branch count :"+branchCount);
		System.out.println("Total Branches list :"+fullBranchList.toString());
	}
	/*
	 * Prints all branches with no .travis.yml.
	 * Make sure populateBranchdata is called before calling this function
	 */
	public void printBranchesWithNoTravis()
	{
		System.out.println("Number of branches with no .travis.yml :"+branchesWithNoTravisCount);
		System.out.println("List of branches with no .travis.yml :"+branchesWithNoTravis.toString());
	}
	/*
	 * Prints all tags.
	 * Make sure populateTagData is called before calling this function
	 */
	public void printTags()
	{
		System.out.println("Total tag count :"+tagCount);
		System.out.println("Total tags list :"+fullTagList.toString());
	}
	/*
	 * Prints all tags with no .travis.yml..
	 * Make sure populateTagData is called before calling this function
	 */
	public void printTagsWithNoTravis()
	{
		System.out.println("Number of tags with no .travis.yml :"+tagsWithNoTravisCount);
		System.out.println("List of tags with no .travis.yml :"+tagsWithNoTravis.toString());
	}
}
