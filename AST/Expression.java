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
import cop5556sp18.Types.Type;
public abstract class Expression extends ASTNode {
	private Type t;
	public Type getType(){
		return t;
	}
	public void setType(Type t1){
		t=t1;
	}
	public Expression(Token firstToken) {
		super(firstToken);
	}
}