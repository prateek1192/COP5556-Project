package cop5556sp18.AST;

import cop5556sp18.Scanner.Token;

public class StatementIf extends Statement {

	public final Expression guard;
	public final Block b;

	public StatementIf(Token firstToken, Expression guard, Block b) {
		super(firstToken);
		this.guard = guard;
		this.b = b;
	}

	@Override
	public Object visit(ASTVisitor v, Object arg) throws Exception {
		return v.visitStatementIf(this, arg);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((guard == null) ? 0 : guard.hashCode());
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
		StatementIf other = (StatementIf) obj;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		if (guard == null) {
			if (other.guard != null)
				return false;
		} else if (!guard.equals(other.guard))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatementIf [guard=" + guard + ", b=" + b + "]";
	}

}
