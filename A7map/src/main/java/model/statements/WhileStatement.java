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

public class WhileStatement implements IStatement{
    IExpression expression;
    IStatement statement;
    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {

        IValue value = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!(value.getType() instanceof BoolType)){
            throw new ExpressionException("While statement expression expects a boolean value! \n");
        }

        if(((BoolValue) value).getValue()){
            IStatement whileStmt = new WhileStatement(expression, statement);
            state.getExecStack().push(whileStmt);
            state.getExecStack().push(statement);
        }

        return null;
    }

    public String toString(){
        return "while(" + expression.toString() + "){\n" + "    " + statement.toString() + "}\n";
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typexp = expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new StatementException("The condition of while has not the type bool");

    }
}
