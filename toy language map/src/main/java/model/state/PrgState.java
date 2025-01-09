package model.state;

import exceptions.ControllerException;
import model.adt.*;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStatement> execStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<String> outputList;
    private IStatement originalStatement;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;
    static int nextId = 0;
    private int id;


    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIDictionary<String,
            IValue> symTable, MyIList<String> outputList, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap heap) {
        this.execStack = execStack;
        this.symTable = symTable;
        this.outputList = outputList;
        this.originalStatement = initState.deepCopy();
        this.fileTable = fileTable;
        this.execStack.push(initState);
        this.heap = heap;
        id = this.getNextId();
    }

    public synchronized int getNextId() {
        return ++nextId;
    }

    public int getId() { return id; }

    public MyIStack<IStatement> getExecStack() {
        return execStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<String> getOutputList() {
        return outputList;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public String toString(){
        return "ID OF PROGRAM: " + id + "\n"
                + "The stack contains: \n" + execStack.toString() + "\n"
                + "SymTable: " + symTable.toString() + "\n"
                + "Output: " + outputList.toString() + "\n"
                + this.toStringFile() + "\n"
                + heap.toString() + "\n"
                + "---------------------------------------\n";
    }

    public String toStringFile(){
        String s = "File Table: ";
        for (StringValue str : fileTable.getKeys()){
            s += str.getValue() + " | ";
        }
        return s;
    }

    public Boolean isNotCompleted(){
        return !execStack.isEmpty();
    }

    public PrgState oneStep() throws ControllerException {
        try {
            MyIStack<IStatement> stack = getExecStack();
            IStatement currentStatement = stack.pop();
            return currentStatement.execute(this);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}