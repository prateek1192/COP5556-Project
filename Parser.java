package cop5556sp18;
/* *
 * Parser for the class project in COP5556 Programming Language Principles 
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

import static  cop5556sp18.Scanner.Kind.*;
import static cop5556sp18.Scanner.Kind.BOOLEAN_LITERAL;
import static cop5556sp18.Scanner.Kind.COMMA;
import static cop5556sp18.Scanner.Kind.DOT;
import static cop5556sp18.Scanner.Kind.EOF;
import static cop5556sp18.Scanner.Kind.IDENTIFIER;
import static cop5556sp18.Scanner.Kind.INTEGER_LITERAL;
import static cop5556sp18.Scanner.Kind.FLOAT_LITERAL;
import static cop5556sp18.Scanner.Kind.KW_Z;
import static cop5556sp18.Scanner.Kind.KW_abs;
import static cop5556sp18.Scanner.Kind.KW_alpha;
import static cop5556sp18.Scanner.Kind.KW_atan;
import static cop5556sp18.Scanner.Kind.KW_blue;
import static cop5556sp18.Scanner.Kind.KW_boolean;
import static cop5556sp18.Scanner.Kind.KW_cart_x;
import static cop5556sp18.Scanner.Kind.KW_cart_y;
import static cop5556sp18.Scanner.Kind.KW_cos;
import static cop5556sp18.Scanner.Kind.KW_default_height;
import static cop5556sp18.Scanner.Kind.KW_default_width;
import static cop5556sp18.Scanner.Kind.KW_filename;
import static cop5556sp18.Scanner.Kind.KW_float;
import static cop5556sp18.Scanner.Kind.KW_from;
import static cop5556sp18.Scanner.Kind.KW_green;
import static cop5556sp18.Scanner.Kind.KW_height;
import static cop5556sp18.Scanner.Kind.KW_if;
import static cop5556sp18.Scanner.Kind.KW_image;
import static cop5556sp18.Scanner.Kind.KW_input;
import static cop5556sp18.Scanner.Kind.KW_int;
import static cop5556sp18.Scanner.Kind.KW_log;
import static cop5556sp18.Scanner.Kind.KW_polar_a;
import static cop5556sp18.Scanner.Kind.KW_polar_r;
import static cop5556sp18.Scanner.Kind.KW_red;
import static cop5556sp18.Scanner.Kind.KW_show;
import static cop5556sp18.Scanner.Kind.KW_sin;
import static cop5556sp18.Scanner.Kind.KW_to;
import static cop5556sp18.Scanner.Kind.KW_while;
import static cop5556sp18.Scanner.Kind.KW_width;
import static cop5556sp18.Scanner.Kind.KW_write;
import static cop5556sp18.Scanner.Kind.LBRACE;
import static cop5556sp18.Scanner.Kind.LPAREN;
import static cop5556sp18.Scanner.Kind.LSQUARE;
import static cop5556sp18.Scanner.Kind.OP_AND;
import static cop5556sp18.Scanner.Kind.OP_AT;
import static cop5556sp18.Scanner.Kind.OP_EXCLAMATION;
import static cop5556sp18.Scanner.Kind.OP_COLON;
import static cop5556sp18.Scanner.Kind.OP_DIV;
import static cop5556sp18.Scanner.Kind.OP_EQ;
import static cop5556sp18.Scanner.Kind.OP_GE;
import static cop5556sp18.Scanner.Kind.OP_GT;
import static cop5556sp18.Scanner.Kind.OP_LE;
import static cop5556sp18.Scanner.Kind.OP_LT;
import static cop5556sp18.Scanner.Kind.OP_MINUS;
import static cop5556sp18.Scanner.Kind.OP_MOD;
import static cop5556sp18.Scanner.Kind.OP_NEQ;
import static cop5556sp18.Scanner.Kind.OP_OR;
import static cop5556sp18.Scanner.Kind.OP_PLUS;
import static cop5556sp18.Scanner.Kind.OP_POWER;
import static cop5556sp18.Scanner.Kind.OP_QUESTION;
import static cop5556sp18.Scanner.Kind.OP_TIMES;
import static cop5556sp18.Scanner.Kind.RBRACE;
import static cop5556sp18.Scanner.Kind.RPAREN;
import static cop5556sp18.Scanner.Kind.RSQUARE;
import static cop5556sp18.Scanner.Kind.SEMI;

import java.util.ArrayList;
import java.util.Arrays;

import cop5556sp18.Scanner.Kind;
import cop5556sp18.Scanner.Token;
import cop5556sp18.AST.ASTNode;
import cop5556sp18.AST.Block;
import cop5556sp18.AST.Declaration;
import cop5556sp18.AST.Expression;
import cop5556sp18.AST.ExpressionBinary;
import cop5556sp18.AST.ExpressionBooleanLiteral;
import cop5556sp18.AST.ExpressionConditional;
import cop5556sp18.AST.ExpressionFloatLiteral;
import cop5556sp18.AST.ExpressionFunctionAppWithExpressionArg;
import cop5556sp18.AST.ExpressionFunctionAppWithPixel;
import cop5556sp18.AST.ExpressionIdent;
import cop5556sp18.AST.ExpressionIntegerLiteral;
import cop5556sp18.AST.ExpressionPixelConstructor;
import cop5556sp18.AST.ExpressionPixel;
import cop5556sp18.AST.ExpressionPredefinedName;
import cop5556sp18.AST.ExpressionUnary;
import cop5556sp18.AST.StatementInput;
import cop5556sp18.AST.StatementShow;
import cop5556sp18.AST.StatementSleep;
import cop5556sp18.AST.StatementWhile;
import cop5556sp18.AST.LHS;
import cop5556sp18.AST.LHSIdent;
import cop5556sp18.AST.LHSPixel;
import cop5556sp18.AST.LHSSample;
import cop5556sp18.AST.PixelSelector;
import cop5556sp18.AST.Program;
import cop5556sp18.AST.Statement;
import cop5556sp18.AST.StatementAssign;
import cop5556sp18.AST.StatementIf;
import cop5556sp18.AST.StatementWrite;

public class Parser {
	
	@SuppressWarnings("serial")
	public static class SyntaxException extends Exception {
		Token t;

		public SyntaxException(Token t, String message) {
			super(message);
			this.t = t;
		}

	}


	void error(Kind... expectedKinds) throws SyntaxException {
		String kinds = Arrays.toString(expectedKinds);
		String message;
		if(expectedKinds.length == 1) {
			message = "Expected " + kinds + " at " + t.line() + ":" + t.posInLine();
		} else {
		    message = "Expected one of" + kinds + " at " + t.line() + ":" + t.posInLine();
		}
		throw new SyntaxException(t, message);
	}

	void error(Token t, String m) throws SyntaxException {
		String message = m + " at " + t.line() + ":" + t.posInLine();
		throw new SyntaxException(t, message);
	}

	Scanner scanner;
	Token t;

	Parser(Scanner scanner) {
		this.scanner = scanner;
		t = scanner.nextToken();
	}


	public Program parse() throws SyntaxException {
		Program p = program();
		matchEOF();
		return p;
	}

	/*
	 * Program ::= Identifier Block
	 */
	 Program program() throws SyntaxException {
		Token first = t;
		Token progName = match(IDENTIFIER);
		Block block = block();
		return new Program (first, progName, block);
	}
	
	/*
	 * Block ::=  { (  (Declaration | Statement) ; )* }
	 */
	
	 Kind[] firstDec = { KW_int, KW_boolean, KW_image, KW_float, KW_filename };
	 Kind[] firstStatement = {IDENTIFIER, KW_if, KW_while, KW_write, KW_show, KW_input, KW_red, KW_green, KW_blue, KW_alpha, KW_sleep};

	 Block block() throws SyntaxException {
		Token first = t;
		match(LBRACE);
		ArrayList<ASTNode> decsAndStatements = new ArrayList<ASTNode>();
		while (isKind(firstDec) || isKind(firstStatement)) {
			if (isKind(firstDec)) {
				Declaration dec = declaration();
				decsAndStatements.add(dec);
			} else if (isKind(firstStatement)) {
				Statement s =statement();
				decsAndStatements.add(s);
			}
			match(SEMI);
		}
		match(RBRACE);
		return new Block(first, decsAndStatements) ;
	}
