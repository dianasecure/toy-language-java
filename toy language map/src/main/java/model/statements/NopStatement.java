package model.statements;

import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.type.IType;

public class NopStatement implements IStatement {

    public NopStatement() {};

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }

    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        return null;
    }
}