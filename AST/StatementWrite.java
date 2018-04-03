package cop5556sp18.AST;

/**
 * This code is for the class project in COP5556 Programming Language Principles 
 * at the University of Florida, Spring 2018.
 * 
 * This software is solely for the educational benefit of students 
 * enrolled in the course during the Spring 2018 semester.  
 * 
 * This software, and any software derived from it, may not be shared with others or posted to public web sites,
 * either during the course or afterwards.
 * 
 *  @Beverly A. Sanders, 2018
 */

import cop5556sp18.Scanner.Token;

public class StatementWrite extends Statement {

	public final String sourceName;
	public final String destName;
	private Declaration sourceDec;
	private Declaration destDec;

	public StatementWrite(Token firstToken, Token sourceName, Token destName) {
		super(firstToken);
		this.sourceName = sourceName.getText();
		this.destName = destName.getText();
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitStatementWrite(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((destName == null) ? 0 : destName.hashCode());
		result = prime * result
				+ ((sourceName == null) ? 0 : sourceName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof StatementWrite))
			return false;
		StatementWrite other = (StatementWrite) obj;
		if (destName == null) {
			if (other.destName != null)
				return false;
		} else if (!destName.equals(other.destName))
			return false;
		if (sourceName == null) {
			if (other.sourceName != null)
				return false;
		} else if (!sourceName.equals(other.sourceName))
			return false;
		return true;
	}
	
	public void setSourceDec(Declaration dec)
	{
		sourceDec = dec;
	}
	
	public Declaration getSourceDec()
	{
		return sourceDec;
	}
	
	public void setDestDec(Declaration dec)
	{
		destDec = dec;
	}
	
	public Declaration getDestDec()
	{
		return destDec;
	}

	@Override
	public String toString() {
		return "StatementWrite [sourceName=" + sourceName + ", destName="
				+ destName + "]";
	}

}
