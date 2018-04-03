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

import cop5556sp18.Scanner.Token;

public class Program extends ASTNode {

	public final String progName;
	public final Block block;

	public Program(Token firstToken, Token progName, Block block) {
		super(firstToken);
		this.progName = progName.getText();
		this.block = block;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitProgram(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((block == null) ? 0 : block.hashCode());
		result = prime * result
				+ ((progName == null) ? 0 : progName.hashCode());
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
		Program other = (Program) obj;
		if (block == null) {
			if (other.block != null)
				return false;
		} else if (!block.equals(other.block))
			return false;
		if (progName == null) {
			if (other.progName != null)
				return false;
		} else if (!progName.equals(other.progName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Program [progName=" + progName + ", block=" + block + "]";
	}

}
