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

public class ExpressionFunctionAppWithExpressionArg extends Expression {

	public final Kind function;
	public final Expression e;

	public ExpressionFunctionAppWithExpressionArg(Token firstToken,
			Token function, Expression e) {
		super(firstToken);
		this.function = function.kind;
		this.e = e;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitExpressionFunctionAppWithExpressionArg(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((e == null) ? 0 : e.hashCode());
		result = prime * result
				+ ((function == null) ? 0 : function.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ExpressionFunctionAppWithExpressionArg))
			return false;
		ExpressionFunctionAppWithExpressionArg other = (ExpressionFunctionAppWithExpressionArg) obj;
		if (e == null) {
			if (other.e != null)
				return false;
		} else if (!e.equals(other.e))
			return false;
		if (function != other.function)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExpressionFunctionApp [function=" + function + ", e=" + e + "]";
	}

}
