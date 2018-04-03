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

import cop5556sp18.Scanner.Kind;
import cop5556sp18.Scanner.Token;

public class ExpressionFunctionAppWithPixel extends Expression {

	public final Kind name;
	public final Expression e0;
	public final Expression e1;

	public ExpressionFunctionAppWithPixel(Token firstToken, Token name,
			Expression e0, Expression e1) {
		super(firstToken);
		this.name = name.kind;
		this.e0 = e0;
		this.e1 = e1;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitExpressionFunctionAppWithPixel(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((e0 == null) ? 0 : e0.hashCode());
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ExpressionFunctionAppWithPixel))
			return false;
		ExpressionFunctionAppWithPixel other = (ExpressionFunctionAppWithPixel) obj;
		if (e0 == null) {
			if (other.e0 != null)
				return false;
		} else if (!e0.equals(other.e0))
			return false;
		if (e1 == null) {
			if (other.e1 != null)
				return false;
		} else if (!e1.equals(other.e1))
			return false;
		if (name != other.name)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExpressionFunctionAppWithPixel [name=" + name + ", e0=" + e0
				+ ", e1=" + e1 + "]";
	}

}
