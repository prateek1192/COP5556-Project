package cop5556sp18;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp18.Parser;
import cop5556sp18.Scanner;
import cop5556sp18.AST.ASTVisitor;
import cop5556sp18.AST.Program;
import cop5556sp18.TypeChecker.SemanticException;

public class TypeCheckerTest {

	/*
	 * set Junit to be able to catch exceptions
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Prints objects in a way that is easy to turn on and off
	 */
	static final boolean doPrint = true;

	private void show(Object input) {
		if (doPrint) {
			System.out.println(input.toString());
		}
	}

	/**
	 * Scans, parses, and type checks the input string
	 * 
	 * @param input
	 * @throws Exception
	 */
	void typeCheck(String input) throws Exception {
		show(input);
		// instantiate a Scanner and scan input
		Scanner scanner = new Scanner(input).scan();
		show(scanner);
		// instantiate a Parser and parse input to obtain and AST
		Program ast = new Parser(scanner).parse();
		show(ast);
		// instantiate a TypeChecker and visit the ast to perform type checking and
		// decorate the AST.
		ASTVisitor v = new TypeChecker();
		ast.visit(v, null);
	}



	/**
	 * Simple test case with an almost empty program.
	 * 
	 * @throws Exception
	 */
	@Test
	public void emptyProg() throws Exception {
		String input = "emptyProg{}";
		typeCheck(input);
	}

	@Test
	public void expression1() throws Exception {
		String input = "prog {show 3+4;}";
		typeCheck(input);
	}
	
	@Test
	public void expression2() throws Exception {
		String input = "prog {show 3.5+4;}";
		typeCheck(input);
	}
	
	@Test
	public void expression3() throws Exception {
		String input = "prog {show 3**4;}";
		typeCheck(input);
	}
	
	@Test
	public void expression4() throws Exception {
		String input = "prog {show 3&4;}";
		typeCheck(input);
	}
	
	@Test
	public void expression5() throws Exception {
		String input = "prog {show 3.5/4.5;}";
		typeCheck(input);
	}
	
	@Test
	public void expression6() throws Exception {
		String input = "prog {show 3==4;}";
		typeCheck(input);
	}
	
	@Test
	public void expression7() throws Exception {
		String input = "prog {show 3>=4;}";
		typeCheck(input);
	}
	
	@Test
	public void expression8() throws Exception {
		String input = "prog {show true&true;}";
		typeCheck(input);
	}
	
	@Test
	public void expression9() throws Exception {
		String input = "prog {show true==false;}";
		typeCheck(input);
	}
	
	@Test
	public void expression10() throws Exception {
		String input = "prog {sleep 10;}";
		typeCheck(input);
	}
	
	@Test
	public void expression11() throws Exception {
		String input = "prog {int i; i := 0; while(i < 2){show i; i := i + 1;};}";
		typeCheck(input);
	}
	
	@Test
	public void expression12() throws Exception {
		String input = "prog {while(false){show 2+2;};}";
		typeCheck(input);
	}
	
	@Test
	public void expression13() throws Exception {
		String input = "prog {while(false){show 2+2;};}";
		typeCheck(input);
	}
	
	@Test
	public void expression14() throws Exception {
		String input = "prog {show abs(5);}";
		typeCheck(input);
	}
	
	@Test
	public void expression15() throws Exception {
		String input = "prog {show red(4);}";
		typeCheck(input);
	}
	
	@Test
	public void expression16() throws Exception {
		String input = "prog {show atan(90.8);}";
		typeCheck(input);
	}
	
	@Test
	public void expression17() throws Exception {
		String input = "prog {show cart_x[4.4, 3.4];}";
		typeCheck(input);
	}
	
	@Test
	public void expression18() throws Exception {
		String input = "prog {show cart_y[4.4, 3.4];}";
		typeCheck(input);
	}
	
	@Test
	public void expression19() throws Exception {
		String input = "prog {show polar_a[4, 3];}";
		typeCheck(input);
	}
	
	@Test
	public void expression20() throws Exception {
		String input = "prog {show polar_r[4, 3];}";
		typeCheck(input);
	}
	
	@Test
	public void expression21() throws Exception {
		String input = "prog {show <<2, 3, 4, 5>>;}";
		typeCheck(input);
	}
	
	@Test
	public void expression22() throws Exception {
		String input = "prog {image a; filename b; write a to b;}";
		typeCheck(input);
	}
	
