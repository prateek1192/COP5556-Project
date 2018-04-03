package cop5556sp18;
import cop5556sp18.Scanner.Kind;
import cop5556sp18.Scanner.Token;
import cop5556sp18.Types.Type;
import cop5556sp18.AST.ASTNode;
import cop5556sp18.AST.ASTVisitor;
import cop5556sp18.AST.Block;
import cop5556sp18.AST.Declaration;
import cop5556sp18.AST.ExpressionBinary;
import cop5556sp18.AST.ExpressionBooleanLiteral;
import cop5556sp18.AST.ExpressionConditional;
import cop5556sp18.AST.ExpressionFloatLiteral;
import cop5556sp18.AST.ExpressionFunctionAppWithExpressionArg;
import cop5556sp18.AST.ExpressionFunctionAppWithPixel;
import cop5556sp18.AST.ExpressionIdent;
import cop5556sp18.AST.ExpressionIntegerLiteral;
import cop5556sp18.AST.ExpressionPixel;
import cop5556sp18.AST.ExpressionPixelConstructor;
import cop5556sp18.AST.ExpressionPredefinedName;
import cop5556sp18.AST.ExpressionUnary;
import cop5556sp18.AST.LHSIdent;
import cop5556sp18.AST.LHSPixel;
import cop5556sp18.AST.LHSSample;
import cop5556sp18.AST.PixelSelector;
import cop5556sp18.AST.Program;
import cop5556sp18.AST.Statement;
import cop5556sp18.AST.StatementAssign;
import cop5556sp18.AST.StatementIf;
import cop5556sp18.AST.StatementInput;
import cop5556sp18.AST.StatementShow;
import cop5556sp18.AST.StatementSleep;
import cop5556sp18.AST.StatementWhile;
import cop5556sp18.AST.StatementWrite;
public class TypeChecker implements ASTVisitor {
	SymbolTable symTab = new SymbolTable();
	TypeChecker() {
	}

	@SuppressWarnings("serial")
	public static class SemanticException extends Exception {
		Token t;

		public SemanticException(Token t, String message) {
			super(message);
			this.t = t;
		}
	}

	// Name is only used for naming the output file. 
	// Visit the child block to type check program.
	@Override
	public Object visitProgram(Program program, Object arg) throws Exception {
		program.block.visit(this, arg);
		return null;
	}

	@Override
	public Object visitBlock(Block block, Object arg) throws Exception {
		// TODO Auto-generated method stub
		symTab.enterScope();
		for(ASTNode d: block.decsOrStatements)
		{
			if(d instanceof Declaration)
			{
				Declaration d1 = (Declaration) d;
				d1.visit(this, arg);
			}
			else
			{
				Statement s1 = (Statement) d;
				s1.visit(this, arg);
			}
		}
		symTab.leaveScope();
		return null;
	}

