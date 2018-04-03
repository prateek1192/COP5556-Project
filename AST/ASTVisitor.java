package cop5556sp18.AST;

/**
 * This code is for the class project in COP5556 Programming Language Principles
 * at the University of Florida, Spring 2018.
 * 
 * This software is solely for the educational benefit of students enrolled in
 * the course during the Spring 2018 semester.
 * 
 * This software, and any software derived from it, may not be shared with
 * others or posted to public web sites, either during the course or afterwards.
 * 
 * @Beverly A. Sanders, 2018
 */

public interface ASTVisitor {

	/**
	 * Interface supporting the Visitor Pattern for traversal of abstract syntax
	 * trees.
	 * 
	 * There is a method for each AST node class. Within each AST subclass C,
	 * the visit(ASTVisitor, Object):Object method should be overridden to all
	 * the corresponding method in this interface.
	 * 
	 * For example, in class IntegerLiteral:
	 * 
	 * @Override public Object visit(ASTVisitor v, Object arg) throws Exception
	 *           { return v.visitExpressionIntegerLiteral(this,arg); }
	 * 
	 *           In a class implementing this interface, the implementation of
	 *           the visitExpressionIntegerLiteral specifies what happens during
	 *           traversal when an ExpressionIntegerLiteral node is reached.
	 */

	Object visitBlock(Block block, Object arg) throws Exception;

	Object visitBooleanLiteral(
			ExpressionBooleanLiteral expressionBooleanLiteral, Object arg)
			throws Exception;

	Object visitDeclaration(Declaration declaration, Object arg)
			throws Exception;

	Object visitExpressionBinary(ExpressionBinary expressionBinary, Object arg)
			throws Exception;;

	Object visitExpressionConditional(
			ExpressionConditional expressionConditional, Object arg)
			throws Exception;

	Object visitExpressionFloatLiteral(
			ExpressionFloatLiteral expressionFloatLiteral, Object arg)
			throws Exception;

	Object visitExpressionFunctionAppWithExpressionArg(
			ExpressionFunctionAppWithExpressionArg expressionFunctionAppWithExpressionArg,
			Object arg) throws Exception;

	Object visitExpressionFunctionAppWithPixel(
			ExpressionFunctionAppWithPixel expressionFunctionAppWithPixel,
			Object arg) throws Exception;

	Object visitExpressionIdent(ExpressionIdent expressionIdent, Object arg)
			throws Exception;

	Object visitExpressionIntegerLiteral(
			ExpressionIntegerLiteral expressionIntegerLiteral, Object arg)
			throws Exception;

	Object visitExpressionPixel(ExpressionPixel expressionPixel, Object arg)
			throws Exception;

	Object visitExpressionPixelConstructor(
			ExpressionPixelConstructor expressionPixelConstructor, Object arg)
			throws Exception;

	Object visitExpressionPredefinedName(
			ExpressionPredefinedName expressionPredefinedName, Object arg)
			throws Exception;

	Object visitExpressionUnary(ExpressionUnary expressionUnary, Object arg)
			throws Exception;

	Object visitLHSIdent(LHSIdent lhsIdent, Object arg) throws Exception;

	Object visitLHSPixel(LHSPixel lhsPixel, Object arg) throws Exception;

	Object visitLHSSample(LHSSample lhsSample, Object arg) throws Exception;

	Object visitPixelSelector(PixelSelector pixelSelector, Object arg)
			throws Exception;

	Object visitProgram(Program program, Object arg) throws Exception;

	Object visitStatementAssign(StatementAssign statementAssign, Object arg)
			throws Exception;

	Object visitStatementIf(StatementIf statementIf, Object arg)
			throws Exception;

	Object visitStatementInput(StatementInput statementInput, Object arg)
			throws Exception;

	Object visitStatementShow(StatementShow statementShow, Object arg)
			throws Exception;

	Object visitStatementSleep(StatementSleep statementSleep, Object arg)
			throws Exception;

	Object visitStatementWhile(StatementWhile statementWhile, Object arg)
			throws Exception;

	Object visitStatementWrite(StatementWrite statementWrite, Object arg)
			throws Exception;

}