	@Test
	public void expression23() throws Exception {
		String input = "prog {image a[2, 4]; filename b; write a to b;}";
		typeCheck(input);
	}
	
	@Test
	public void expression24() throws Exception {
		String input = "prog {image a; input a from @ 2;}";
		typeCheck(input);
	}
	
	@Test
	public void expression25() throws Exception {
		String input = "prog {int a; a := 2;}";
		typeCheck(input);
	}
	
	@Test
	public void expression26() throws Exception {
		String input = "prog {image b; b[2, 3] := 5;}";
		typeCheck(input);
	}
	
	@Test
	public void expression27() throws Exception {
		String input = "prog {image b; b[2.5, 3.5] := 5;}";
		typeCheck(input);
	}
	
	@Test
	public void expression28() throws Exception {
		String input = "prog {image b; green(b[2.5, 3.5]) := 5;}";
		typeCheck(input);
	}
	
	@Test
	public void expression29() throws Exception {
		String input = "prog {float a; a := 2.5;}";
		typeCheck(input);
	}
	
	@Test
	public void expression30() throws Exception {
		String input = "prog {boolean b; boolean c; b := c;}";
		typeCheck(input);
	}
	
	@Test
	public void expression31() throws Exception {
		String input = "prog {filename b; filename c; b := c;}";
		typeCheck(input);
	}
	
	@Test
	public void expression32() throws Exception {
		String input = "prog {image b; image c; b := c;}";
		typeCheck(input);
	}
	
	@Test
	public void expression33() throws Exception {
		String input = "prog {int a; a := 2==3 ? 5 : 4;}";
		typeCheck(input);
	}
	
	@Test
	public void expression34() throws Exception {
		String input = "prog {filename a; filename b; filename c; a := 2==3 ? b : c;}";
		typeCheck(input);
	}
	
	@Test
	public void expression35() throws Exception {
		String input = "prog {int a; a := 2==3 ? +2 : -2;}";
		typeCheck(input);
	}
	
	@Test
	public void expression36() throws Exception {
		String input = "prog {int a; image b; a := b[2,3];}";
		typeCheck(input);
	}
	
	@Test
	public void expression37() throws Exception {
		String input = "prog {show 3**4.5;}";
		typeCheck(input);
	}
	
	@Test
	public void expression2_fail() throws Exception {
		String input = "prog { show true+4; }"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression3_fail() throws Exception {
		String input = "prog { show true+false; }"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression4_fail() throws Exception {
		String input = "prog { show 3.5%4.5; }"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression5_fail() throws Exception {
		String input = "prog {sleep 3.5;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression6_fail() throws Exception {
		String input = "prog {if(2){show 4;};}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression7_fail() throws Exception {
		String input = "prog {while(2){show 4;};}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression8_fail() throws Exception {
		String input = "prog {show red(5.5);}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression9_fail() throws Exception {
		String input = "prog {show log(10);}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression10_fail() throws Exception {
		String input = "prog {show polar_r[4.4, 3.4];}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression11_fail() throws Exception {
		String input = "prog {show cart_x[4, 3];}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression12_fail() throws Exception {
		String input = "prog {show <<2.4, 3.4, 4.4, 5.4>>;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression13_fail() throws Exception {
		String input = "prog {image a[2.5, 4.2]; filename b; write a to b;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression14_fail() throws Exception {
		String input = "prog {int a; a := 2.5;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression18_fail() throws Exception {
		String input = "prog {image a; a := 2.5;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression19_fail() throws Exception {
		String input = "prog {float a; a := 2;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression15_fail() throws Exception {
		String input = "prog {image b; b[2, 3.5] := 3;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression16_fail() throws Exception {
		String input = "prog {image b; b[2, 3] := 3.5;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression17_fail() throws Exception {
		String input = "prog {image b; blue(b[2, 3]) := 3.5;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression20_fail() throws Exception {
		String input = "prog {float b; b := 2==3 ? 4 : 5;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression21_fail() throws Exception {
		String input = "prog {float b; b := 2 ? 4 : 5;}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
	
	@Test
	public void expression22_fail() throws Exception {
		String input = "prog {int b; image c; b := c[2, 3.5];}"; //error, incompatible types in binary expression
		thrown.expect(SemanticException.class);
		try {
			typeCheck(input);
		} catch (SemanticException e) {
			show(e);
			throw e;
		}
	}
}
