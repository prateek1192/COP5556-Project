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

public class ExpressionBinary extends Expression {
	
	public final Expression leftExpression;
	public final Kind op;
	public final Expression rightExpression;
	
	

	public ExpressionBinary(Token firstToken, Expression leftExpression, Token op, Expression rightExpression) {
		super(firstToken);
		this.leftExpression = leftExpression;
		this.op = op.kind;
		this.rightExpression = rightExpression;
	}



	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitExpressionBinary(this, arg);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((leftExpression == null) ? 0 : leftExpression.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result + ((rightExpression == null) ? 0 : rightExpression.hashCode());
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
		ExpressionBinary other = (ExpressionBinary) obj;
		if (leftExpression == null) {
			if (other.leftExpression != null)
				return false;
		} else if (!leftExpression.equals(other.leftExpression))
			return false;
		if (op != other.op)
			return false;
		if (rightExpression == null) {
			if (other.rightExpression != null)
				return false;
		} else if (!rightExpression.equals(other.rightExpression))
			return false;
		return true;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpressionBinary [leftExpression=");
		builder.append(leftExpression);
		builder.append(", op=");
		builder.append(op);
		builder.append(", rightExpression=");
		builder.append(rightExpression);
		builder.append("]");
		return builder.toString();
	}
	
	

}
