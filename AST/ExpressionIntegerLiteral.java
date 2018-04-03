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

public class ExpressionIntegerLiteral extends Expression {
	
	public final int value;

	public ExpressionIntegerLiteral(Token firstToken, Token intLiteral) {
		super(firstToken);
		this.value = intLiteral.intVal();
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitExpressionIntegerLiteral(this,arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ExpressionIntegerLiteral))
			return false;
		ExpressionIntegerLiteral other = (ExpressionIntegerLiteral) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExpressionIntegerLiteral [value=" + value + "]";
	}
	
}
