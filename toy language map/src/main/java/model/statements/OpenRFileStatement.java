package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.expressions.IExpression;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{
    private IExpression expression;
    public OpenRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IValue eval = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if(!eval.getType().equals(new StringType())){
            throw new ExpressionException("The expression does not evaluate to a string type");
        }
        if(state.getFileTable().contains((StringValue) eval)){
            throw new ExpressionException("The expression is already open");
        }
        StringValue computedFileName = (StringValue) eval;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(computedFileName.getValue()));
            state.getFileTable().insert(computedFileName, reader);
            System.out.println("File opened and added to FileTable: " + computedFileName.getValue());

        } catch (IOException e) {
            throw new StatementException("Error opening file");
        }
        return null;
    }

    public String toString(){
        return "openRFile(" + expression.toString() + ");\n";
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
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
