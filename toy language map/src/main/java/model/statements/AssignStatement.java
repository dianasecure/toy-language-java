package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.expressions.IExpression;
import model.type.IType;
import model.value.IValue;

public class AssignStatement implements IStatement{
    String id;
    IExpression expression;

    public AssignStatement(String id, IExpression expression){
        this.id = id;
        this.expression = expression;
    }

    public String toString(){
        return this.id + " = " + this.expression + ";\n";
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (!state.getSymTable().contains(id)){
            throw new StatementException("Variable wasn't declared previously!\n");
        }
        IValue val = expression.evaluate(state.getSymTable(), state.getHeap());

        if (!val.getType().equals(state.getSymTable().get(id).getType())){
            throw new StatementException("Type mismatch!\n");
        }

        state.getSymTable().insert(id, val);
        return null;
    }

    public IStatement deepCopy(){
        return new AssignStatement(this.id, this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typevar = typeEnv.get(id);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new StatementException("Assignment: right hand side and left hand side have different types! \n");
    }
}