/*
 * Statement ::= StatementInput | StatementWrite | StatementAssignment 
		| StatementWhile | StatementIf | StatementShow | StatementSleep
		
	StatementInput ::= input IDENTIFIER from @ Expression
	StatementShow ::= show Expression
	StatementWrite ::= write IDENTIFIER to IDENTIFIER
	StatementAssignment ::= LHS ::= Expression
    StatementIf ::= if ( Expression ) Block 
    StatementWhile ::= if (Expression ) Block
    StatementSleep ::= sleep Expression
    StatementAssignment ::= LHS := Expression
    
 */
	 Kind[] firstLHS = {IDENTIFIER, KW_red, KW_green, KW_blue, KW_alpha};

	 Statement statement() throws SyntaxException {
		Token first = t;
		if (isKind(KW_show)) {
			consume();  //show
			Expression e = expression();
			return new StatementShow(first, e);
		}
		if (isKind(KW_write)) {
			consume(); //write
			Token sourceName = match(IDENTIFIER);
			match(KW_to);
			Token destName = match(IDENTIFIER);
			return new StatementWrite(first, sourceName, destName);			
		}
		if (isKind(KW_input)) {
			consume(); //input
			Token destName = match(IDENTIFIER);
			match(KW_from);
			match(OP_AT);
			Expression e = expression();
			return new StatementInput(first, destName, e);
		}
		if (isKind(firstLHS)) {
			LHS lhs = lhs();
			match(OP_ASSIGN);
			Expression e = expression();
			return new StatementAssign(first, lhs, e);
		}
		if (isKind(KW_if)) {
			consume();
			match(LPAREN);
			Expression e = expression();
			match(RPAREN);
			Block b = block();
			return new StatementIf(first, e, b);
		}
		if (isKind(KW_while)) {
			consume();
			match(LPAREN);
			Expression e = expression();
			match(RPAREN);
			Block b = block();
			return new StatementWhile(first, e, b);
		}
		if (isKind(KW_sleep)) {
			consume();
			Expression e = expression();
			return new StatementSleep(first, e);
		}
		throw new UnsupportedOperationException();
	}

	/*
	      LHS ::=  IDENTIFIER | IDENTIFIER PixelSelector | Color ( IDENTIFIER PixelSelector )
	      Color ::= red | green | blue | alpha
	 */
	 LHS lhs() throws SyntaxException {
		Token first = t;
		if (isKind(IDENTIFIER)) {
			Token name = consume();
			if (isKind(LSQUARE)) {
				PixelSelector pixel = pixelSelector();
				return new LHSPixel(first, name, pixel);
			}
			return new LHSIdent(first, name);
		}
		Token color = consume();
		match(LPAREN);
		Token name = match(IDENTIFIER);
		PixelSelector selector = pixelSelector();
		match(RPAREN);
		return new LHSSample(first, name, selector, color);
	}