	@Override
	public Object visitDeclaration(Declaration declaration, Object arg) throws Exception {
		// TODO Auto-generated method stub
		boolean b = symTab.insert(declaration.name, declaration);
		if(!b){
			throw new SemanticException(declaration.firstToken, "Error in visitDeclaration");
		}
		if((declaration.height == null && declaration.width != null) || (declaration.width == null && declaration.height != null))
		{
			throw new SemanticException(declaration.firstToken, "Error in visitDeclaration");
		}
		if(declaration.width != null && declaration.height != null)
		{
			Type t1 = (Type) declaration.width.visit(this, arg);
			Type t2 = (Type) declaration.height.visit(this, arg);
			if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER) && declaration.type.equals(Kind.KW_image))
			{
				declaration.setType(Types.getType(declaration.type));
			}
			else
			{
				throw new SemanticException(declaration.firstToken, "Error in visitDeclaration");
			}
		}
		else
		{
			declaration.setType(Types.getType(declaration.type));
		}
		return null;
	}

	@Override
	public Object visitStatementWrite(StatementWrite statementWrite, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Declaration sourceDec = symTab.lookup(statementWrite.sourceName);
		Declaration destDec = symTab.lookup(statementWrite.destName);
		if(sourceDec == null || destDec == null)
		{
			throw new SemanticException(statementWrite.firstToken, "Error in visitStatementWrite");
		}
		Type t1 = sourceDec.getType();
		Type t2 = destDec.getType();
		if(!t1.equals(Type.IMAGE) || !t2.equals(Type.FILE))
		{
			throw new SemanticException(statementWrite.firstToken, "Error in visitStatementWrite");
		}
		statementWrite.setSourceDec(sourceDec);
		statementWrite.setDestDec(destDec);
		return null;
	}

	@Override
	public Object visitStatementInput(StatementInput statementInput, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Declaration sourceDec = symTab.lookup(statementInput.destName);
		if(sourceDec == null)
		{
			throw new SemanticException(statementInput.firstToken, "Error in visitStatementInput");
		}
		Type e = (Type) statementInput.e.visit(this, arg);
		if(!e.equals(Type.INTEGER))
		{
			throw new SemanticException(statementInput.firstToken, "Error in visitStatementInput");
		}
		statementInput.setDec(sourceDec);
		return null;
	}

	@Override
	public Object visitPixelSelector(PixelSelector pixelSelector, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) pixelSelector.ex.visit(this, arg);
		Type t2 = (Type) pixelSelector.ey.visit(this, arg);
		if(!t1.equals(t2))
		{
			throw new SemanticException(pixelSelector.firstToken, "Error in visitPixelSelector");
		}
		else
		{
			if(!t1.equals(Type.INTEGER) && !t1.equals(Type.FLOAT))
			{
				throw new SemanticException(pixelSelector.firstToken, "Error in visitPixelSelector");
			}
		}
		return null;
	}

	@Override
	public Object visitExpressionConditional(ExpressionConditional expressionConditional, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) expressionConditional.guard.visit(this, arg);
		if(!t1.equals(Type.BOOLEAN))
		{
			throw new SemanticException(expressionConditional.firstToken, "Error in visitExpressionConditional");
		}
		Type t2 = (Type) expressionConditional.trueExpression.visit(this, arg);
		Type t3 = (Type) expressionConditional.falseExpression.visit(this, arg);
		if(!t2.equals(t3))
		{
			throw new SemanticException(expressionConditional.firstToken, "Error in visitExpressionConditional");
		}
		expressionConditional.setType(t2);
		return expressionConditional.getType();
	}

	@Override
	public Object visitExpressionBinary(ExpressionBinary expressionBinary, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) expressionBinary.leftExpression.visit(this, arg);
		Type t2 = (Type) expressionBinary.rightExpression.visit(this, arg);
		Kind op = expressionBinary.op;
		if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER) && (op.equals(Kind.OP_PLUS) || op.equals(Kind.OP_MINUS) || op.equals(Kind.OP_TIMES) || op.equals(Kind.OP_DIV) || op.equals(Kind.OP_POWER) || op.equals(Kind.OP_MOD) || op.equals(Kind.OP_AND) || op.equals(Kind.OP_OR)))
		{
			expressionBinary.setType(Type.INTEGER);
		}
		else if(t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER) && (op.equals(Kind.OP_EQ) || op.equals(Kind.OP_NEQ) || op.equals(Kind.OP_GT) || op.equals(Kind.OP_GE) || op.equals(Kind.OP_LT) || op.equals(Kind.OP_LE)))
		{
			expressionBinary.setType(Type.BOOLEAN);
		}
		else if(t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT) && (op.equals(Kind.OP_PLUS) || op.equals(Kind.OP_MINUS) || op.equals(Kind.OP_TIMES) || op.equals(Kind.OP_DIV) || op.equals(Kind.OP_POWER)))
		{
			expressionBinary.setType(Type.FLOAT);
		}
		else if(t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT) && (op.equals(Kind.OP_EQ) || op.equals(Kind.OP_NEQ) || op.equals(Kind.OP_GT) || op.equals(Kind.OP_GE) || op.equals(Kind.OP_LT) || op.equals(Kind.OP_LE)))
		{
			expressionBinary.setType(Type.BOOLEAN);
		}
		else if(((t1.equals(Type.FLOAT) && t2.equals(Type.INTEGER)) || (t1.equals(Type.INTEGER) && t2.equals(Type.FLOAT))) && (op.equals(Kind.OP_PLUS) || op.equals(Kind.OP_MINUS) || op.equals(Kind.OP_TIMES) || op.equals(Kind.OP_DIV) || op.equals(Kind.OP_POWER)))
		{
			expressionBinary.setType(Type.FLOAT);
		}
		else if(t1.equals(Type.BOOLEAN) && t2.equals(Type.BOOLEAN) && (op.equals(Kind.OP_AND) || op.equals(Kind.OP_OR) || op.equals(Kind.OP_GT) || op.equals(Kind.OP_GE) || op.equals(Kind.OP_LE) || op.equals(Kind.OP_LT) || op.equals(Kind.OP_EQ) || op.equals(Kind.OP_NEQ)))
		{
			expressionBinary.setType(Type.BOOLEAN);
		}
		else
		{
			throw new SemanticException(expressionBinary.firstToken, "Error in visitExpressionBinary");
		}
		return expressionBinary.getType();
	}

	@Override
	public Object visitExpressionUnary(ExpressionUnary expressionUnary, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) expressionUnary.expression.visit(this, arg);
		if((expressionUnary.op.equals(Kind.OP_PLUS) && t1.equals(Type.BOOLEAN)) || (expressionUnary.op.equals(Kind.OP_MINUS) && t1.equals(Type.BOOLEAN)) || (expressionUnary.op.equals(Kind.OP_EXCLAMATION) && t1.equals(Type.FLOAT)) || t1.equals(Type.IMAGE) || t1.equals(Type.FILE))
		{
			throw new SemanticException(expressionUnary.firstToken, "Error in visitExpressionUnary");
		}
		expressionUnary.setType(t1);
		return expressionUnary.getType();
	}

	@Override
	public Object visitExpressionIntegerLiteral(ExpressionIntegerLiteral expressionIntegerLiteral, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		expressionIntegerLiteral.setType(Type.INTEGER);
		return expressionIntegerLiteral.getType();
	}

	@Override
	public Object visitBooleanLiteral(ExpressionBooleanLiteral expressionBooleanLiteral, Object arg) throws Exception {
		// TODO Auto-generated method stub
		expressionBooleanLiteral.setType(Type.BOOLEAN);
		return expressionBooleanLiteral.getType();
	}

	@Override
	public Object visitExpressionPredefinedName(ExpressionPredefinedName expressionPredefinedName, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		expressionPredefinedName.setType(Type.INTEGER);
		return expressionPredefinedName.getType();
	}

	@Override
	public Object visitExpressionFloatLiteral(ExpressionFloatLiteral expressionFloatLiteral, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		expressionFloatLiteral.setType(Type.FLOAT);
		return expressionFloatLiteral.getType();
	}

	@Override
	public Object visitExpressionFunctionAppWithExpressionArg(
			ExpressionFunctionAppWithExpressionArg expressionFunctionAppWithExpressionArg, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) expressionFunctionAppWithExpressionArg.e.visit(this, arg);
		Kind func = expressionFunctionAppWithExpressionArg.function;
		if(t1.equals(Type.INTEGER) && (func.equals(Kind.KW_abs) || func.equals(Kind.KW_red) || func.equals(Kind.KW_green) || func.equals(Kind.KW_blue) || func.equals(Kind.KW_alpha) || func.equals(Kind.KW_int)))
		{
			expressionFunctionAppWithExpressionArg.setType(Type.INTEGER);
		}
		else if(t1.equals(Type.INTEGER) && func.equals(Kind.KW_float))
		{
			expressionFunctionAppWithExpressionArg.setType(Type.FLOAT);
		}
		else if(t1.equals(Type.FLOAT) && (func.equals(Kind.KW_abs) || func.equals(Kind.KW_atan) || func.equals(Kind.KW_cos) || func.equals(Kind.KW_sin) || func.equals(Kind.KW_log) || func.equals(Kind.KW_float)))
		{
			expressionFunctionAppWithExpressionArg.setType(Type.FLOAT);
		}
		else if(t1.equals(Type.FLOAT) && func.equals(Kind.KW_int))
		{
			expressionFunctionAppWithExpressionArg.setType(Type.INTEGER);
		}
		else if(t1.equals(Type.IMAGE) && (func.equals(Kind.KW_width) || func.equals(Kind.KW_height)))
		{
			expressionFunctionAppWithExpressionArg.setType(Type.INTEGER);
		}
		else
		{
			throw new SemanticException(expressionFunctionAppWithExpressionArg.firstToken, "Error in visitExpressionFunctionAppWithExpressionArg");
		}
		return expressionFunctionAppWithExpressionArg.getType();
	}

	@Override
	public Object visitExpressionFunctionAppWithPixel(ExpressionFunctionAppWithPixel expressionFunctionAppWithPixel,
			Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) expressionFunctionAppWithPixel.e0.visit(this, arg);
		Type t2 = (Type) expressionFunctionAppWithPixel.e1.visit(this, arg);
		Kind func = expressionFunctionAppWithPixel.name;
		if((func.equals(Kind.KW_cart_x) || func.equals(Kind.KW_cart_y)) && t1.equals(Type.FLOAT) && t2.equals(Type.FLOAT))
		{
			expressionFunctionAppWithPixel.setType(Type.INTEGER);
		}
		else if((func.equals(Kind.KW_polar_a) || func.equals(Kind.KW_polar_r)) && t1.equals(Type.INTEGER) && t2.equals(Type.INTEGER))
		{
			expressionFunctionAppWithPixel.setType(Type.FLOAT);
		}
		else
		{
			throw new SemanticException(expressionFunctionAppWithPixel.firstToken, "Error in visitExpressionFunctionAppWithPixel");
		}
		return expressionFunctionAppWithPixel.getType();
	}

	@Override
	public Object visitExpressionPixelConstructor(ExpressionPixelConstructor expressionPixelConstructor, Object arg)
			throws Exception {
		// TODO Auto-generated method stub
		Type alpha = (Type) expressionPixelConstructor.alpha.visit(this, arg);
		Type blue = (Type) expressionPixelConstructor.blue.visit(this, arg);
		Type green = (Type) expressionPixelConstructor.green.visit(this, arg);
		Type red = (Type) expressionPixelConstructor.red.visit(this, arg);
		if(alpha.equals(Type.INTEGER) && blue.equals(Type.INTEGER) && green.equals(Type.INTEGER) && red.equals(Type.INTEGER))
		{
			expressionPixelConstructor.setType(Type.INTEGER);
		}
		else
		{
			throw new SemanticException(expressionPixelConstructor.firstToken, "Exception in visitExpressionPixelConstructor");
		}
		return expressionPixelConstructor.getType();
	}

	@Override
	public Object visitStatementAssign(StatementAssign statementAssign, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) statementAssign.lhs.visit(this, arg);
		Type t2 = (Type) statementAssign.e.visit(this, arg);
		if(!t1.equals(t2))
		{
			throw new SemanticException(statementAssign.firstToken, "Error in visitStatementAssign");
		}
		return null;
	}

	@Override
	public Object visitStatementShow(StatementShow statementShow, Object arg) throws Exception {
		Type e = (Type) statementShow.e.visit(this, arg);
		if(!e.equals(Type.INTEGER) && !e.equals(Type.FLOAT) && !e.equals(Type.IMAGE) && !e.equals(Type.BOOLEAN))
		{
			throw new SemanticException(statementShow.firstToken, "Error in visitStatementShow");
		}
		return null;
	}

	@Override
	public Object visitExpressionPixel(ExpressionPixel expressionPixel, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Declaration sourceDec = symTab.lookup(expressionPixel.name);
		if(sourceDec != null)
		{
			Type t1 = sourceDec.getType();
			if(t1.equals(Type.IMAGE))
			{
				expressionPixel.pixelSelector.visit(this, arg);
				expressionPixel.setType(Type.INTEGER);
			}
			else
			{
				throw new SemanticException(expressionPixel.firstToken, "Exception in visitExpressionPixel");
			}
		}
		else
		{
			throw new SemanticException(expressionPixel.firstToken, "Exception in visitExpressionPixel");
		}
		expressionPixel.setDec(sourceDec);
		return expressionPixel.getType();
	}

	@Override
	public Object visitExpressionIdent(ExpressionIdent expressionIdent, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Declaration sourceDec = symTab.lookup(expressionIdent.name);
		if(sourceDec != null)
		{
			expressionIdent.setType(sourceDec.getType()); 
		}
		else
		{
			throw new SemanticException(expressionIdent.firstToken, "Exception in visitExpressionIdent");
		}
		expressionIdent.setDec(sourceDec);
		return expressionIdent.getType();
	}

	@Override
	public Object visitLHSSample(LHSSample lhsSample, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Declaration sourceDec = symTab.lookup(lhsSample.name);
		if(sourceDec != null)
		{
			lhsSample.pixelSelector.visit(this, arg);
			Type t1 = sourceDec.getType();
			if(!t1.equals(Type.IMAGE))
			{
				throw new SemanticException(lhsSample.firstToken, "Error in visitLHSSample");
			}
			lhsSample.setType(Type.INTEGER);
		}
		else
		{
			throw new SemanticException(lhsSample.firstToken, "Error in visitLHSSample");
		}
		lhsSample.setDec(sourceDec);
		return lhsSample.getType();
	}

	@Override
	public Object visitLHSPixel(LHSPixel lhsPixel, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Declaration sourceDec = symTab.lookup(lhsPixel.name);
		if(sourceDec != null)
		{
			lhsPixel.pixelSelector.visit(this, arg);
			Type t1 = sourceDec.getType();
			if(!t1.equals(Type.IMAGE))
			{
				throw new SemanticException(lhsPixel.firstToken, "Error in visitLHSPixel");
			}
			lhsPixel.setType(Type.INTEGER);
		}
		else
		{
			throw new SemanticException(lhsPixel.firstToken, "Error in visitLHSPixel");
		}
		lhsPixel.setDec(sourceDec);
		return lhsPixel.getType();
	}

	@Override
	public Object visitLHSIdent(LHSIdent lhsIdent, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Declaration sourceDec = symTab.lookup(lhsIdent.name);
		if(sourceDec == null)
		{
			throw new SemanticException(lhsIdent.firstToken, "Error in visitLHSIdent");
		}
		Type t1 = sourceDec.getType();
		lhsIdent.setType(t1);
		lhsIdent.setDec(sourceDec);
		return lhsIdent.getType();
	}

	@Override
	public Object visitStatementIf(StatementIf statementIf, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) statementIf.guard.visit(this, arg);
		if(!t1.equals(Type.BOOLEAN))
		{
			throw new SemanticException(statementIf.firstToken, "Exception in visitStatementIf");
		}
		statementIf.b.visit(this, arg);
		return null;
	}

	@Override
	public Object visitStatementWhile(StatementWhile statementWhile, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) statementWhile.guard.visit(this, arg);
		if(!t1.equals(Type.BOOLEAN))
		{
			throw new SemanticException(statementWhile.firstToken, "Exception in visitStatementWhile");
		}
		statementWhile.b.visit(this, arg);
		return null;
	}

	@Override
	public Object visitStatementSleep(StatementSleep statementSleep, Object arg) throws Exception {
		// TODO Auto-generated method stub
		Type t1 = (Type) statementSleep.duration.visit(this, arg);
		if(!t1.equals(Type.INTEGER))
		{
			throw new SemanticException(statementSleep.firstToken, "Exception in visitStatementSleep");
		}
		return null;
	}
}