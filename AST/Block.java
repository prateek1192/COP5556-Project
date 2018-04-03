package cop5556sp18.AST;

/**
 * This code is for the class project in COP5556 Programming Language Principles 
 * at the University of Florida, Spring 2018.
 * 
 * This software is solely for the educational benefit of students 
 * enrolled in the course during the Spring 2018 semester.  
 * 
 * This software, and any software derived from it,  may not be shared with others or posted to public web sites,
 * either during the course or afterwards.
 * 
 *  @Beverly A. Sanders, 2018
 */

import java.util.List;

import cop5556sp18.Scanner.Token;

public class Block extends ASTNode {

	public final List<ASTNode> decsOrStatements;

	public ASTNode decOrStatement(int index) {
		return decsOrStatements.get(index);
	}

	public Block(Token firstToken, List<ASTNode> decsOrStatements) {
		super(firstToken);
		this.decsOrStatements = decsOrStatements;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitBlock(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((decsOrStatements == null)
				? 0
				: decsOrStatements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (decsOrStatements == null) {
			if (other.decsOrStatements != null)
				return false;
		} else if (!decsOrStatements.equals(other.decsOrStatements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Block [decsOrStatements=" + decsOrStatements + "]";
	}

}