/*
    PixelSelector ::= [ Expression , Expression ]
 */
	 PixelSelector pixelSelector() throws SyntaxException {
		Token first = t;
		consume();
		Expression e0 = expression();
		match(COMMA);
		Expression e1 = expression();
		match(RSQUARE);
		PixelSelector pixel = new PixelSelector(first,e0,e1);
		return pixel;
	}

/* 
 *      Declaration ::= Type IDENTIFIER | image Identifier [ Expression , Expression ]
 *      Type ::= int | float | boolean | image | filename
 */
	 Declaration declaration() throws SyntaxException {
		Token first = t;
		Token type = consume();
		Token name = match(IDENTIFIER);
		Expression w = null;
		Expression h = null;
		if (type.kind == KW_image) {
			if(isKind(LSQUARE)) {
				consume();
				w = expression();
				match(COMMA);
				h = expression();
				match(RSQUARE);
			}
		}
		return new Declaration(first,type,name, w, h);
	}

	protected boolean isKind(Kind kind) {
		return t.kind == kind;
	}

	protected boolean isKind(Kind... kinds) {
		for (Kind k : kinds) {
			if (k == t.kind)
				return true;
		}
		return false;
	}

	/**
	 * Use when you are interested in some token other than the current one in parser.
	 * 
	 * @param t
	 * @param kind
	 * @return
	 */
	protected static boolean isKind(Token t, Kind kind) {
		return t.kind == kind;
	}

	protected static boolean isKind(Token t, Kind... kinds) {
		for (Kind k : kinds) {
			if (k == t.kind)
				return true;
		}
		return false;
	}

	/**
	 * Precondition: kind != EOF
	 * 
	 * @param kind
	 * @return
	 * @throws SyntaxException
	 */
	 Token match(Kind kind) throws SyntaxException {
		Token tmp = t;
		if (isKind(kind)) {
			consume();
			return tmp;
		}
		error(kind);
		return null; // unreachable
	}

	/**
	 * Precondition: kind != EOF
	 * 
	 * @param kind
	 * @return
	 * @throws SyntaxException
	 */
	 Token match(Kind... kinds) throws SyntaxException {
		Token tmp = t;
		if (isKind(kinds)) {
			consume();
			return tmp;
		}
		StringBuilder sb = new StringBuilder();
		for (Kind kind1 : kinds) {
			sb.append(kind1).append(kind1).append(" ");
		}
		error(kinds);
		return null; // unreachable
	}

	 Token consume() throws SyntaxException {
		Token tmp = t;
		if (isKind(t, EOF)) {
			error(t, "attempting to consume EOF");
		}
		t = scanner.nextToken();
		return tmp;
	}

