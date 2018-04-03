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

public class StatementInput extends Statement {

	public final String destName;
	public final Expression e;
	private Declaration dec;

	public StatementInput(Token firstToken, Token destName, Expression e) {
		super(firstToken);
		this.destName = destName.getText();
		this.e = e;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitStatementInput(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((destName == null) ? 0 : destName.hashCode());
		result = prime * result + ((e == null) ? 0 : e.hashCode());
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
		StatementInput other = (StatementInput) obj;
		if (destName == null) {
			if (other.destName != null)
				return false;
		} else if (!destName.equals(other.destName))
			return false;
		if (e == null) {
			if (other.e != null)
				return false;
		} else if (!e.equals(other.e))
			return false;
		return true;
	}
	
	public void setDec(Declaration dec)
	{
		this.dec = dec;
	}
	
	public Declaration getDec()
	{
		return dec;
	}

	@Override
	public String toString() {
		return "StatementInput [destName=" + destName + ", e=" + e + "]";
	}

}
