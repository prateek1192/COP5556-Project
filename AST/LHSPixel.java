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

public class LHSPixel extends LHS {

	public final String name;
	public final PixelSelector pixelSelector;
	private Declaration dec;

	public LHSPixel(Token firstToken, Token name, PixelSelector pixelSelector) {
		super(firstToken);
		this.name = name.getText();
		this.pixelSelector = pixelSelector;
	}
	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitLHSPixel(this, arg);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((pixelSelector == null) ? 0 : pixelSelector.hashCode());
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
		LHSPixel other = (LHSPixel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pixelSelector == null) {
			if (other.pixelSelector != null)
				return false;
		} else if (!pixelSelector.equals(other.pixelSelector))
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
		return "LHSPixel [name=" + name + ", pixelSelector=" + pixelSelector
				+ "]";
	}

}
