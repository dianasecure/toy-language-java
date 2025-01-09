package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;


public class IfStatement implements IStatement {
    IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public String toString(){
        return "if("+ expression.toString() +"){\n    "+ thenStatement.toString() +
                "} else {\n    "+ elseStatement.toString()+"}\n";
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IValue val = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new BoolType())) {
            throw new StatementException("The expression is not a bool type!\n");
        }
        if (((BoolValue) val).getValue()) {
            state.getExecStack().push(this.thenStatement);
        } else
            state.getExecStack().push(this.elseStatement);
        return null;
    }

    public IStatement deepCopy(){
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StatementException("The condition of IF has not the type bool");
    }
}