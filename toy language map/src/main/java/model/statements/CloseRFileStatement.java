package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    private IExpression expression;

    public CloseRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IValue eval = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if(!eval.getType().equals(new StringType())){
            throw new ExpressionException("The expression does not evaluate to a string type \n");
        }

        BufferedReader reader = state.getFileTable().get(((StringValue) eval));
        if (reader == null) {
            throw new StatementException("File not open or already closed\n");
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new StatementException("Error closing file\n");
        }

        state.getFileTable().remove(((StringValue) eval));

        return null;
    }

    public String toString(){
        return "closeRFile(" + expression.toString() + ");\n";
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        IType typexp = expression.typecheck(typeEnv);
        if(typexp.equals(new StringType())){
            return typeEnv;
        }
        else {
            throw new StatementException("The expression does not evaluate to a string type! \n");
        }
    }
}
