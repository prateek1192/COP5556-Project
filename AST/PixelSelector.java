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

public class PixelSelector extends ASTNode {

	public final Expression ex;
	public final Expression ey;

	public PixelSelector(Token firstToken, Expression ex, Expression ey) {
		super(firstToken);
		this.ex = ex;
		this.ey = ey;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitPixelSelector(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ex == null) ? 0 : ex.hashCode());
		result = prime * result + ((ey == null) ? 0 : ey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof PixelSelector))
			return false;
		PixelSelector other = (PixelSelector) obj;
		if (ex == null) {
			if (other.ex != null)
				return false;
		} else if (!ex.equals(other.ex))
			return false;
		if (ey == null) {
			if (other.ey != null)
				return false;
		} else if (!ey.equals(other.ey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PixelSelector [ex=" + ex + ", ey=" + ey + "]";
	}

}
