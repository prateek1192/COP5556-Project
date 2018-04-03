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

public class ExpressionConditional extends Expression {

	public final Expression guard;
	public final Expression trueExpression;
	public final Expression falseExpression;

	public ExpressionConditional(Token firstToken, Expression guard,
			Expression trueExpression, Expression falseExpression) {
		super(firstToken);
		this.guard = guard;
		this.trueExpression = trueExpression;
		this.falseExpression = falseExpression;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitExpressionConditional(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((falseExpression == null) ? 0 : falseExpression.hashCode());
		result = prime * result + ((guard == null) ? 0 : guard.hashCode());
		result = prime * result
				+ ((trueExpression == null) ? 0 : trueExpression.hashCode());
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
		ExpressionConditional other = (ExpressionConditional) obj;
		if (falseExpression == null) {
			if (other.falseExpression != null)
				return false;
		} else if (!falseExpression.equals(other.falseExpression))
			return false;
		if (guard == null) {
			if (other.guard != null)
				return false;
		} else if (!guard.equals(other.guard))
			return false;
		if (trueExpression == null) {
			if (other.trueExpression != null)
				return false;
		} else if (!trueExpression.equals(other.trueExpression))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpressionConditional [guard=");
		builder.append(guard);
		builder.append(", trueExpression=");
		builder.append(trueExpression);
		builder.append(", falseExpression=");
		builder.append(falseExpression);
		builder.append("]");
		return builder.toString();
	}

}
