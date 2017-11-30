package pl.coderslab.warsztat3.model;

public class SolutionWithAuthor {
	private SolutionDTO solution;
	private String AuthorName;
	
	
	public SolutionWithAuthor(SolutionDTO solution, String authorName) {
		super();
		this.solution = solution;
		this.AuthorName = authorName;
	}
	public SolutionDTO getSolution() {
		return solution;
	}
	public String getAuthorName() {
		return AuthorName;
	}
	
	

}