//	 Kind[] concat(Kind[]... kinds) {
//		int size = 0;
//		for (Kind[] kindArray : kinds) {
//			size += kindArray.length;
//		}
//		Kind[] toReturn = new Kind[size];
//		int destPos = 0;
//		for (Kind[] kindArray : kinds) {
//			System.arraycopy(kindArray, 0, toReturn, destPos, kindArray.length);
//			destPos += kindArray.length;
//		}
//		return toReturn;
//	}

	/**
	 * Only for check at end of program. Does not "consume" EOF so no attempt to get
	 * nonexistent next Token.
	 * 
	 * @return
	 * @throws SyntaxException
	 */
	 Token matchEOF() throws SyntaxException {
		if (isKind(EOF)) {
			return t;
		}
		error(EOF);
		return null; // unreachable
	}
	
	/**
	 * 	Expression ::=  OrExpression  ? Expression : Expression
	               |   OrExpression

	 */
	Expression expression() throws SyntaxException {
		Token first = t;
		Expression e0 = orExpression();
		if (isKind(OP_QUESTION)) {
			consume();
			Expression e1 = expression();
			match(OP_COLON);
			Expression e2 = expression();
			e0 = new ExpressionConditional(first, e0, e1, e2);
		}
		return e0;
	}

	/**
	 * OrExpression ::= AndExpression   (  |  AndExpression) *
	 * 
	 */
	 Expression orExpression() throws SyntaxException {
		Token first = t;
		Expression e0 = andExpression();
		while(isKind(OP_OR)) {
			Token op = consume();
			Expression e1 = andExpression();
			e0 = new ExpressionBinary(first, e0, op, e1);
		}
		return e0;
	}

	
	/**
	 * 	AndExpression ::= EqExpression ( & EqExpression )*

	 */
	 Expression andExpression() throws SyntaxException {
		Token first = t;
		Expression e0 = eqExpression();
		while(isKind(OP_AND)) {
			Token op = consume();
			Expression e1 = eqExpression();
			e0 = new ExpressionBinary(first, e0, op, e1);
		}
		return e0;
	}

	
	/**
	 * 	EqExpression ::= RelExpression  (  (== | !=)  RelExpression )*

	 */
	 Expression eqExpression() throws SyntaxException {
		Token first = t;
		Expression e0 = relExpression();
		while(isKind(OP_EQ, OP_NEQ)) {
			Token op = consume();
			Expression e1 = relExpression();
			e0 = new ExpressionBinary(first, e0,op,e1);
		}
		return e0;
	}

	
	/**
	 * RelExpression ::= AddExpression (  (<  | > |  <=  | >=)   AddExpression)*

	 */
	 Expression relExpression() throws SyntaxException {
		Token first = t;
		Expression e0 = addExpression();
		while(isKind(OP_LT,OP_GT,OP_LE,OP_GE)) {
			Token op = consume();
			Expression e1 = addExpression();
			e0 = new ExpressionBinary(first,e0,op,e1);
		}
		return e0;
	}

	/**
	 * 	AddExpression ::= MultExpression   (  (+ | -) MultExpression )*

	 */
	 Expression addExpression() throws SyntaxException {
		Token first = t;
		Expression e0 = multExpression();
		while(isKind(OP_PLUS, OP_MINUS)) {
			Token op = consume();
			Expression e1 = multExpression();
			e0 = new ExpressionBinary(first,e0,op,e1);
		}
		return e0;
	}

	/**
	 * MultExpression := PowerExpression ( (* | /  | %) PowerExpression )*

	 */
	 Expression multExpression() throws SyntaxException {
		Token first = t;
		Expression e0 = powerExpression();
		while(isKind(OP_TIMES, OP_DIV, OP_MOD)) {
			Token op = consume();
			Expression e1 = powerExpression();
			e0 = new ExpressionBinary(first,e0,op,e1);
		}
		return e0;
	}

	/**
	 * 	PowerExpression := PowerExpression := UnaryExpression  (** PowerExpression | eps)

	 */
	 Expression powerExpression() throws SyntaxException {
		Token first = t;
		Expression e0 = unaryExpression();
		if (isKind(OP_POWER)) {
			Token op = consume();
			Expression e1 = powerExpression();
			return new ExpressionBinary(first, e0, op, e1);
		}
		return e0;
	}
	
	/**
	 * 	UnaryExpression ::= + UnaryExpression | - UnaryExpression | UnaryExpressionNotPlusMinus

	 */
	 Expression unaryExpression() throws SyntaxException {
		Token first = t;
		if (isKind(OP_PLUS)) {  //throw away the plus here
			Token op = consume();
			Expression e = unaryExpression();
			return new ExpressionUnary(first, op, e);
		}
		else if (isKind(OP_MINUS)){
			Token op = consume();
			Expression e = unaryExpression();
			return new ExpressionUnary(first, op, e);
		}
		else {
			return unaryExpressionNotPlusMinus();
		}
	}

	
	/**
	UnaryExpressionNotPlusMinus ::=  ! UnaryExpression  | Primary 

	 */
	
	 Expression unaryExpressionNotPlusMinus() throws SyntaxException {
		Token first = t;
		if (isKind(OP_EXCLAMATION)) {
			Token op = consume();
			Expression e = unaryExpression();
			return new ExpressionUnary(first,op,e);
		} else 	{
			return primary(); //errors will be reported by primary()
		}
	}
	


