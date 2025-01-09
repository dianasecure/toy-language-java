package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.*;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {

        MyIStack<IStatement> newExecStack = new MyStack<>();
        PrgState newForkState = new PrgState(this.statement, newExecStack, state.getSymTable().deepCopy(),
                state.getOutputList(),state.getFileTable(),state.getHeap() );

        return newForkState;
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    public String toString(){
        return "fork(" + statement.toString() + "    );\n";
    }
}
