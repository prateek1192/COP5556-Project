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

public class ExpressionPredefinedName extends Expression {

	public final Kind name;

	public ExpressionPredefinedName(Token firstToken, Token name) {
		super(firstToken);
		this.name = name.kind;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitExpressionPredefinedName(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ExpressionPredefinedName))
			return false;
		ExpressionPredefinedName other = (ExpressionPredefinedName) obj;
		if (name != other.name)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExpressionPredefinedName [name=" + name + "]";
	}

}