/*	Primary ::= INTEGER_LITERAL | BOOLEAN_LITERAL | FLOATING_POINT_LITERAL | 
                ( Expression ) | FunctionApplication  | PixelExpression | 
                 PredefinedName | PixelConstructor
                 
     ExpressionPixelConstructor ::= << Expression , Expression , Expression , Expression >> ;
     ExpressionPixel ::= IDENTIFIER PixelSelector
     ExpressionSample ::= Color ( Expression )
 

	*/

	Kind[] functionName = {KW_sin, KW_cos, KW_log, KW_atan, KW_abs, KW_cart_x , KW_cart_y, KW_polar_a, KW_polar_r, KW_width, KW_height, KW_red, KW_green, KW_blue, KW_alpha, KW_int, KW_float};
	Kind[] predefinedName = {KW_Z, KW_default_width, KW_default_height};
	protected Expression primary() throws SyntaxException {
		Token first = t;
		if (isKind(INTEGER_LITERAL)) {
			Token intLit = consume();
			return new ExpressionIntegerLiteral(first, intLit);
		}
		if (isKind(BOOLEAN_LITERAL)) {
			Token booleanLit = consume();			
			return new ExpressionBooleanLiteral(first, booleanLit);
		}
		if (isKind(FLOAT_LITERAL)) {
			Token floatLit = consume();
			return new ExpressionFloatLiteral(first, floatLit);
		}
		if (isKind(LPAREN)) {
			consume();
			Expression e = expression();
			match(RPAREN);
			return e;
		}
		if (isKind(functionName)) {
			Expression e= functionApplication();
			return e;
		}
		if (isKind(predefinedName)) { //
			Token t = consume();
			return new ExpressionPredefinedName(first,t);
		}
		if (isKind(LPIXEL)) {
			consume();
			Expression alpha = expression();
			match(COMMA);
			Expression red = expression();
			match(COMMA);
			Expression green = expression();
			match(COMMA);
			Expression blue = expression();
			match(RPIXEL);
			return new ExpressionPixelConstructor(first, alpha, red, green, blue);
		}
		if (isKind(IDENTIFIER)) {//is an IdentEpression, or PixelExpression 
		Token name = consume();
		if (isKind(LSQUARE)) {
			PixelSelector pixelSelector = pixelSelector();
		    	return new ExpressionPixel(first, name, pixelSelector);
		}
		return new ExpressionIdent(first, name);
	}
		error(t, "Illegal start of expression");
		return null;
		
	}

	/**
	 * 
	 * 	FunctionApplication ::= FunctionName ( Expression )  | FunctionName  [ Expression, Expression ] 
	    FunctionName ::= sin | cos | atan | abs | log | cart_x | cart_y | polar_a | polar_r 
	    			| red | green | blue | alpha | int | float | width | height

	 */

	 Expression functionApplication() throws SyntaxException {
		Token first = t;
		Token name = match(functionName);
		if (isKind(LPAREN)) {
			consume();
			Expression e = expression();
			match(RPAREN);
			return new ExpressionFunctionAppWithExpressionArg(first, name, e);
		}
		if (isKind(LSQUARE)) {
			consume();
			Expression e0 = expression();
			match(COMMA);
			Expression e1 = expression();
			match(RSQUARE);
			return new ExpressionFunctionAppWithPixel(first, name, e0, e1);
		}
		error(t, "bug in parser");
		return null;
	}
}